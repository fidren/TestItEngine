import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestItEngine {

    private final String className;
    private static final Path TEST_IT_NAME_FILE_PATH = Paths.get("TestItName.txt").toAbsolutePath();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify test class name");
            System.exit(-1);
        }
        String className = args[0].trim();
        printNameTestIt();
        System.out.printf("Testing class: %s\n", className);
        TestItEngine engine = new TestItEngine(className);
        engine.runTests();
    }

    private static void printNameTestIt() {
        try {
            System.out.println("Current working directory: " + Paths.get("").toAbsolutePath());
            List<String> lines = Files.readAllLines(TEST_IT_NAME_FILE_PATH);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TestItEngine(String className) {
        this.className = className;
    }

    public void runTests() {
        final Object unit = getObject(className);
        List<Method> testMethods = getTestMethods(unit);
        int passCount = 0;
        int failCount = 0;
        int errorCount = 0;
        for (Method m: testMethods) {
            TestResult result = launchSingleMethod(m, unit);
            switch (result) {
                case PASS -> passCount++;
                case FAIL -> failCount++;
                case ERROR -> errorCount++;
            }
        }
        System.out.printf("Engine launched %d tests.\n", testMethods.size());
        System.out.printf("%d of them passed, %d failed, %d finished with error.\n", passCount, failCount, errorCount);
        printIndicator(passCount, testMethods.size(), "Pass  -> ");
        printIndicator(failCount, testMethods.size(), "Fail  -> ");
        printIndicator(errorCount, testMethods.size(), "error -> ");
    }

    private void printIndicator(int count, int totalCount, String message) {
        char charToPrint = '#';
        System.out.print(message);
        int i = 0;
        while (i < count) {
            System.out.print(charToPrint);
            i++;
        }
        charToPrint = '=';
        while (i < totalCount) {
            System.out.print(charToPrint);
            i++;
        }
        System.out.println();
    }

    private TestResult launchSingleMethod(Method m, Object unit) {
        TestIt annotation = m.getAnnotation(TestIt.class);
        Class<? extends Throwable> expectedException = annotation.expectedException();
        String[] rawParams = annotation.params();
        String expectedValue = annotation.expectedValue();

        if(!expectedValue.isEmpty() && expectedException != TestIt.None.class)
            return TestResult.ERROR;

        try {
            Parameter[] methodParameters = m.getParameters();
            Object[] arguments;

            if (rawParams.length == 0) {
                m.invoke(unit);
            } else if (expectedValue.isEmpty() && expectedException == TestIt.None.class) {
                if (rawParams.length != methodParameters.length) return TestResult.ERROR;
                arguments = prepareArguments(rawParams, methodParameters);
                m.invoke(unit, arguments);
            } else if (expectedValue.isEmpty()) {
                if ((rawParams.length + 1) != methodParameters.length) return TestResult.ERROR;
                arguments = prepareArgumentsWithExpectedException(rawParams, methodParameters);
                m.invoke(unit, arguments);
            } else {
                if ((rawParams.length + 1) != methodParameters.length) return TestResult.ERROR;
                arguments = prepareArgumentsWithExpectedValue(rawParams, methodParameters, expectedValue);
                m.invoke(unit, arguments);
            }
            System.out.println("Tested method: " + m.getName() + " test successful.");
            return TestResult.PASS;
        } catch (Throwable e) {
            return handleException(e, expectedException, m.getName());
        }
    }

    private TestResult handleException(Throwable e, Class<? extends Throwable> expectedException, String methodName) {
        Throwable cause = e.getCause();
        Throwable toCheck = (cause != null) ? cause : e;

        if(toCheck instanceof AssertionError) {
            System.out.println("Tested method: " + methodName + " test failed.");
            return TestResult.FAIL;
        }

        if(expectedException != TestIt.None.class) {
            if(expectedException.isAssignableFrom(toCheck.getClass())) {
                System.out.println("Tested method: " + methodName + " threw the expected exception: " + toCheck);
                return TestResult.PASS;
            } else {
                System.out.println("Tested method: " + methodName + " failed. Unexpected exception: " + toCheck);
                return TestResult.ERROR;
            }
        }

        e.printStackTrace();
        System.out.println("Tested method: " + methodName + " failed. Exception thrown: " + toCheck);
        return TestResult.ERROR;
    }

    private Object[] prepareArgumentsWithExpectedValue(String[] rawParams, Parameter[] methodParameters, String expectedValue) {
        Object[] values = new Object[rawParams.length + 1];
        values[0] = convertToParameter(expectedValue, methodParameters[0]);
        for (int i = 1; i < (rawParams.length + 1); i++) {
            values[i] = convertToParameter(rawParams[i-1], methodParameters[i]);
        }
        return values;
    }

    private Object[] prepareArgumentsWithExpectedException(String[] rawParams, Parameter[] methodParameters) {
        Object[] values = new Object[rawParams.length + 1];
        values[0] = defaultValue(methodParameters[0]);
        for (int i = 1; i < (rawParams.length + 1); i++) {
            values[i] = convertToParameter(rawParams[i-1], methodParameters[i]);
        }
        return values;
    }

    private Object[] prepareArguments(String[] rawParams, Parameter[] methodParameters) {
        Object[] values = new Object[rawParams.length];
        for (int i = 0; i < rawParams.length; i++) {
            values[i] = convertToParameter(rawParams[i], methodParameters[i]);
        }
        return values;
    }

    private Object defaultValue(Parameter methodParameter) {
        Class<?> type = methodParameter.getType();

        if (type.isPrimitive()) {
            if (type.equals(int.class)) return 0;
            if (type.equals(long.class)) return 0L;
            if (type.equals(double.class)) return 0.0;
            if (type.equals(float.class)) return 0.0f;
            if (type.equals(boolean.class)) return false;
            if (type.equals(char.class)) return '\u0000';
            if (type.equals(byte.class)) return (byte) 0;
            if (type.equals(short.class)) return (short) 0;
        }
        return null;
    }

    private Object convertToParameter(String string, Parameter parameter) {
        Class<?> type = parameter.getType();
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(string);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(string);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(string);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(string);
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(string);
        } else if (type.equals(char.class) || type.equals(Character.class)) {
            if (string.length() != 1) {
                throw new IllegalArgumentException("Cannot convert to char: " + string);
            }
            return string.charAt(0);
        } else if (type.equals(String.class)) {
            if(string.equals("null")) {
                return null;
            }
            return string;
        } else {
            throw new UnsupportedOperationException("Unsupported parameter type: " + type.getName());
        }
    }

    private static List<Method> getTestMethods(Object unit) {
        Method[] methods = unit.getClass().getDeclaredMethods();
        return Arrays.stream(methods).filter(
                m -> m.getAnnotation(TestIt.class) != null).collect(Collectors.toList());
    }

    private static Object getObject(String className) {
        try {
            Class<?> unitClass = Class.forName(className);
            return unitClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new Object();
        }
    }
}
