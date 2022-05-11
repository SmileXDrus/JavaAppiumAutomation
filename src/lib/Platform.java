package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android",
            PLATFORM_MOBILE_WEB = "",
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    //Singleton
    private static Platform instance;
    private Platform() {} //private constructor, cannot return new object
    public static Platform getInstance() {
        if(instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception{
        URL URL = new URL(APPIUM_URL);
        if (isAndroid()) {
            return new AndroidDriver<>(URL, this.getAndroidDesiredCapabilities());
        } else if (isIOS()) {
            return new IOSDriver<>(URL, this.getIOSDesiredCapabilities());
        }
        else {
            throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
        }
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

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // для андройд любое, для ios соответствовало устройству
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app ", "C:/Users/user/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE (2nd generation)");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("app ", "C:/Users/user/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.app");
        return capabilities;
    }



    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW()
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }
}
