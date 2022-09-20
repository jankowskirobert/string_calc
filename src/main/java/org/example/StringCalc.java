package org.example;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalc {

    private static final String DEFAULT_DELIMITER = "\\r?\\n|\\r|,";
    private static final String DELIMITER_PREFIX = "//";
    private static final String OPEN_PAR = "\\[";
    private static final String CLOSE_PAR = "\\]";
    private static final String DELIMITER_SUFFIX = "\n";
    private static final Pattern multipleCharsPattern = Pattern.compile(multipleCharDelimiterPattern(), Pattern.CASE_INSENSITIVE);
    private static final Pattern optionalDelimiterPattern = Pattern.compile(optionalDelimiterPattern(), Pattern.CASE_INSENSITIVE);
    private static final int IGNORE_THRESHOLD = 1000;

    public static int add(String numbers) {
        if (isBlank(numbers)) {
            return 0;
        }
        Matcher matcher = optionalDelimiterPattern.matcher(numbers);
        String[] values = getSplitValues(numbers, matcher);
        if (containsBlankValues(values)) {
            throw new IllegalArgumentException("Delimiters without leading values are not allowed");
        }
        List<Integer> preparedValues = prepareValuesToCalculation(values);
        containsNegativeValues(preparedValues);
        return preparedValues.stream()
                .reduce(Integer::sum)
                .orElseThrow(() -> new NumberFormatException("Could not sum provided arguments"));
    }

    private static String[] getSplitValues(String numbers, Matcher matcher) {
        String toCalculate = numbers;
        String delimiter = DEFAULT_DELIMITER;
        if (numbers.startsWith(DELIMITER_PREFIX)) {
            if (matcher.find()) {
                delimiter = matcher.group(1);
                Matcher multipleCharsMatcher = multipleCharsPattern.matcher(delimiter);
                if(multipleCharsMatcher.find()) {
                    delimiter = multipleCharsMatcher.group(1);
                }
                toCalculate = matcher.group(2);
            } else {
                throw new IllegalArgumentException("Wrong format of delimiter missing new line symbol");
            }
        }
        return toCalculate.split(delimiter);
    }

    private static List<Integer> prepareValuesToCalculation(String[] values) {
        return Stream.of(values)
                .map(String::trim)
                .filter(StringCalc::nonEmpty)
                .map(Integer::parseInt)
                .filter(i -> i <= IGNORE_THRESHOLD)
                .collect(Collectors.toList());
    }

    private static void containsNegativeValues(List<Integer> numbers) {
        List<Integer> negativeValues = numbers.stream().filter(i -> i < 0).collect(Collectors.toList());
        if(!negativeValues.isEmpty()) {
            throw new IllegalArgumentException(String.format("Negatives not allowed %s", negativeValues));
        }
    }

    private static String optionalDelimiterPattern() {
        return "^" + DELIMITER_PREFIX  + "(.*)" + DELIMITER_SUFFIX + "(.*)";
    }

    private static String multipleCharDelimiterPattern() {
        return "^" + OPEN_PAR + "(.*)" +CLOSE_PAR+"$" ;
    }

    private static boolean containsBlankValues(String[] values) {
        return Stream.of(values)
                .map(String::trim)
                .anyMatch(String::isEmpty);
    }

    private static boolean nonEmpty(String number) {
        return number != null && !number.isEmpty();
    }

    private static boolean isBlank(String args) {
        return args == null || args.isEmpty();
    }

}
