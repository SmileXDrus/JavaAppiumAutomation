package lib.ui;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class MainPageObject {
    protected AppiumDriver<?> driver;

    public MainPageObject(AppiumDriver<?> driver) {
        this.driver = driver;
    }

    public boolean assertExpectedTextMatchesTheTextAttribute(String locator_with_type, String expected_text, String attribute, String error_message) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.attributeContains(by, attribute, expected_text)
        );
    }

    public List<WebElement> getListOfAllElementsLocatedBy(String locator_with_type, String error_message) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(by)
        );
    }

    public WebElement waitForElementPresent(String locator_with_type, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public boolean assertElementPresent(String locator_with_type) {
        By by = this.getLocatorByString(locator_with_type);
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e_selenium) {
            return false;
        }
    }

    public WebElement waitForElementPresent(String locator_with_type, String error_message) {
        // в java нет параметра по умолчанию, используют перегрузку методов
        return waitForElementPresent(locator_with_type, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator_with_type, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator_with_type, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSeconds);
        MobileElement elMobile = (MobileElement) element;
        elMobile.setValue(value);
        return elMobile;
    }

    public boolean waitForElementNotPresent(String locator_with_type, String error_message, long timeoutInSecond) {
        By by = this.getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + '\n');
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator_with_type, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSecond);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width * 0.8);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();

    }

    public void swipeUpQuick() {
        swipeUp(100);
    }

    public void swipeUpToFindElement(String locator_with_type, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator_with_type);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(locator_with_type, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator_with_type) {
        return true; //TODO
    }

    public void swipeElementToLeft(String locator_with_type, String err_msg) {
        WebElement element = waitForElementPresent(
                locator_with_type,
                err_msg,
                5
        );
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                .moveTo(PointOption.point(left_x / 2, middle_y))
                .release()
                .perform();

    }

    public int getAmountOfElements(String locator_with_type) {
        By by = this.getLocatorByString(locator_with_type);
        List<?> elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator_with_type, String err_msg) {
        int amount_of_elements = getAmountOfElements(locator_with_type);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + locator_with_type + "' supposed to be not present";
            throw new AssertionError(default_message + " " + err_msg);
        }
    }

    public String waitForElementAndGetAttribute(String locator_with_type, String attribute, String err_msg, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator_with_type, err_msg, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")) {
            return  By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator value: " + locator_with_type);
        }

    }
}
