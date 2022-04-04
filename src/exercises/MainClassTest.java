package exercises;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest  {
    MainClass mc_obj = new MainClass();

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Return value is not equal 14", 14, mc_obj.getLocalNumber());
    }
}
