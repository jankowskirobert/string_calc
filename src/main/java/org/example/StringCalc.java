package org.example;

import java.util.stream.Stream;

public class StringCalc {

    public static int add(String numbers) {
        if(hasBlankArgs(numbers)) {
            return 0;
        }
        String[] values = numbers.split("\\r?\\n|\\r|,");
        return Stream.of(values)
                .map(String::trim)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElseThrow(()-> new NumberFormatException("Could not sum provided arguments"));
    }


    private static boolean hasBlankArgs(String args) {
        return args == null || args.isEmpty();
    }

}
