package com.cdTester.tests.seleniumhq.interactions;


import com.cdTester.pages.selenium.web.Frames;
import com.cdTester.pages.selenium.web.FormPage;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FramesTest extends BaseTest {
  protected Frames framesPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.frames);
    framesPage = new Frames(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should switch to iFrame using id")
  public void iframeWithId() {

    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using Web Element
    framesPage.switchToIframe(framesPage.iframeWindowById);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement emailInput = forms.loginFormEmailInput;
    emailInput.sendKeys("admin@selenium.dev");
    emailInput.clear();

    // exit current iframe
    driver.switchTo().defaultContent();
  }

  @Test
  @DisplayName("Should switch to iFrame using name")
  public void iframeWithName() {
    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using Web Element
    framesPage.switchToIframe(framesPage.iframeWindowByName);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement emailInput = forms.loginFormEmailInput;
    emailInput.sendKeys("admin@selenium.dev");
    emailInput.clear();

    // exit current iframe
    driver.switchTo().defaultContent();
  }

  @Test
  @DisplayName("Should switch to iFrame using index")
  public void iframeWithIndex() {
    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using index
    driver.switchTo().frame(0);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement emailInput = forms.loginFormAgeInput;
    emailInput.sendKeys("21");
    emailInput.clear();

    //leave frame
    driver.switchTo().defaultContent();
  }

}
