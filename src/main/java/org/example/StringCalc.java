package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringCalc {

    private static final String DEFAULT_DELIMITER = "\\r?\\n|\\r|,";
    private static final String DELIMITER_PREFIX = "//";
    private static final String DELIMITER_SUFFIX = "\n";
    private static final Pattern pattern = Pattern.compile(optionalDelimiterPattern(), Pattern.CASE_INSENSITIVE);

    public static int add(String numbers) {
        if (isBlank(numbers)) {
            return 0;
        }
        Matcher matcher = pattern.matcher(numbers);
        String toCalculate = numbers;
        String delimiter = DEFAULT_DELIMITER;
        if (numbers.startsWith(DELIMITER_PREFIX)) {
            if (matcher.find()) {
                String group = matcher.group();
                delimiter = group.substring(DELIMITER_PREFIX.length(), group.length() - DELIMITER_SUFFIX.length());
                toCalculate = removeOptionalDelimiter(numbers, toCalculate, group);
            } else {
                throw new IllegalArgumentException("Wrong format of delimiter missing new line symbol");
            }
        }
        String[] values = toCalculate.split(delimiter);
        if (containsBlankValues(values)) {
            throw new IllegalArgumentException("Delimiters without leading values are not allowed");
        }
        return Stream.of(values)
                .map(String::trim)
                .filter(StringCalc::nonEmpty)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElseThrow(() -> new NumberFormatException("Could not sum provided arguments"));
    }

    private static String removeOptionalDelimiter(String numbers, String toCalculate, String group) {
        if (numbers.startsWith(group)) {
            toCalculate = numbers.substring(group.length());
        }
        return toCalculate;
    }

    private static String optionalDelimiterPattern() {
        return "^" + DELIMITER_PREFIX + ".*" + DELIMITER_SUFFIX;
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
