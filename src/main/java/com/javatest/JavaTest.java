package com.javatest;

import com.javatest.runner.TestRunner;

public class JavaTest {

    public static void main(String[] args) {

        System.out.println("Running tests...");
        TestRunner runner = new TestRunner();
        runner.runTests("target/test-classes");
        System.out.println("Tests complete.");

    }

}
