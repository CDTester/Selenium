package com.cdTester.pages.selenium.web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Alerts {
  public WebDriver driver;

  @FindBy(id = "alert")
  public WebElement alertLink;

  @FindBy(id = "empty-alert")
  public WebElement emptyAlertLink;

  @FindBy(id = "prompt")
  public WebElement promptLink;

  @FindBy(id = "prompt-with-default")
  public WebElement promptWithDefaultLink;

  @FindBy(id = "double-prompt")
  public WebElement doublePromptLink;

  @FindBy(id = "slow-alert")
  public WebElement slowAlertLink;

  @FindBy(id = "confirm")
  public WebElement confirmAlertLink;

  public By iframeWindow = By.name("iframeWithAlert");

  @FindBy(id = "alertInFrame")
  public WebElement iframeLink;

  public By nestedIframeWindow = By.name("iframeWithIframe");

  public Alerts(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public String getAlertMessage() {
    Alert alert = this.driver.switchTo().alert();
    return alert.getText();
  }

  public String getAlertMessageAndClose(String acceptDismiss) {
    Alert alert = this.driver.switchTo().alert();
    String message = alert.getText();
    if (acceptDismiss == "accept") {
      alert.accept();
    }
    else {
      alert.dismiss();
    }
    return message;
  }

  public void sendKeysToAlertAndClose(String text, String acceptDismiss) {
    Alert alert = this.driver.switchTo().alert();
    alert.sendKeys(text);
    if (acceptDismiss == "accept") {
      alert.accept();
    }
    else {
      alert.dismiss();
    }
  }

  public String getUrl() {
    return this.driver.getCurrentUrl();
  }

  public WebDriver switchToIframe(By iframe) {

    WebElement iframeElement = this.driver.findElement(iframe);
    System.out.println("Switching to iFrame: " + iframeElement.getAttribute("name"));

    return this.driver.switchTo().frame(iframeElement);
  }

}
