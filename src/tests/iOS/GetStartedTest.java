package tests.iOS;

import lib.IOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends IOSTestCase {

    @Test
    public void testPassThroughWelcome()
    {

        WelcomePageObject Welcome = new WelcomePageObject(driver);

        Welcome.waitForLearnMoreLink();
        Welcome.clickNextButton();

        Welcome.waitForNewWayToExploreText();
        Welcome.clickNextButton();

        Welcome.waitForAddOrEditPreferredLangText();
        Welcome.clickNextButton();

        Welcome.waitForLearnMoreAboutDataCollectedText();
        Welcome.clickGetStartedButton();
    }
}
