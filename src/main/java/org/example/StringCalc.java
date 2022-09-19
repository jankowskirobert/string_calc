package org.example;

import java.util.stream.Stream;

public class StringCalc {
    //Create a simple String calculator with a method int Add(string numbers)

    /**
     *
     * @param args
     * @return
     */
    public static int add(String numbers) {
        if(hasBlankArgs(numbers)) {
            return 0;
        }

        return Integer.parseInt(numbers);
    }


    private static boolean hasBlankArgs(String args) {
        return args == null || args.isEmpty();
    }

}
