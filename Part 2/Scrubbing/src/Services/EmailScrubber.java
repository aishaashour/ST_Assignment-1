package Services;

import Interfaces.IScrubEmails;

public class EmailScrubber implements IScrubEmails {
    @Override
    public String scrub(String input) {
        //wrong code (this ignores the requirement of throwing IllegalArgumentException when string is blank
//        if (input == null || input.isBlank()) {
//            throw new NullPointerException("Input cannot be null or blank");
//        }
        if (input == null) {
            throw new NullPointerException("Input cannot be null");          //NullPointerException id string is null
        }
        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be blank");       //IllegalArgumentException if string is blank
        }
        //not 0-0 it's 0-9
        return input == null ? "" : input.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}", "[EMAIL_HIDDEN]");
    }
}
