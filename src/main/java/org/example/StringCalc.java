package org.example;

import java.util.stream.Stream;

public class StringCalc {

    public static int add(String numbers) {
        if (isBlank(numbers)) {
            return 0;
        }
        String[] values = numbers.split("\\r?\\n|\\r|,");
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
