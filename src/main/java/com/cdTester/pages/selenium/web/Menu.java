package com.cdTester.pages.selenium.web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Menu {
  public WebDriver driver;

  @FindBy(id = "main_navbar")
  public WebElement mainMenuBar;

  @FindBy(css = "[data-bs-target='#main_navbar']")
  public WebElement smallMenuBar;

  @FindBy(linkText = "About")
  public WebElement aboutLink;

  @FindBy(linkText = "Downloads")
  public WebElement downloadsLink;

  @FindBy(linkText = "Documentation")
  public WebElement documentationLink;

  @FindBy(linkText = "Projects")
  public WebElement projectsLink;

  @FindBy(linkText = "Support")
  public WebElement supportLink;

  @FindBy(linkText = "Blog")
  public WebElement blogLink;

  @FindBy(linkText = "English")
  public WebElement LanguageLink;


  public Menu(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public String getMenuType() {
    int width = this.driver.manage().window().getSize().getWidth();
    if (width < 990) {
      return "small";
    }
    else {
      return "large";
    }
  }

  public void clickMenuLink(WebElement linkName) {
    if (getMenuType() == "small") {
      if (!this.mainMenuBar.isDisplayed()) {
        this.smallMenuBar.click();
      }
    }
    linkName.click();
  }

}
