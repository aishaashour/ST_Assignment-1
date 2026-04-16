import Interfaces.IScrub;
import Interfaces.IScrubDigits;
import Services.DigitScrubber;
import Services.EmailScrubber;
import Interfaces.IScrubEmails;
import Services.MainScrubber;

import static Models.ScrubMode.ONLY_DIGITS;

public class App {
    public static void main(String[] args) throws Exception {
        IScrubEmails emailScrubber = new EmailScrubber();
        IScrubDigits digitScrubber = new DigitScrubber();
        IScrub scrubber = new MainScrubber(digitScrubber,emailScrubber);
        System.out.println(emailScrubber.scrub("Contact me at john.doe@example.com"));
        System.out.println(digitScrubber.scrub("Contact me at 1111"));
        System.out.println(digitScrubber.scrub("Contact me at 1111$"));
        System.out.println(scrubber.scrub("Contact me at 1111",ONLY_DIGITS));
        System.out.println(emailScrubber.scrub("Contact me at john.doe0@example.com"));
        System.out.println(emailScrubber.scrub("Contact me at john.doe9@example.com"));



    }
}
