package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.pages.selenium.web.FormPage;
import com.cdTester.pages.Urls;
import com.cdTester.tests.seleniumhq.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelectListTest extends BaseTest {
  protected FormPage formsPage;

  @BeforeEach
  public void navigate() {
    driver = startChromeDriver(1);
    driver.get(Urls.formPage);
    formsPage = new FormPage(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @DisplayName("Should be able to select options from a single select list")
  public void selectOption() throws InterruptedException {
    WebElement nonMultiSelect = formsPage.highlightElement(formsPage.nonMultiSelect);
    Select select = new Select(nonMultiSelect);
    Thread.sleep(500);

    select.selectByVisibleText("Four");
    assertTrue(formsPage.nonMultiOptionFour.isSelected());
    Thread.sleep(500);

    select.selectByValue("two");
    assertTrue(formsPage.nonMultiOptionTwo.isSelected());
    Thread.sleep(500);

    select.selectByIndex(3);
    assertTrue(formsPage.nonMultiOptionStillLearning.isSelected());
    Thread.sleep(500);
  }

  @Test
  @DisplayName("Should be able to select multiple options from a multi select list")
  public void selectMultipleOption() throws InterruptedException {
    WebElement multiSelect = formsPage.highlightElement(formsPage.multiSelect);
    Select select = new Select(multiSelect);
    Thread.sleep(500);

    WebElement hamOption = formsPage.multiOptionHam;
    WebElement gravyoption = formsPage.multiOptionGravy;
    WebElement eggOption = formsPage.multiOptionEggs;
    WebElement sausageOption = formsPage.multiOptionSausages;

    List<WebElement> optionsByFindingElements = multiSelect.findElements(By.tagName("option"));
    List<WebElement> optionsBySelectGetOptions = select.getOptions();
    assertEquals(optionsByFindingElements, optionsBySelectGetOptions);

    List<WebElement> getSelectedOptionList = select.getAllSelectedOptions();
    List<WebElement> expectedSelection = new ArrayList<WebElement>() {{
      add(eggOption);
      add(sausageOption);
    }};
    assertEquals(expectedSelection, getSelectedOptionList);

    select.selectByValue("ham");
    Thread.sleep(500);
    select.selectByValue("onion gravy");
    Thread.sleep(500);
    Assertions.assertTrue(hamOption.isSelected());
    Assertions.assertTrue(gravyoption.isSelected());

    select.deselectByValue("eggs");
    Thread.sleep(500);
    select.deselectByValue("sausages");
    Thread.sleep(500);
    Assertions.assertFalse(eggOption.isSelected());
    Assertions.assertFalse(sausageOption.isSelected());
  }

  @Test
  @DisplayName("Should not be able to select a disabled option from a select list")
  public void disabledOption() throws InterruptedException {
    WebElement SelectDisabled = formsPage.highlightElement(formsPage.singleSelectDisabled);
    SelectDisabled.click();
    Thread.sleep(500);
    assertTrue(formsPage.singleOptionEnabled.isEnabled());
    assertFalse(formsPage.singleOptionDisabled.isEnabled());

    Select select = new Select(SelectDisabled);
    Thread.sleep(500);

    assertThrows(UnsupportedOperationException.class, () -> {
      select.selectByValue("disabled");
    });
  }
}