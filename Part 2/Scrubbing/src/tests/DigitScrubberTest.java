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

    @Test
    void testScrubDigits_WithPrice_ShouldNotChange() {
        String result = digitScrubber.scrub("Price is 1111$");
        assertEquals("Price is 1111$", result);
    }

    @Test
    void testScrubDigits_MixedInput() {
        String result = digitScrubber.scrub("Call 123 and price 50$");
        assertEquals("Call XXX and price 50$", result);
    }

    @Test
    void testScrubDigits_NullInput_ShouldThrow() {
        assertThrows(NullPointerException.class, () -> {
            digitScrubber.scrub(null);
        });
    }

    @Test
    void testScrubDigits_BlankInput_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            digitScrubber.scrub("   ");
        });
    }
}