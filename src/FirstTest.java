import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.apache.bcel.ExceptionConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setup() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        // для андройд любое, для ios соответствовало устройству
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("AutomationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app ","C:/Users/user/IdeaProjects/JavaAppiumAutomation/apks");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {


        /* WebElement element_to_skip_setting = driver.findElementById("org.wikipedia:id/fragment_onboarding_skip_button");
        element_to_skip_setting.click();*/

        WebElement element_to_init_search = driver.findElementByXPath("//android.widget.ImageView[@content-desc=\"Search Wikipedia\"]");
        element_to_init_search.click();

        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
                "//*[contains(@text,'Search…')]",
                "Cannot find search element",
                5);
        element_to_enter_search_line.sendKeys("123");
    }

    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

}
