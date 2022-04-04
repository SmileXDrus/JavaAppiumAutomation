import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.synth.SynthDesktopIconUI;

public class MainTest extends CoreTestCase {

    @Before
    public void textStartTest() {
        System.out.println("Start test");
    }

    @After
    public void textFinishText(){
        System.out.println("Finish");
    }

    @Test
    public void firstTest() {
        System.out.println("First");
    }

    @Test
    public void secondTest() {
        System.out.println("Second");
    }

}
