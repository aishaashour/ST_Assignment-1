package tests;
import Interfaces.IScrubDigits;
import Interfaces.IScrubEmails;
import Services.MainScrubber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static Models.ScrubMode.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainScrubberTest {
    IScrubDigits mockDigits;
    IScrubEmails mockEmails;

    @BeforeEach
    public void setUp() {
        mockDigits = mock(IScrubDigits.class);    //mock for digit scrubber
        mockEmails = mock(IScrubEmails.class);     //mock for email scrubber
    }


    //test the behavior of the main scrubber using mockito (to test it in isolation of its dependances)
    @Test
    void testDigitsMode() {
        when(mockDigits.scrub("1234")).thenReturn("XXXX");

        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("1234", ONLY_DIGITS);

        assertEquals("XXXX", result);
        verify(mockDigits, times(1)).scrub("1234");         //digits mock used
        verify(mockEmails, never()).scrub(anyString());        //emails mock never used
    }

    @Test
    void testEmailsMode() {
        when(mockEmails.scrub("shazawagdy@gmail.com")).thenReturn("[EMAIL_HIDDEN]");
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("shazawagdy@gmail.com", ONLY_EMAILS);

        assertEquals("[EMAIL_HIDDEN]", result);
        verify(mockEmails , times(1)).scrub("shazawagdy@gmail.com");      //used once
        verify(mockDigits, never()).scrub(anyString());    //never used
    }

    @Test
    void testFullScrubbingMode() {

        when(mockDigits.scrub("shazawagdy@gmail.com 1234")).thenReturn("shazawagdy@gmail.com XXXX");
        when(mockEmails.scrub("shazawagdy@gmail.com XXXX")).thenReturn("[EMAIL_HIDDEN] XXXX");

        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);

        String result = scrubber.scrub("shazawagdy@gmail.com 1234", FULL_SCRUBBING);

        assertEquals("[EMAIL_HIDDEN] XXXX", result);

        verify(mockDigits, times(1)).scrub("shazawagdy@gmail.com 1234");
        verify(mockEmails, times(1)).scrub("shazawagdy@gmail.com XXXX");
    }

    //will fail because class doesn't throw exceptions , it only returns null
    @Test
    void testNullInput_ShouldThrow() {
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);
        assertThrows(NullPointerException.class, () -> {scrubber.scrub(null, ONLY_DIGITS);});
    }

    @Test
    void testBlankInput_ShouldThrow() {
        MainScrubber scrubber = new MainScrubber(mockDigits, mockEmails);
        assertThrows(IllegalArgumentException.class, () -> {scrubber.scrub("   ", ONLY_DIGITS);});
    }
}