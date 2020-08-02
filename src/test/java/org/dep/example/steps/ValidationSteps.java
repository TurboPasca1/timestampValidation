package org.dep.example.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dep.example.config.PageObjectManager;

import java.util.List;
import java.util.Map;

public class ValidationSteps {
    @Given("user is on my dummy website")
    public void theUserIsOnMyDummyWebsite() {
        PageObjectManager.getDefectlessDummyPage().open();
    }


    @When("user clicks on the download button")
    public void userClicksOnTheDownloadButton() {
        PageObjectManager.getDefectlessDummyPage().clickOnDownloadButton();
    }

    @Then("the file has been downloaded to the default folder by {string} user and file name starts with {string} and ends with {string}")
    public void theFileHasBeenDownloadedToTheDefaultFolderByUserAndFileNameStartsWithAndEndsWith(String expectedUserName, String expectedBeginningOfFileName, String expectedEndOfFileName) {
        PageObjectManager.getDefectlessDummyPage().checkThatTheFileHasBeenDownloadedToTheDefaultFolder(expectedUserName,expectedBeginningOfFileName,expectedEndOfFileName);
    }

    @And("wait")
    public void waitALittle() throws InterruptedException {
        Thread.sleep(2000);
    }
}
