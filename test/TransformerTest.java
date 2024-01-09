import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {

    @Test
    void testBwtForward() {
        String text = "banana";
        String expected = "annb$aa";
        String actual = Transformer.doBwtForward(text);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBwtBackwards() {
        String expected = "banana";
        String text = "annb$aa";
        String actual = Transformer.doBwtBackwards(text);
        Assertions.assertEquals(expected, actual);
    }
}