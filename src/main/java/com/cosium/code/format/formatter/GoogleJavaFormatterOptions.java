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
  private final boolean skipReflowingLongStrings;

  public GoogleJavaFormatterOptions(
      boolean aosp,
      boolean fixImportsOnly,
      boolean skipSortingImports,
      boolean skipRemovingUnusedImports,
      boolean skipReflowingLongStrings) {
    if (aosp) {
      style = AOSP;
    } else {
      style = GOOGLE;
    }
    this.fixImportsOnly = fixImportsOnly;
    this.skipSortingImports = skipSortingImports;
    this.skipRemovingUnusedImports = skipRemovingUnusedImports;
    this.skipReflowingLongStrings = skipReflowingLongStrings;
  }

  public JavaFormatterOptions javaFormatterOptions() {
    return JavaFormatterOptions.builder().style(style).build();
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

  public boolean isSkipReflowingLongStrings() {
    return skipReflowingLongStrings;
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
        + ", skipReflowingLongStrings="
        + skipReflowingLongStrings
        + '}';
  }
}
