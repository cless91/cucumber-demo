package com.example.cucumberdemo;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"html:target/cucumber-html-report",
        "json:target/cucumber.json", "junit:target/cucumber-results.xml",
        "pretty:target/cucumber-pretty.txt", "usage:target/cucumber-usage.json"},
        glue = "com.example.cucumberdemo")
public class TestRunner {
}
