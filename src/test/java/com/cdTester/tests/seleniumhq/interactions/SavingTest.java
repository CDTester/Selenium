package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.tests.seleniumhq.BaseTest;
import com.cdTester.pages.Urls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class SavingTest extends BaseTest {
  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to save page as PDF")
  public void prints() throws IOException {
    driver.get(Urls.base);

    String content = ((RemoteWebDriver) driver).print(new PrintOptions()).getContent();
    byte[] bytes = Base64.getDecoder().decode(content);
    Files.write(Paths.get("selenium.pdf"), bytes);

    assertTrue(Files.exists(Paths.get("selenium.pdf")));
  }
}