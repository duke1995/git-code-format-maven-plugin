package com.cosium.code.format.formatter;

import static com.google.googlejavaformat.java.JavaFormatterOptions.Style.AOSP;
import static com.google.googlejavaformat.java.JavaFormatterOptions.Style.GOOGLE;

import com.google.googlejavaformat.java.JavaFormatterOptions;

/** @author Réda Housni Alaoui */
public class GoogleJavaFormatterOptions {

  private final JavaFormatterOptions.Style style;
  private final boolean fixImportsOnly;
  private final boolean skipSortingImports;
  private final boolean skipRemovingUnusedImports;
  private final boolean skipJavadocFormatting;

  public GoogleJavaFormatterOptions(
      boolean aosp,
      boolean fixImportsOnly,
      boolean skipSortingImports,
      boolean skipRemovingUnusedImports,
      boolean skipJavadocFormatting) {
    if (aosp) {
      style = AOSP;
    } else {
      style = GOOGLE;
    }
    this.fixImportsOnly = fixImportsOnly;
    this.skipSortingImports = skipSortingImports;
    this.skipRemovingUnusedImports = skipRemovingUnusedImports;
    this.skipJavadocFormatting = skipJavadocFormatting;
  }

  public JavaFormatterOptions javaFormatterOptions() {
    return JavaFormatterOptions.builder().style(style).formatJavadoc(!skipJavadocFormatting).build();
  }

  public boolean isFixImportsOnly() {
    return fixImportsOnly;
  }

  public boolean isSkipSortingImports() {
    return skipSortingImports;
  }

  public boolean isSkipRemovingUnusedImports() {
    return skipRemovingUnusedImports;
  }

  public boolean isSkipJavadocFormatting() {
    return skipJavadocFormatting;
  }

  @Override
  public String toString() {
    return "GoogleJavaFormatterOptions{"
        + "style="
        + style
        + ", fixImportsOnly="
        + fixImportsOnly
        + ", skipSortingImports="
        + skipSortingImports
        + ", skipRemovingUnusedImports="
        + skipRemovingUnusedImports
        + ", skipJavadocFormatting="
        + skipJavadocFormatting
        + '}';
  }
}
