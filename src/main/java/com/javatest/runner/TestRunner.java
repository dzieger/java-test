package com.javatest.runner;

import com.javatest.annotations.*;
import com.javatest.core.TestDiscovery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {

    public TestDiscovery discovery;

    public TestRunner(){
        discovery = TestDiscovery.getInstance();
    }

    public Map<Class<?>, Map<Class<? extends Annotation>, List<Method>>> getTests(){
        return discovery.getTestClasses();
    }

    public void runTests(String rootPackage) {
        System.out.println("(TestRunner.class) Running tests...");
        discovery.discoverTests(rootPackage);
        Map<Class<?>, Map<Class<? extends Annotation>, List<Method>>> tests = getTests();
        List<Queue<Method>> methodQueuesPerClass = new ArrayList<>();
        for (Map.Entry<Class<?>, Map<Class<? extends Annotation>, List<Method>>> entry : tests.entrySet()) {
            methodQueuesPerClass.add(createClassMethodQueue(entry.getValue()));
            System.out.println("Class: " + entry.getKey().getName());
        }
        System.out.println("Test method Queues created: " + methodQueuesPerClass.size());
        runTestMethods(methodQueuesPerClass);
    }

    private void runTestMethods(List<Queue<Method>> methodQueuesPerClass) {
        System.out.println("(TestRunner.class) Running test methods...");
        for (Queue<Method> methodQueue : methodQueuesPerClass) {
            Object testInstance = null;

            while (!methodQueue.isEmpty()) {
                Method method = methodQueue.poll();
                try {
                    if (Modifier.isStatic(method.getModifiers())) {
                        if (testInstance == null) {
                            System.out.println("Creating new instance of test class: " + method);
                            testInstance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                        }
                        method.invoke(testInstance);
                        System.out.println("Test passed: " + method.getName());
                    } else {
                        method.invoke(null);
                        System.out.println("Test passed: " + method.getName());
                    }
                } catch (Exception e) {
                    System.out.println("Test failed: " + method.getName());
                }
            }
        }
    }

    private Queue<Method> createClassMethodQueue(Map<Class<? extends Annotation>, List<Method>> tests) {
        Queue<Method> methodQueue = new LinkedList<>();

        if (tests.containsKey(BeforeAll.class)) {
            methodQueue.addAll(tests.get(BeforeAll.class));
        }
        if (tests.containsKey(Test.class)) {
            for (int i = 0; i < tests.get(Test.class).size(); i++) {
                if (tests.containsKey(BeforeEach.class)) {
                    methodQueue.addAll(tests.get(BeforeEach.class));
                }
                methodQueue.add(tests.get(Test.class).get(i));
                if (tests.containsKey(AfterEach.class)) {
                    methodQueue.addAll(tests.get(AfterEach.class));
                }
            }
        }
        if (tests.containsKey(AfterAll.class)) {
            methodQueue.addAll(tests.get(AfterAll.class));
        }
        return methodQueue;
    }

}