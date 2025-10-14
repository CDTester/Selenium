package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.pages.selenium.web.Windows;
import com.cdTester.tests.seleniumhq.BaseTest;
import com.cdTester.pages.selenium.web.Urls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WindowsTest extends BaseTest {
  protected Windows windowPage;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    driver.get(Urls.windows);
    windowPage = new Windows(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to get window handle")
  public void windowsHandle() throws InterruptedException{
    //fetch handle of this
    String currHandle=driver.getWindowHandle();
    System.out.println("Current handle is: "+currHandle);

    Thread.sleep(500);
    assertNotNull(currHandle);
  }

  @Test
  @DisplayName("Should be able to get all window handles")
  public void windowsHandles() throws InterruptedException{
    //click on link to open a new window
    windowPage.openNewWindowLink.click();

    //fetch handles of all windows, there will be two, [0]- default, [1] - new window
    Object[] windowHandles = driver.getWindowHandles().toArray();
    driver.switchTo().window((String) windowHandles[1]);

    //assert all window handles are not null
    System.out.println("windowHandles are: ");
    for(Object handle: windowHandles){
      System.out.println(handle);
      assertNotNull(handle);
    }

    Thread.sleep(500);
  }

  @Test
  @DisplayName("Should be able to switch to a new tab using window handles")
  public void SwitchToNewTab() throws InterruptedException {
    //click on link to open a new window
    windowPage.openNewWindowLink.click();

    //fetch handles of all windows, there will be two, [0]- default, [1] - new window
    Object[] windowHandles = driver.getWindowHandles().toArray();
    driver.switchTo().window(windowHandles[1].toString());

    Thread.sleep(1000);

    //assert on title of new window
    String title=driver.getTitle();
    assertEquals("Simple Page",title);
  }

  @Test
  @DisplayName("Should be able to open a new tab and switch to it")
  public void opensNewTab() throws InterruptedException {
    //Opens a new tab and switches to new tab
    driver.switchTo().newWindow(WindowType.TAB);
    Thread.sleep(1000);

    assertEquals("",driver.getTitle());
  }


  @Test
  @DisplayName("Should be able to open a new window and switch to it")
  public void opensNewWindow() throws InterruptedException {
    //Opens a new window and switches to new window
    driver.switchTo().newWindow(WindowType.WINDOW);
    Thread.sleep(1000);

    assertEquals("",driver.getTitle());

    driver.close();
  }
}