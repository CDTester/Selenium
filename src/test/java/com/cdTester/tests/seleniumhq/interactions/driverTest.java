package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;

public class driverTest extends BaseTest {
  private final String URL = "https://www.selenium.dev/";

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(URL);
  }

  @AfterEach
  public void tearDown(TestInfo testInfo) {
    // Check if test failed
    if (testInfo.getTags().contains("failed")) {
      String text =  testInfo.getDisplayName().replace(" ", "_").replace("'", "");
      takeScreenshot(driver, text).join();
      driver.quit();
    }
    else {
      driver.quit();
    }
  }

  @Test
  @Tag("failed")
  @DisplayName("Browser title should be 'Selenium'")
  public void getTitle() {
    try {
      String title = driver.getTitle();
      Assertions.assertEquals("Selenium", title, "Page title is not as expected");
    }
    catch (Exception e) {
      System.out.println("Exception occurred: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should have current URL of 'https://www.selenium.dev/'")
  public void getCurrentUrl() {
    try {
      String url = driver.getCurrentUrl();
      Assertions.assertEquals(URL, url, "Current URL is not as expected");
    }
    catch (Exception e) {
      System.out.println("Exception occurred: " + e.getMessage());
    }
  }
}