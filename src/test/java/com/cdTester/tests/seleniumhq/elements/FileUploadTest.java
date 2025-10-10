package com.cdTester.tests.seleniumhq.elements;

import com.cdTester.tests.seleniumhq.BaseChromeTest;
import com.cdTester.pages.selenium.web.Upload;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileUploadTest extends BaseChromeTest {

  protected String filePath = "src/test/resources/";
  protected String fileName = "selenium-snapshot.png";

  @Test
  @DisplayName("Should be able to Upload a fileName")
  public void fileUploadTest() {
    Upload uploadPage = new Upload(driver);

    /* The following is replaced by Page Object Model
     *    File uploadFile = new File(filePath + fileName);
     *    WebElement fileInput = driver.findElement(By.cssSelector("input[type=fileName]"));
     *    fileInput.sendKeys(uploadFile.getAbsolutePath());
     *    driver.findElement(By.id("fileName-submit")).click();
    */
    uploadPage.uploadFile(filePath, fileName);


    /* The following is replaced by Page Object Model
     *    WebElement fileName = driver.findElement(By.id("uploaded-files"));
    */
    assertEquals(fileName, uploadPage.getUploadedFile());
  }
}
