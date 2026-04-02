package tests;
import Services.DigitScrubber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitScrubberTest {

    private final DigitScrubber digitScrubber = new DigitScrubber();

    //test 1: the happy normal path ,just normal digit scrubbing
    @Test
    void testScrubDigits_NormalCase() {
        String result = digitScrubber.scrub("Call me at 1234");
        assertEquals("Call me at XXXX", result);
    }

    //test 2: code has a defect here ,it scrubs prices
    @Test
    void testScrubDigits_WithPrice_ShouldNotChange() {
        String result = digitScrubber.scrub("Price is 1111$");
        assertEquals("Price is 1111$", result);
    }

    //test 3 : it will scrub both price and numbers
    @Test
    void testScrubDigits_MixedInput() {
        String result = digitScrubber.scrub("Call 123 and price 50$");
        assertEquals("Call XXX and price 50$", result);
    }

    // test 4 : it throws null pointer exception if string is null
    @Test
    void testScrubDigits_NullInput_ShouldThrow() {
        assertThrows(NullPointerException.class, () -> {digitScrubber.scrub(null);});
    }

    // test 5 : it throws IllegalArgumentException if string is blank
    @Test
    void testScrubDigits_BlankInput_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            digitScrubber.scrub("   ");
        });
    }
}