package tests;
import Services.EmailScrubber;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailScrubberTest {
    private final EmailScrubber emailScrubber = new EmailScrubber();

    //test 1 : test noraml happy path , no exception and email doesn't have numbers
    @Test
    void testEmailScrub_NormalCase(){
        String email = emailScrubber.scrub("contact me at Shazawagdy@gmail.com");
        assertEquals("contact me at [EMAIL_HIDDEN]", email);
    }

    //test 2 : test if no emails, just normal text
    @Test
    void testScrubEmails_NoEmails() {
        String result = emailScrubber.scrub("Hello Shaza");
        assertEquals("Hello Shaza", result);
    }

    //test 3 :if more than one email
    @Test
    void testScrubEmails_MultipleEmails() {
        String result = emailScrubber.scrub("shaza@test.com and aisha@test.com");
        assertEquals("[EMAIL_HIDDEN] and [EMAIL_HIDDEN]", result);
    }

    //test 4: throwing null pointer exception if text is null
    @Test
    void testScrubEmails_NullInput_ShouldThrow() {
        assertThrows(NullPointerException.class, () -> {emailScrubber.scrub(null);});
    }

    //test 5 : throwing illegal argument exception if text is blank (will fail because code doesn't handle this requirement)
    @Test
    void testScrubEmails_BlankInput_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {emailScrubber.scrub("   ");} );
    }

    //test 5 : if email has numbers (either than 0) (will fail , there's a mistake in the regex of the email)
    @Test
    void testEmailScrub_EmailWithNumbers(){
        String email = emailScrubber.scrub("contact me at Shazawagdy2004@gmail.com");
        assertEquals("contact me at [EMAIL_HIDDEN]", email);
    }


}
