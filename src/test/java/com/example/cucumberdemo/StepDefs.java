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
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefs {
    private WebDriver driver;
    private Actions actions;

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.FINEST);

        FirefoxOptions options = new FirefoxOptions();
//        options.setHeadless(true);
//        options.setLogLevel(FirefoxDriverLogLevel.TRACE);
//        driver = new FirefoxDriver(options);
        driver = new FirefoxDriver();
        actions = new Actions(driver);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("start recording "+scenario.getName());
        VideoRecorderUtility.startRecord(scenario.getName());
    }

    @BeforeStep
    public void beforeStep() throws InterruptedException {
        Thread.sleep(1000);
    }

    @After
    public void after() throws Exception {
        driver.quit();
        VideoRecorderUtility.stopRecord();
    }

    @Given("^I am on Facebook login page$")
    public void goToFacebook() {
        driver.navigate().to("https://www.facebook.com/");
    }

    @When("^I enter username as \"(.*)\"$")
    public void enterUsername(String arg1) {
        WebElement target = driver.findElement(By.id("email"));
        actions.moveToElement(target).build().perform();
        target.sendKeys(arg1);
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
        WebElement target = driver.findElement(By.id("session_key"));
        Point location = target.getLocation();
        actions.moveToElement(target, location.getX(), location.getY()).sendKeys(arg1);
//        actions.moveToElement(target, location.getX(), location.getY()).build().perform();
//        target.sendKeys(arg1);
    }

    @When("^I enter password as \"(.*)\" on Linkedin$")
    public void enterPasswordLinkedin(String arg1) throws InterruptedException {
        WebElement target = driver.findElement(By.id("session_password"));
        actions.moveToElement(target).build().perform();
        target.sendKeys(arg1);
        Thread.sleep(1000);
        target = driver.findElement(By.className("sign-in-form__submit-button"));
        actions.moveToElement(target).build().perform();
        target.click();
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

