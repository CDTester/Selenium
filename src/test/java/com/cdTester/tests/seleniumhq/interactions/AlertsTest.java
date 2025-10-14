package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.pages.selenium.web.Alerts;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertsTest extends BaseTest {
  protected Alerts alertsPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.alerts);
    alertsPage = new Alerts(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to get text from alert and accept it")
  public void alertInformationTest() throws InterruptedException {
    alertsPage.click(alertsPage.alertLink);

    wait.until(ExpectedConditions.alertIsPresent());
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("cheese", alertMessage, "Alert text not as expected");
  }

  @Test
  @DisplayName("Should be able to accept an empty alert")
  public void alertEmptyInformationTest() throws InterruptedException {
    alertsPage.click(alertsPage.emptyAlertLink);

    wait.until(ExpectedConditions.alertIsPresent());
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("", alertMessage, "Alert text not as expected");
  }

  @Test
  @DisplayName("Should be able to enter text into a prompt and accept it")
  public void promptDisplayAndInputTest() throws InterruptedException {
    alertsPage.click(alertsPage.promptLink);

    //Wait for the alert to be displayed and store it in a variable
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("Enter something", alertsPage.getAlertMessage(), "Alert text not as expected");
    alertsPage.sendKeysToAlertAndClose("Selenium", "accept");
  }

  @Test
  @DisplayName("Should not be able to read the default text in a prompt")
  public void promptDefaultInputTest() throws InterruptedException {
    alertsPage.click(alertsPage.promptWithDefaultLink);

    wait.until(ExpectedConditions.alertIsPresent());
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("Enter something", alertMessage, "Alert message not as expected");
    assertNotEquals("This is a default value", alertMessage, "Should not have got the default prompt message");
  }

  @Test
  @DisplayName("Should be able to enter text into multiple prompts and accept them")
  public void multiplePromptInputsTest() throws InterruptedException {
    alertsPage.click(alertsPage.doublePromptLink);
    wait.until(ExpectedConditions.alertIsPresent());

    assertEquals("First", alertsPage.getAlertMessage(), "Alert message not as expected");
    alertsPage.sendKeysToAlertAndClose("first", "accept");

    assertEquals("Second", alertsPage.getAlertMessage(), "Alert message not as expected");
    alertsPage.sendKeysToAlertAndClose("second", "accept");
  }

  @Test
  @DisplayName("Should be able to handle a delayed alert")
  public void slowAlertTest() throws InterruptedException {
    alertsPage.click(alertsPage.slowAlertLink);
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("Slow", alertsPage.getAlertMessageAndClose("accept"));
  }

  @Test
  @DisplayName("Should be able to accept a confirmation alert that redirects to another URL")
  public void confirmationAlertTest() throws InterruptedException {
    alertsPage.click(alertsPage.confirmAlertLink);
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("Are you sure?", alertsPage.getAlertMessageAndClose("accept"),
            "Alert message not as expected");
    assertTrue(alertsPage.getUrl().endsWith("simpleTest.html"),"Page was not redirected, URL = " + alertsPage.getUrl());
  }

  @Test
  @DisplayName("Should be able to click a link in an iframe that triggers an alert")
  public void iframeAlertTest() throws InterruptedException {
    alertsPage.switchToIframe(alertsPage.iframeWindow);
    alertsPage.click(alertsPage.iframeLink);
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("framed cheese", alertsPage.getAlertMessageAndClose("accept"),
            "Alert message not as expected");
  }

  @Test
  @DisplayName("Should be able to click a link in a nested iframe that triggers an alert")
  public void nestedIframeAlertTest() throws InterruptedException {
    alertsPage.switchToIframe(alertsPage.nestedIframeWindow);
    alertsPage.switchToIframe(alertsPage.iframeWindow);

    alertsPage.click(alertsPage.iframeLink);
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("framed cheese", alertsPage.getAlertMessageAndClose("accept"),
            "Alert message not as expected");
  }

  @Test
  @DisplayName("Should be able to handle alerts created by JavaScriptExecutor")
  public void testForAlerts() throws InterruptedException {

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("alert('Sample Alert');");
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("Sample Alert", alertsPage.getAlertMessageAndClose("accept"));

    js.executeScript("confirm('Are you sure?');");
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("Are you sure?", alertsPage.getAlertMessageAndClose("dismiss"));

    js.executeScript("prompt('What is your name?');");
    wait.until(ExpectedConditions.alertIsPresent());
    assertEquals("What is your name?", alertsPage.getAlertMessage());
    alertsPage.sendKeysToAlertAndClose("Selenium","accept");

    driver.quit();
  }
}