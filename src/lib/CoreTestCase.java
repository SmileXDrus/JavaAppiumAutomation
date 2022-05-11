package lib;

import junit.framework.TestCase;

import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;

public class CoreTestCase extends TestCase {

    protected AppiumDriver<?> driver;


    @Override
    protected void setUp() throws Exception {
        super.setUp(); //  use setUp from TestCase
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIOSApp();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    private void skipWelcomePageForIOSApp() {
        if(Platform.getInstance().isIOS()){
            WelcomePageObject welcome = new WelcomePageObject(driver);
            welcome.clickSkip();
        }
    }
}
