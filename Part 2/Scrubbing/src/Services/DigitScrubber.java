package Services;

import Interfaces.IScrubDigits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitScrubber implements IScrubDigits {
    @Override
    public String scrub(String input) throws IllegalArgumentException, NullPointerException {
        if (input == null) {
            throw new NullPointerException("Input cannot be null");
        }
        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be blank");
        }
        //wrong code: does not ignore prices (numbers before the $)
        return input.replaceAll("\\d", "X");

//        //right code
////        return input.replaceAll("\\d+(?!\\$)", match -> match.group().replaceAll(".", "X"));
//        Pattern pattern = Pattern.compile("\\b\\d+\\b(?!\\$)");
//        Matcher matcher = pattern.matcher(input);
//
//        StringBuffer result = new StringBuffer();
//
//        while (matcher.find()) {
//            String digits = matcher.group();
//            String replacement = digits.replaceAll(".", "X");
//            matcher.appendReplacement(result, replacement);
//        }
//
//        matcher.appendTail(result);
//
//        return result.toString();
    }
}