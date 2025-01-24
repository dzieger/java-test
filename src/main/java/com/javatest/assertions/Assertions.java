package com.javatest.assertions;

public class Assertions {

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + " but got: " + actual);
        } else {
            System.out.println("Test passed");
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected: true but got: false");
        } else {
            System.out.println("Test passed");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Expected: false but got: true");
        } else {
            System.out.println("Test passed");
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected: not null but got: null");
        } else {
            System.out.println("Test passed");
        }
    }

    public static void assertNull(Object object) {
        if (object != null) {
            throw new AssertionError("Expected: null but got: not null");
        } else {
            System.out.println("Test passed");
        }
    }

}
