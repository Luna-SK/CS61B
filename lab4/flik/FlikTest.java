package flik;
import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void test127() {
        assertTrue(Flik.isSameNumber(127, 127));
    }

    @Test
    public void testObject() {
        assertTrue(Flik.isSameNumber(new Integer(22), new Integer(22)));
    }
}
