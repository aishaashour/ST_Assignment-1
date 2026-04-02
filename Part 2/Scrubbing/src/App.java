import Interfaces.IScrubDigits;
import Services.DigitScrubber;
import Services.EmailScrubber;
import Interfaces.IScrubEmails;

public class App {
    public static void main(String[] args) throws Exception {
        IScrubEmails emailScrubber = new EmailScrubber();
        IScrubDigits digitScrubber = new DigitScrubber();
        System.out.println(emailScrubber.scrub("Contact me at john.doe@example.com"));
        System.out.println(digitScrubber.scrub("Contact me at 1111"));
        System.out.println(digitScrubber.scrub("Contact me at 1111$"));


    }
}
