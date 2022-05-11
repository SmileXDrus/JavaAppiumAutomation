package lib;

import junit.framework.TestCase;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class CoreTestCase extends TestCase {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android";

    protected AppiumDriver<?> driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp(); //  use setUp from TestCase

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

        if (isPlatform(PLATFORM_ANDROID)) {
            URL URL = new URL(AppiumURL);
            driver = new AndroidDriver(URL, this.getCapabilitiesByPlatformEnv());
        }  else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
        this.rotateScreenPortrait();
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

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        String platform = getPlatformVar();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            // для андройд любое, для ios соответствовало устройству
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("AutomationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app ","C:/Users/user/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE (2nd generation)");
            capabilities.setCapability("platformVersion", "14.5");
            capabilities.setCapability("app ", "C:/Users/user/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value: " + platform);
        }
        return capabilities;
    }

    private boolean isPlatform(String my_platform)
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }
}
