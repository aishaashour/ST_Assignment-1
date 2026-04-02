package tests;
import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Services.MainScrubber;
import org.junit.jupiter.api.Test;
import static Models.ScrubMode.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainScrubberTest {

    @Test
    void testDigitsMode() {
        IScrubDigits mockDigits = mock(IScrubDigits.class);    //mock for digit scrubber
        IScrubEmails mockEmails = mock(IScrubEmails.class);     //mock for email scrubber

        when(mockDigits.scrub("test")).thenReturn("XXXX");
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("test", ONLY_DIGITS);
//        System.out.println("RESULT = " + result);
        assertEquals("XXXX", result);
        verify(mockDigits).scrub("test");         //digits mock used
        verify(mockEmails, never()).scrub(anyString());   //emails mock never used
    }

    @Test
    void testEmailsMode() {
        IScrubDigits mockDigits = mock(IScrubDigits.class);
        IScrubEmails mockEmails = mock(IScrubEmails.class);
        when(mockEmails.scrub("test")).thenReturn("[EMAIL_HIDDEN]");
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("test", ONLY_EMAILS);

        assertEquals("[EMAIL_HIDDEN]", result);
        verify(mockEmails).scrub("test");        //used
        verify(mockDigits, never()).scrub(anyString());    //never used
    }

    @Test
    void testFullScrubbingMode() {
        IScrubDigits mockDigits = mock(IScrubDigits.class);
        IScrubEmails mockEmails = mock(IScrubEmails.class);

        when(mockDigits.scrub("test")).thenReturn("XXXX");
        when(mockEmails.scrub("XXXX")).thenReturn("[EMAIL_HIDDEN]");
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("test", FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN]", result);

        verify(mockDigits, times(1)).scrub("test");
        verify(mockEmails, times(1)).scrub("XXXX");
    }

    //will fail because class doesn't throw exceptions , it only return null
    @Test
    void testNullInput_ShouldThrow() {
        IScrubDigits mockDigits = mock(IScrubDigits.class);
        IScrubEmails mockEmails = mock(IScrubEmails.class);
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        assertThrows(NullPointerException.class, () -> {scrubber.scrub(null, ONLY_DIGITS);});
    }

    @Test
    void testBlankInput_ShouldThrow() {
        IScrubDigits mockDigits = mock(IScrubDigits.class);
        IScrubEmails mockEmails = mock(IScrubEmails.class);
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        assertThrows(IllegalArgumentException.class, () -> {scrubber.scrub("   ", ONLY_DIGITS);});
    }
}