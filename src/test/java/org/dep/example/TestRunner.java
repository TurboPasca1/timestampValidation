package org.dep.example;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = {"src/test/resources/features"},
        tags = { "@validation" }
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
