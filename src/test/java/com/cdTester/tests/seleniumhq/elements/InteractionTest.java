package com.cdTester.tests.seleniumhq.elements;

import com.cdTester.pages.selenium.web.Inputs;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InteractionTest extends BaseTest {
  protected Inputs inputPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(2);
    driver.get(Urls.inputs);
    inputPage = new Inputs(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }


  @Test
  @DisplayName("should be able to click on a checkbox")
  public void clickCheckbox() {
    boolean isSelected = inputPage.checkboxInput.isSelected();
    assertTrue(isSelected, "Expected checkbox to be selected");
    inputPage.checkboxInput.click();
    isSelected = inputPage.checkboxInput.isSelected();
    assertFalse(isSelected, "Expected checkbox to be unselected");
  }

  @Test
  @DisplayName("Should be able to clear the text from an input field")
  public void clearText() {
    inputPage.emailInput.clear();

    String emailInputValue = inputPage.emailInput.getAttribute("value");
    assertEquals("", emailInputValue, "Email value not as expected");
  }

  @Test
  @DisplayName("Should be able to enter text in an input field")
  public void isEnabled() {
    String email = "admin@localhost.dev";
    inputPage.emailInput.clear();
    inputPage.emailInput.sendKeys(email);

    String emailInputValue = inputPage.emailInput.getAttribute("value");
    assertEquals(email, emailInputValue, "Email value not as expected");
  }

}
