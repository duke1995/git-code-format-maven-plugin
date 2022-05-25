package com.cosium.code.format.formatter;

import static java.util.Objects.requireNonNull;

import com.cosium.code.format.FileExtension;
import com.cosium.code.format.MavenGitCodeFormatException;
import com.google.common.collect.RangeSet;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.ImportOrderer;
import com.google.googlejavaformat.java.RemoveUnusedImports;
import com.google.googlejavaformat.java.StringWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

/**
 * Created on 07/11/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class GoogleJavaFormatter implements CodeFormatter {

  private final GoogleJavaFormatterOptions options;
  private final Formatter formatter;
  private final String sourceEncoding;

  public GoogleJavaFormatter(GoogleJavaFormatterOptions options, String sourceEncoding) {
    this.options = requireNonNull(options);
    this.formatter = new Formatter(options.javaFormatterOptions());
    this.sourceEncoding = sourceEncoding;
  }

  @Override
  public FileExtension fileExtension() {
    return FileExtension.of("java");
  }

  @Override
  public void format(InputStream content, LineRanges lineRanges, OutputStream formattedContent) {
    final String formattedContentToWrite;
    try {
      String unformattedContent = IOUtils.toString(content, sourceEncoding);
      formattedContentToWrite = doFormat(unformattedContent, lineRanges);
    } catch (IOException | FormatterException e) {
      throw new MavenGitCodeFormatException(e);
    }

    try {
      IOUtils.write(formattedContentToWrite, formattedContent, sourceEncoding);
    } catch (IOException e) {
      throw new MavenGitCodeFormatException(e);
    }
  }

  @Override
  public boolean validate(InputStream content) {
    try {
      String unformattedContent = IOUtils.toString(content, sourceEncoding);
      String formattedContent = doFormat(unformattedContent, LineRanges.all());
      return unformattedContent.equals(formattedContent);
    } catch (IOException | FormatterException e) {
      throw new MavenGitCodeFormatException(e);
    }
  }

  private String doFormat(String unformattedContent, LineRanges lineRanges)
      throws FormatterException {
    if (options.isFixImportsOnly()) {
      if (!lineRanges.isAll()) {
        return unformattedContent;
      }
      return fixImports(unformattedContent);
    }
    if (lineRanges.isAll()) {
      String formatted = formatter.formatSource(unformattedContent);

      if (!options.isSkipReflowingLongStrings()) {
        formatted = StringWrapper.wrap(formatted, formatter);
      }

      return fixImports(formatted);
    }

    RangeSet<Integer> charRangeSet =
        Formatter.lineRangesToCharRanges(unformattedContent, lineRanges.rangeSet());
    String formatted = formatter.formatSource(unformattedContent, charRangeSet.asRanges());

    if (!options.isSkipReflowingLongStrings()) {
      formatted = StringWrapper.wrap(formatted, formatter);
    }

    return formatted;
  }

  private String fixImports(final String unformattedContent) throws FormatterException {
    String formattedContent = unformattedContent;
    if (!options.isSkipRemovingUnusedImports()) {
      formattedContent = RemoveUnusedImports.removeUnusedImports(formattedContent);
    }
    if (!options.isSkipSortingImports()) {
      formattedContent = ImportOrderer.reorderImports(formattedContent);
    }
    return formattedContent;
  }
}
