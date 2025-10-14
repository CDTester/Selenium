package com.cdTester.pages.selenium.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.cdTester.utils.Highlight.*;


public class FormPage {
  public WebDriver driver;

  @FindBy(id = "email")
  public WebElement loginFormEmailInput;

  @FindBy(id = "age")
  public WebElement loginFormAgeInput;

  @FindBy(id = "submitButton")
  public WebElement loginFormSubmitButton;


  public FormPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void click(WebElement element) throws InterruptedException {
    highlightElement(this.driver, element);
    element.click();
  }

  public void type(WebElement element, String text) throws InterruptedException {
    highlightElement(this.driver, element);
    element.sendKeys(text);
  }

  public void clearText(WebElement element) throws InterruptedException {
    highlightElement(this.driver, element);
    element.click();
  }

  public String getUrl() {
    return this.driver.getCurrentUrl();
  }


}
