package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*

Start with the simplest test case of an empty string and move to 1 and two numbers
Remember to solve things as simply as possible so that you force yourself to write tests you did not think about
Remember to refactor after each passing test
Allow the Add method to handle an unknown amount of numbers
Allow the Add method to handle new lines between numbers (instead of commas).
the following input is ok:  “1\n2,3”  (will equal 6)
the following input is NOT ok:  “1,\n” (not need to prove it - just clarifying)
Support different delimiters
to change a delimiter, the beginning of the string will contain a separate line that looks like this:   “//[delimiter]\n[numbers…]” for example “//;\n1;2” should return three where the default delimiter is ‘;’ .
the first line is optional. all existing scenarios should still be supported
Calling Add with a negative number will throw an exception “negatives not allowed” - and the negative that was passed.if there are multiple negatives, show all of them in the exception message
Numbers bigger than 1000 should be ignored, so adding 2 + 1001  = 2
Delimiters can be of any length with the following format:  “//[delimiter]\n” for example: “//[***]\n1***2***3” should return 6
Allow multiple delimiters like this:  “//[delim1][delim2]\n” for example “//[*][%]\n1*2%3” should return 6.
make sure you can also handle multiple delimiters with length longer than one char

 */
class StringCalcTest {

    @Test
    public void shouldReturnZeroIfNullArguments() {
        int sum = StringCalc.add(null);
        assertEquals(0, sum);
    }

    @Test
    public void shouldReturnZeroIfEmptyArgument() {
        int sum = StringCalc.add("");
        assertEquals(0, sum);
    }

    @Test
    public void shouldReturnSumOfArguments() {
        String toBeSummed = "2";
        int sum = StringCalc.add(toBeSummed);
        assertEquals(2, sum);
    }

    @Test
    public void shouldThrowExceptionIfCannotParseArgument() {
        String toBeSummed = "$%^";
        assertThrows(NumberFormatException.class, () -> StringCalc.add(toBeSummed));
    }


}