package com.cdTester.tests.seleniumhq.elements;

import com.cdTester.pages.selenium.web.Login;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
  protected Login loginPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.login);
    loginPage = new Login(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }


  @Test
  @DisplayName("Username field should have placeholder text")
  public void username() {
    String userPlaceholder = loginPage.usernameField.getAttribute("placeholder");
    assertEquals("Username", userPlaceholder, "Expected username placeholder not as expected");
  }

  @Test
  @DisplayName("Password field should have placeholder text")
  public void password() {
    String passwordPlaceholder = loginPage.passwordField.getAttribute("placeholder");
    assertEquals("Password", passwordPlaceholder, "Expected password placeholder not as expected");
  }

  @Test
  @DisplayName("Should get an error message when logging in with invalid credentials")
  public void invalidLogin() {
    System.out.println("username: " + username);
    System.out.println("password: " + password);
    loginPage.login(username, password);

    wait.until(ExpectedConditions.alertIsPresent());
    String status = loginPage.getLoginStatusAndClose();
    System.out.println("Login Message: " + status);
    assertEquals("Please enter valid credentials", status, "Login message not as expected");
  }

  @Test
  @DisplayName("Should get a success message when logging in with valid credentials")
  public void validLogin() {
    System.out.println("username: " + "username");
    System.out.println("password: " + "password");
    loginPage.login("username", "password");

    wait.until(ExpectedConditions.alertIsPresent());
    String status = loginPage.getLoginStatusAndClose();
    System.out.println("Login Message: " + status);
    assertEquals("You have successfully logged in.", status, "Login message not as expected");
  }

}
