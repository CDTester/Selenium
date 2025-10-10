package com.cdTester.pages.selenium.web;

import com.cdTester.utils.ConfigReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
  WebDriver driver;

  protected String URL = ConfigReader.getBaseUrl() + "selenium/web/login.html";

  @FindBy(id = "username-field")
  public WebElement usernameField;

  @FindBy(id = "password-field")
  public WebElement passwordField;

  @FindBy(id = "login-form-submit")
  public WebElement loginButton;

  public Login(WebDriver driver) {
    String URL = this.URL;
    this.driver = driver;
    driver.get(URL);
    PageFactory.initElements(driver, this);
  }

  public void login(String user, String password){
    usernameField.sendKeys(user);
    passwordField.sendKeys(password);
    loginButton.click();
  }

  public String getLoginStatusAndClose() {
    Alert alert = this.driver.switchTo().alert();
    String message = alert.getText();
    alert.accept();
    return message;
  }
}
