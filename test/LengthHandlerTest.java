import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthHandlerTest {

    @Test
    void testRleForward() {
        String test = "wwwwaaadexxxxxxywww";
        String expected = "w4a3dex6yw3";
        String actual = LengthHandler.doRleForward(test);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testRleBackwards() {
        String expected = "wwwwaaadexxxxxxywww";
        String test = "w4a3dex6yw3";
        String actual = LengthHandler.doRleBackwards(test);
        Assertions.assertEquals(expected, actual);
    }
}