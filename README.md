# TestIt Testing Engine

A lightweight Java-based testing engine designed to verify test results with three possible outcomes:

* **PASS** - The test completed, and the result is correct.
* **FAIL** - The test completed, but the result is incorrect.
* **ERROR** - An uncaught exception (java.lang.Exception) occurred.

## Running Tests

Tests are executed using a Python script:

```bash
python run.py [class names to test]
```

### Example

```bash
python run.py MyBeautifulTestSuite
```

## Testing Scenarios

Different ways to test based on the following annotation:

1. Check if the result matches the expected value.
2. Check if the expected exception was thrown.
3. Check if an unhandled exception occurred.

## Author
**Wojciech Mikula** - project creator