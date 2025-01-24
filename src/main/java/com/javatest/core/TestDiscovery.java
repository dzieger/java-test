package com.javatest.core;


import com.javatest.annotations.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestDiscovery {

    private static final TestDiscovery INSTANCE = new TestDiscovery();

    private TestDiscovery() {
    }

    public static TestDiscovery getInstance() {
        return INSTANCE;
    }

    private final Map<Class<?>, Map<Class<? extends Annotation>, List<Method>>> testClasses = new HashMap<>();

    public Map<Class<?>, Map<Class<? extends Annotation>, List<Method>>> getTestClasses() {
        return testClasses;
    }

    public void discoverTests(String rootPackage) {
        System.out.println("(TestDiscovery.class) Discovering tests...");
        // Discover all classes that implement RunnableTest
        // Discover all methods in those classes that are annotated with @Test

        File file = new File(rootPackage);

        if (!file.exists()) {
            System.out.println("Test directory does not exist");
        } else {
            findTestClasses(file);
        }
    }

    private void findTestClasses(File file) {
        try {
            for (File f : file.listFiles()) {
                System.out.println("File: " + f.getName());
                if (f.isDirectory()) {
                    findTestClasses(f);
                }
                if (f.getName().toLowerCase().endsWith(".class")) {
                    System.out.println("Class: " + f.getName());
                    String className = convertToClassName(f);
                    checkAndAddTestClass(className);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Test directory does not exist");
        }
    }

    private void checkAndAddTestClass(String className) {
        try {
            System.out.println("Checking class: " + className);
            Class<?> clazz = Class.forName(className);
            System.out.println("Found class: " + clazz.getName());
            Map<Class<? extends Annotation>, List<Method>> testMethods = new HashMap<>();
            testMethods.put(BeforeAll.class, new ArrayList<>());
            testMethods.put(Test.class, new ArrayList<>());
            testMethods.put(AfterAll.class, new ArrayList<>());
            testMethods.put(BeforeEach.class, new ArrayList<>());
            testMethods.put(AfterEach.class, new ArrayList<>());

            for (Method method : clazz.getDeclaredMethods()) {
                for (Class<? extends Annotation> annotation : testMethods.keySet()) {
                    if (method.isAnnotationPresent(annotation)) {
                        testMethods.get(annotation).add(method);
                        System.out.println("Added method: " + method.getName());
                    }
                }
            }

            if (testMethods.values().stream().anyMatch(list -> !list.isEmpty())) {
                testClasses.put(clazz, testMethods);
                System.out.println("Added class: " + clazz.getName());
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
        }
    }

    private String convertToClassName(File file) {
        String path = file.getPath();
        System.out.println("Path: " + path);

        // Strip the "target/test-classes" prefix
        String classPath = path.substring(path.indexOf("target/test-classes/") + "target/test-classes/".length() + 1, path.indexOf(".class"));
        System.out.println("Class path: " + classPath);

        // Replace file separators (e.g., / or \) with dots
        return classPath.replace(File.separatorChar, '.');
    }





}
