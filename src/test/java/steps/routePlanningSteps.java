package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class routePlanningSteps {

    protected static WebDriver driver;

    protected static WebDriverWait wait;

    @Before // cucumber annotáció
    public static void setup() throws IOException {
        WebDriverManager.chromedriver().setup();

        // loading arguments, properties
        Properties props = new Properties(); // java.util
        InputStream is = routePlanningSteps.class.getResourceAsStream("/browser.properties");
        props.load(is);

        // set chrome options
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(props.getProperty("chrome.arguments"));
        // chromeOptions.setHeadless(true);

        // init driver
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        driver.manage().window().setSize(new Dimension(900, 900)); // ...selenium.Dimension
    }

    // @After
    // public static void cleanup() {
    //    driver.quit();
    //}

    @Given("page open")
    public void pageOpen() {
        driver.get("https://go.bkk.hu");
    }

    @And("cookies accepted")
    public void cookiesAccepted() {
        WebElement acceptButton = wait.until(driver -> driver.findElement(By.xpath("/html/body/div[5]/div/div[3]/button[2]")));
        acceptButton.click();
    }

    @Given("address A is {string}")
    public void addressAIs(String addressA) {
        WebElement inputAddressA = driver.findElement(By.cssSelector("#panel-context-view > div > form > div:nth-child(1) > table > tbody > tr:nth-child(1) > td.planner-from-to > input"));
        inputAddressA.sendKeys(addressA);
    }


    @And("address B is {string}")
    public void addressBIs(String addressB) {
        WebElement inputAddressB = driver.findElement(By.cssSelector("#panel-context-view > div > form > div:nth-child(1) > table > tbody > tr:nth-child(2) > td.planner-from-to > input"));
        inputAddressB.sendKeys(addressB);
        inputAddressB.click();
    }

    @When("planning initiated")
    public void planningInitiated() {
        WebElement planButton = wait.until(driver -> driver.findElement(By.cssSelector("#panel-context-view > div > form > div.planner-button-container > input")));
        planButton.click();
// Ha debug módban futtatom (tehát kézzel lököm tovább a 86-os sornál), akkor jól fut.
// Sima futtatás esetén panaszkodik, hogy nem tudja a "Plan" gombot megkattintani. Ez mitől van?
    }

    @Then("calculated route is shown")
    public void calculatedRouteIsShown() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement itineraries = wait.until(driver -> driver.findElement(By.className("itineraries-container")));
        assertTrue(itineraries.isDisplayed());


        //   //*[@id="panel-context-view"]/div/div[1]
        // <div class="noprint itineraries-container">
        //
        //    <div class="itineraries ui-accordion ui-widget ui-helper-reset" role="tablist">
        //        <h1 class="itinerary-overview-header ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" data-itinerary="0" role="tab" id="ui-accordion-4-header-0" aria-controls="ui-accordion-4-panel-0" aria-selected="false" aria-expanded="false" tabindex="0"><div class="root-0-2-2" title=""><div class="root-0-2-8">17:35 ⇒ 18:20<span class="duration-0-2-3">45 perc</span></div><div class="container-0-2-4"><div class="legs-0-2-5"><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 box-0-2-22" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">4</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"></div><span class="root-0-2-20 box-0-2-22 mergeWithNext-0-2-19" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">47</span></div></div><span class="root-0-2-20 box-0-2-22 item-0-2-12 mergeWithPrevious-0-2-18" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">56</span><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="root-0-2-17" style="width: 22px; height: 22px; background-size: 154px; background-position: 0px -308px;"></span></div></div></div><p class="infoRow-0-2-7">556 m séta</p></div></div></h1>
        //        <div class="itinerary-overview-content ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" id="ui-accordion-4-panel-0" aria-labelledby="ui-accordion-4-header-0" role="tabpanel" aria-hidden="true" style="display: none;"></div>
        //        <h1 class="itinerary-overview-header ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" data-itinerary="1" role="tab" id="ui-accordion-4-header-1" aria-controls="ui-accordion-4-panel-1" aria-selected="false" aria-expanded="false" tabindex="-1"><div class="root-0-2-2" title=""><div class="root-0-2-8">17:53 ⇒ 18:24<span class="duration-0-2-3">31 perc</span></div><div class="container-0-2-4"><div class="legs-0-2-5"><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 box-0-2-22 mergeWithNext-0-2-19" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">4</span></div></div><span class="root-0-2-20 box-0-2-22 item-0-2-12 mergeWithPrevious-0-2-18" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">6</span><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=subway&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 circle-0-2-21" style="background-color: rgb(76, 162, 47); color: rgb(255, 255, 255);">4</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=rail&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"></div><span class="root-0-2-20 box-0-2-22 mergeWithNext-0-2-19" style="background-color: rgb(0, 175, 240); color: rgb(255, 255, 255);">S36</span></div></div><span class="root-0-2-20 box-0-2-22 item-0-2-12 mergeWithPrevious-0-2-18" style="background-color: rgb(0, 175, 240); color: rgb(255, 255, 255);">S40</span></div><p class="infoRow-0-2-7">309 m séta</p></div></div></h1>
        //        <div class="itinerary-overview-content ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" id="ui-accordion-4-panel-1" aria-labelledby="ui-accordion-4-header-1" role="tabpanel" aria-hidden="true" style="display: none;"></div>
        //        <h1 class="itinerary-overview-header ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" data-itinerary="2" role="tab" id="ui-accordion-4-header-2" aria-controls="ui-accordion-4-panel-2" aria-selected="false" aria-expanded="false" tabindex="-1"><div class="root-0-2-2" title=""><div class="root-0-2-8">17:41 ⇒ 18:28<span class="duration-0-2-3">47 perc</span></div><div class="container-0-2-4"><div class="legs-0-2-5"><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 box-0-2-22" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">6</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=bus&amp;color=009EE3&amp;secondaryColor=FFFFFF&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 alert-0-2-16" style="width: 11px; height: 11px; background-size: 77px; background-position: -33px -143px;"></span><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 box-0-2-22" style="background-color: rgb(0, 158, 227); color: rgb(255, 255, 255);">33</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="root-0-2-17" style="width: 22px; height: 22px; background-size: 154px; background-position: 0px -308px;"></span></div></div></div><p class="infoRow-0-2-7">532 m séta</p></div></div></h1>
        //        <div class="itinerary-overview-content ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" id="ui-accordion-4-panel-2" aria-labelledby="ui-accordion-4-header-2" role="tabpanel" aria-hidden="true" style="display: none;"></div>
        //        <h1 class="itinerary-overview-header ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" data-itinerary="3" role="tab" id="ui-accordion-4-header-3" aria-controls="ui-accordion-4-panel-3" aria-selected="false" aria-expanded="false" tabindex="-1"><div class="root-0-2-2" title=""><div class="root-0-2-8">17:37 ⇒ 18:24<span class="duration-0-2-3">47 perc</span></div><div class="container-0-2-4"><div class="legs-0-2-5"><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 box-0-2-22" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">6</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=subway&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"><span class="root-0-2-17 wheelchairAccessibleIcon-0-2-15" style="width: 11px; height: 11px; background-size: 77px; background-position: -66px -132px;"></span></div><span class="root-0-2-20 circle-0-2-21" style="background-color: rgb(76, 162, 47); color: rgb(255, 255, 255);">4</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="agazat-logo arculat-sprite" style="background-size: 22px; background-position: 0px center, 0px center; background-image: url(&quot;/api/ui-service/v1/icon?name=tram&amp;color=FFD800&amp;secondaryColor=000000&amp;scale=0.275&quot;); width: 22px; height: 22px;"></span><div class="extraIconContainer-0-2-14"></div><span class="root-0-2-20 box-0-2-22" style="background-color: rgb(255, 216, 0); color: rgb(0, 0, 0);">56</span></div></div><div class="root-0-2-25 dash-0-2-27"></div><div class="head-0-2-10 item-0-2-12"><div class="top-0-2-11"><span class="root-0-2-17" style="width: 22px; height: 22px; background-size: 154px; background-position: 0px -308px;"></span></div></div></div><p class="infoRow-0-2-7">794 m séta</p></div></div></h1>
        //        <div class="itinerary-overview-content ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" id="ui-accordion-4-panel-3" aria-labelledby="ui-accordion-4-header-3" role="tabpanel" aria-hidden="true" style="display: none;"></div>
        //    </div>
        //</div>

    }


}
