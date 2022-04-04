package exercises;

import org.junit.Assert;
import org.junit.Test;

public class MainClassTest  {
    MainClass mc_obj = new MainClass();

    @Test
    public void testGetClassNumber(){
        Assert.assertTrue("Number not more than 45", mc_obj.getClassNumber() > 45);
    }

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Return value is not equal 14", 14, mc_obj.getLocalNumber());
    }
}
