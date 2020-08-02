package org.dep.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AbstractPage {
  protected WebDriver driver;

  public AbstractPage(WebDriver driver){
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void typeIntoInputField(String inputValue) {
    driver.findElement(By.cssSelector("input#username")).sendKeys(inputValue);
  }

  public void checkTypedInValue(String expectedValue) {
    String actualValue = driver.findElement(By.cssSelector("input#username")).getText();
    Assert.assertEquals(actualValue, expectedValue);
  }
}
