package com.cdTester.tests.seleniumhq.elements;

import com.cdTester.pages.selenium.web.Inputs;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

public class InformationTest extends BaseTest {
  protected Inputs inputPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.inputs);
    inputPage = new Inputs(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Email input should be displayed")
  public void isDisplayed() throws InterruptedException {
    WebElement email = inputPage.highlightElement(inputPage.emailInput);
    boolean isEmailVisible = email.isDisplayed();
    assertTrue(isEmailVisible);
  }

  @Test
  @DisplayName("Button input should be enabled")
  public void isEnabled() throws InterruptedException {
    WebElement button = inputPage.highlightElement(inputPage.buttonInput);
    boolean isEnabledButton = button.isEnabled();
    assertTrue(isEnabledButton);
  }

  @Test
  @DisplayName("Checkbox input should be selected")
  public void isSelected() throws InterruptedException {
    WebElement checkbox = inputPage.highlightElement(inputPage.checkboxInput);
    boolean isSelectedCheck = checkbox.isSelected();
    assertTrue(isSelectedCheck);
  }

  @Test
  @DisplayName("Should be able to get the tag name of an element")
  public void getTagName() throws InterruptedException {
    WebElement email = inputPage.highlightElement(inputPage.emailInput);
    String actualTagName = email.getTagName();
    assertEquals("input", actualTagName);
  }

  @Test
  @DisplayName("Should be able to get the horizontal size of an element")
  public void getRect() throws InterruptedException {
    WebElement range = inputPage.highlightElement(inputPage.rangeInput);
    Rectangle length = range.getRect();
    assertEquals(10, length.getX());
  }

  @Test
  @DisplayName("Should be able to get the font size of an element")
  public void getCssValue() throws InterruptedException {
    WebElement colour = inputPage.highlightElement(inputPage.colourInput);
    String fontSize = colour.getCssValue("font-size");
    assertEquals("13.3333px", fontSize);
  }

  @Test
  @DisplayName("Should be able to get text from an element")
  public void getText() throws InterruptedException {
    WebElement header = inputPage.highlightElement(inputPage.header1);
    String h1Text = header.getText();
    assertEquals("Testing Inputs", h1Text);
  }

  @Test
  @DisplayName("Should be able to get an attribute of an element")
  public void getAttribute() throws InterruptedException {
    WebElement email = inputPage.highlightElement(inputPage.emailInput);
    String value = email.getAttribute("value");
    assertEquals("admin@localhost", value);
  }

}
