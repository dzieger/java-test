package com.javatest.tests;

import com.javatest.annotations.*;

public class TestClass {

    @BeforeEach
    public static void setup() {
        System.out.println("Setup");
    }

    @AfterEach
    public static void teardown() {
        System.out.println("Teardown");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("AfterAll");
    }


    @Test
    public static void test() {
        System.out.println("Test");
    }

    @Test
    public static void test2() {
        System.out.println("Test2");
    }

}