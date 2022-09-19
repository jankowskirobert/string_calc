package org.example;

import java.util.stream.Stream;

public class StringCalc {
    //Create a simple String calculator with a method int Add(string numbers)

    public static int add(String numbers) {
        if(hasBlankArgs(numbers)) {
            return 0;
        }
        String[] values = numbers.split(",");
        return Stream.of(values)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElseThrow(()-> new NumberFormatException("Could not sum provided arguments"));
    }


    private static boolean hasBlankArgs(String args) {
        return args == null || args.isEmpty();
    }

}
