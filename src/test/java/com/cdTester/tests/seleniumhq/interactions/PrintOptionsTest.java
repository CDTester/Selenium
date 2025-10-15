package com.cdTester.tests.seleniumhq.interactions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.print.PageSize;
import com.cdTester.tests.seleniumhq.BaseTest;
import com.cdTester.pages.Urls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Printing a webpage is a common task, whether for sharing information or maintaining archives.
 * Selenium simplifies this process through its PrintOptions, PrintsPage, and browsingContext classes,
 * which provide a flexible and intuitive interface for automating the printing of web pages.
 * These classes enable you to configure printing preferences, such as page layout, margins, and scaling,
 * ensuring that the output meets your specific requirements.
 */

public class PrintOptionsTest extends BaseTest {

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.base);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to print in landscape")
  public void TestOrientation() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setOrientation(PrintOptions.Orientation.LANDSCAPE);

    assertEquals(PrintOptions.Orientation.LANDSCAPE, printOptions.getOrientation());
  }


  @Test
  @DisplayName("Should be able set print page range")
  public void TestRange() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setPageRanges("1-2");

    String[] expectedRange = {"1", "2"};
    assertEquals(expectedRange, printOptions.getPageRanges());
  }

  @Test
  @DisplayName("Should be able set page paper size")
  public void TestSize() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setPageSize(new PageSize(27.94, 21.59)); // A4 size in cm

    assertEquals(27.94, printOptions.getPageSize().getHeight());
  }

  @Test
  @DisplayName("Should be able set page paper margins")
  public void TestMargins() {
    PrintOptions printOptions = new PrintOptions();
    PageMargin margins = new PageMargin(1.1,1.2,1.3,1.4);
    printOptions.setPageMargin(margins);

    assertEquals(1.1, margins.getTop());
    assertEquals(1.2, margins.getBottom());
    assertEquals(1.3, margins.getLeft());
    assertEquals(1.4, margins.getRight());
  }

  @Test
  @DisplayName("Should be able set print scale")
  public void TestScale() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setScale(.50);

    assertEquals(0.50, printOptions.getScale());
  }

  @Test
  @DisplayName("Should be able set the background images to print")
  public void TestBackground() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setBackground(true);

    assertTrue(printOptions.getBackground());
  }

  @Test
  @DisplayName("Should be able shrink the scale to fit on a page")
  public void TestShrinkToFit() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setShrinkToFit(true);

    assertTrue(printOptions.getShrinkToFit());
  }
}