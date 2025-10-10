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
  public void isDisplayed() {
    boolean isEmailVisible = inputPage.emailInput.isDisplayed();
    assertTrue(isEmailVisible);
  }

  @Test
  @DisplayName("Button input should be enabled")
  public void isEnabled() {
    boolean isEnabledButton = inputPage.buttonInput.isEnabled();
    assertTrue(isEnabledButton);
  }

  @Test
  @DisplayName("Checkbox input should be selected")
  public void isSelected() {
    boolean isSelectedCheck = inputPage.checkboxInput.isSelected();
    assertTrue(isSelectedCheck);
  }

  @Test
  @DisplayName("Should be able to get the tag name of an element")
  public void getTagName() {
    String actualTagName = inputPage.emailInput.getTagName();
    assertEquals("input", actualTagName);
  }

  @Test
  @DisplayName("Should be able to get the horizontal size of an element")
  public void getRect() {
    Rectangle length = inputPage.rangeInput.getRect();
    assertEquals(10, length.getX());
  }

  @Test
  @DisplayName("Should be able to get the font size of an element")
  public void getCssValue() {
    String fontSize = inputPage.colourInput.getCssValue("font-size");
    assertEquals("13.3333px", fontSize);
  }

  @Test
  @DisplayName("Should be able to get text from an element")
  public void getText() {
    String h1Text = inputPage.header1.getText();
    assertEquals("Testing Inputs", h1Text);
  }

  @Test
  @DisplayName("Should be able to get an attribute of an element")
  public void getAttribute() {
    String value = inputPage.emailInput.getAttribute("value");
    assertEquals("admin@localhost", value);
  }

}
