public class MyBeautifulTestSuite {
    @TestIt
    public void imFailue() {
        System.out.println("I AM EVIL.");
        throw new NullPointerException();
    }

    @TestIt(params = {"1", "2"},
            expectedValue = "3")
    public void addTwoNumbersBothPositiveTest(int expectedValue, int a, int b) {
        int result = Example.add(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"4", "0"},
            expectedValue = "4")
    public void addTwoNumbersOneZeroTest(int expectedValue, int a, int b) {
        int result = Example.add(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-4", "-5"},
            expectedValue = "-9")
    public void addTwoNumbersBothNegativeTest(int expectedValue, int a, int b) {
        int result = Example.add(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"1", "2"},
            expectedValue = "0")
    public void divideTwoNumbersPassTest(int expectedValue, int a, int b) {
        int result = Example.divide(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-4", "-5"},
            expectedValue = "1")
    public void divideTwoNumbersFailTest(int expectedValue, int a, int b) {
        int result = Example.divide(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"4", "0"},
            expectedValue = "0")
    public void divideTwoNumbersErrorTest(int expectedValue, int a, int b) {
        int result = Example.divide(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"4", "0"},
            expectedException = ArithmeticException.class)
    public void divideTwoNumbersThrowExceptionTest(int expectedValue, int a, int b) {
        int result = Example.divide(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"1"},
            expectedValue = "false")
    public void oddNumberIsEvenTest(boolean expectedValue, int a) {
        boolean result = Example.numberIsEven(a);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"4"},
            expectedValue = "true")
    public void evenNumberIsEvenTest(boolean expectedValue, int a) {
        boolean result = Example.numberIsEven(a);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-4"},
            expectedValue = "true")
    public void negativeEvenNumberIsEvenTest(boolean expectedValue, int a) {
        boolean result = Example.numberIsEven(a);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-3"},
            expectedValue = "true")
    public void negativeOddNumberIsEvenFailTest(boolean expectedValue, int a) {
        boolean result = Example.numberIsEven(a);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"1", "1"},
            expectedValue = "11")
    public void concatenateTwoNumbersStringsTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"a", "b"},
            expectedValue = "ab")
    public void concatenateTwoStringsTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"11@##@!$", "#@!#@!"},
            expectedValue = "11@##@!$#@!#@!")
    public void concatenateTwoStringsDifferentCharsTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"", "b"},
            expectedValue = "b")
    public void concatenateTwoStringsOneEmptyTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {" ", "b"},
            expectedValue = "b")
    public void concatenateTwoStringsOneSpaceBarFailTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"null", "a"},
            expectedValue = "a")
    public void concatenateTwoStringsOneNullTest(String expectedValue, String a, String b) {
        String result = Example.concatenateTwoStrings(a, b);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"1.0"},
            expectedValue = "3.0")
    public void calculateAreaOfCirclePassTest(double expectedValue, double radius) {
        double result = Example.calculateAreaOfCircle(radius);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"1.12"},
            expectedValue = "2.0")
    public void calculateAreaOfCircleFailTest(double expectedValue, double radius) {
        double result = Example.calculateAreaOfCircle(radius);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"0.0"},
            expectedValue = "0.0")
    public void calculateAreaOfCircleRadiusZeroTest(double expectedValue, double radius) {
        double result = Example.calculateAreaOfCircle(radius);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-4.0"},
            expectedValue = "0.0")
    public void calculateAreaOfCircleNegativeRadiusTest(double expectedValue, double radius) {
        double result = Example.calculateAreaOfCircle(radius);
        Assertions.assertEquals(result, expectedValue);
    }

    @TestIt(params = {"-4.0"},
            expectedException = IllegalArgumentException.class)
    public void calculateAreaOfCircleThrowExceptionTest(double expectedValue, double radius) {
        double result = Example.calculateAreaOfCircle(radius);
        Assertions.assertEquals(result, expectedValue);
    }
}
