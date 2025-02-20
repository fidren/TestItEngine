public class  Example {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }

    public static boolean numberIsEven(int a) {
        return a % 2 == 0;
    }

    public static String concatenateTwoStrings(String a, String b) {
        return a + b;
    }

    public static double calculateAreaOfCircle(double radius) {
        if (radius < 0) throw new IllegalArgumentException();
        return Math.round(Math.PI * radius * radius);
    }
}
