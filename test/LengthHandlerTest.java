import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthHandlerTest {

    @Test
    void testRleForward() {
        String test = "123450000010";
        String expected = "1234505101";
        String actual = LengthHandler.doRleForward(test);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testRleBackwards() {
        String expected = "123450000010";
        String test = "1234505101";
        String actual = LengthHandler.doRleBackwards(test);
        Assertions.assertEquals(expected, actual);
    }
}