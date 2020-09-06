package com.example.cucumberdemo;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefs {
    private WebDriver driver;

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", false);
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.out.println("start recording "+scenario.getName());
        VideoRecorderUtility.startRecord(scenario.getName());
    }

    @BeforeStep
    public void beforeStep() throws InterruptedException {
        Thread.sleep(1000);
    }

    @After
    public void after() throws Exception {
        driver.close();
        VideoRecorderUtility.stopRecord();
    }

    @Given("^I am on Facebook login page$")
    public void goToFacebook() {
        driver.navigate().to("https://www.facebook.com/");
    }

    @When("^I enter username as \"(.*)\"$")
    public void enterUsername(String arg1) {
        driver.findElement(By.id("email")).sendKeys(arg1);
    }

    @When("^I enter password as \"(.*)\"$")
    public void enterPassword(String arg1) {
        driver.findElement(By.id("pass")).sendKeys(arg1);
        driver.findElement(By.name("login")).click();
    }

    @Then("^Login should fail$")
    public void checkFail() {
        Awaitility.await()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofSeconds(1))
                .with()
                .conditionEvaluationListener(evaluatedCondition -> System.out.println(evaluatedCondition.getDescription()))
                .until(() -> driver.getCurrentUrl(), string -> string.matches("https://www\\.facebook\\.com/login.*"));
    }

    @Then("^Relogin option should be available$")
    public void checkRelogin() {
        assertThat(driver.findElements(By.id("pass"))).isNotEmpty();
        assertThat(driver.findElements(By.id("loginbutton"))).isNotEmpty();
    }

    @Given("I am on Linkedin login page")
    public void iAmOnLinkedinLoginPage() {
        driver.navigate().to("https://www.linkedin.com/");
    }

    @When("^I enter username as \"(.*)\" on Linkedin$")
    public void enterUsernameLinkedin(String arg1) {
        driver.findElement(By.id("session_key")).sendKeys(arg1);
    }

    @When("^I enter password as \"(.*)\" on Linkedin$")
    public void enterPasswordLinkedin(String arg1) throws InterruptedException {
        driver.findElement(By.id("session_password")).sendKeys(arg1);
        Thread.sleep(1000);
        driver.findElement(By.className("sign-in-form__submit-button")).click();
    }

    @Then("^Login should fail on Linkedin$")
    public void checkFailLinkedIn() {
        WebElement alertContent = Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofSeconds(1))
                .with()
                .conditionEvaluationListener(evaluatedCondition -> System.out.println(evaluatedCondition.getDescription()))
                .until(() -> driver.findElements(By.className("alert-content")),
                        webElements -> !webElements.isEmpty())
                .get(0);

        assertThat(alertContent.getText()).isEqualTo("Please enter a valid email address or mobile number.");
    }
}
