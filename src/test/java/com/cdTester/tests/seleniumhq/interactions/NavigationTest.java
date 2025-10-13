package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.tests.seleniumhq.BaseTest;
import com.cdTester.pages.selenium.web.Urls;
import com.cdTester.pages.selenium.web.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTest extends BaseTest{

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to navigate to a URL using the driver.get() method")
  public void navigateBrowserGet() {
    //Convenient
    driver.get(Urls.base);
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @DisplayName("Should be able to navigate to a URL using the navigate().to() method")
  public void navigateBrowserTo() {
    //Longer way
    driver.navigate().to(Urls.base);
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @DisplayName("Should be able to navigate back")
  public void navigateBrowserBack() {
    driver.get(Urls.base);
    assertEquals("Selenium", driver.getTitle());

    Menu menu = new Menu(driver);
    menu.clickMenuLink(menu.documentationLink);
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());

    //Back
    driver.navigate().back();
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @DisplayName("Should be able to navigate forward")
  public void navigateBrowserForward () {
    driver.get(Urls.base);
    assertEquals("Selenium", driver.getTitle());

    Menu menu = new Menu(driver);
    menu.clickMenuLink(menu.documentationLink);
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());

    //Back
    driver.navigate().back();
    assertEquals("Selenium", driver.getTitle());

    //Forward
    driver.navigate().forward();
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());
  }

  @Test
  @DisplayName("Should be able to get text from alert and accept it")
  public void navigateBrowserRefresh() {
    driver.get(Urls.base);
    assertEquals("Selenium", driver.getTitle());

    //Refresh
    driver.navigate().refresh();
  }
}