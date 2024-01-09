import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplifierTest {

    @Test
    void testEncode() {
        String test = "abba";
        String alphabet = "abc";
        String expected = "0 1 0 1";
        String actual = Simplifier.encode(test, alphabet);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDecode() {
        String expected = "abba";
        String alphabet = "abc";
        String test = "0 1 0 1";
        String actual = Simplifier.decode(test, alphabet);
        Assertions.assertEquals(expected, actual);
    }
}