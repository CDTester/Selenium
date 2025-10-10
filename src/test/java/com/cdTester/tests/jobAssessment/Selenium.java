package com.cdTester.tests.jobAssessment;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Arrays;


public class Selenium {

  public static void main(String[] args) {
    String url = "https://www.selenium.dev/selenium/web/alerts.html#";
    By emailInput = By.id("email");
    By passwordInput = By.id("password");
    By loginButton = By.id("login_button");

    int attempts;
    String[] emails;
    String[] passwords;
    int successCounter = 0;

    System.out.println("Enter the number of login attempts:");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      attempts = Integer.parseInt(reader.readLine());
      System.out.println("Number of attempts: " + attempts);

      System.out.println("Enter the email addresses, separated by space:");
      emails = reader.readLine().split(" ");
      System.out.println("Email addresses: " + Arrays.toString(emails));

      System.out.println("Enter the passwords, separated by space:");
      passwords = reader.readLine().split(" ");
      System.out.println("Passwords: " + Arrays.toString(passwords));

    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (int i = 0; i <= attempts; i++) {
      System.out.println("Attempt " + (i + 1) + ":");
      System.out.println("Email: " + emails[i]);
      System.out.println("Password: " + passwords[i]);

      ChromeOptions options = new ChromeOptions();
      WebDriver driver = new ChromeDriver(options);
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      driver.get(url);

      // find input and enter email address
      driver.findElement(emailInput).sendKeys(emails[i]);

      // find input and enter password
      driver.findElement(passwordInput).sendKeys(passwords[i]);

      // click on login
      driver.findElement(loginButton).click();

      // Switch to alert to get success/fail
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      String result = alert.getText();
      if (result.contains("success")) {
        successCounter++;
      }
      alert.accept();

      driver.close();
    }

    System.out.println("The total number of successful login attempts was: " + successCounter);

  }
}
