# Java Test

## What is it?

  To better understand and master core Java concepts, I built this porject to be my own implementation of a testing framework similar to JUnit. I do not plan to develop it to a point where it would be widely used. It is just a project that I am using to develop my skills in Java.

## What can you do with it?

  Similar to JUnit, you can write tests and identify them with the @Test annotation. Other annotations include @BeforeEach, @BeforeAll, @AfterEach, and @AfterAll. Within @Test annotated methods, you can call any of the follwoing assertions:
  - Assertions.assertEquals(Object, Object); - compares two objexts with .equals().
  - Assertions.assertTrue(boolean);
  - Assertions.assertFalse(boolean);
  - Assertions.assertNotNull(Object);
  - Assertions.assertNull(Object);

  Each one of those assertions, is governed by an if/else statement. If that statement is true, it simply prints test passed. If it is false, it will throw an AssertionError from java.lang with a message of what was expected vs what was received.

## How it works?

  The main method in the JavaTest class instantiates a new TestRunner instance and calls the runTests method with a given package name of "target/test-classes";
  ** Note that the Test classes have to have been compiled and placed in the target/test-classes directory for tests to be discovered **

  During construction of the TestRunner class, a singleton of the TestDiscovery class is created and accessed in the TestRunner class. The runTests method which takes a String containing the rootPackage calls the discovery.discoverTests(rootPackage); method to disocver all tests in the project. 

  The TestDisocvery class will recursively locate all class files, given that they exist, or state that the class or directory do not exist.

  Once a class file is located, the TestDiscovery class will then check if the class contains any annotation included as part of the framework. It will then store in a Map, the class name of class containing the provided annotations, and another Map that links methods to their annotation type. For example, the TestClass key will hold a map containing the following: `BeforeEach.class`, List of all methods with that BeforeEach annotation. Then a sperate Key in the map for Test, etc ...
  
  The Map<Class, Map<Class, List<Method>>> is returned to the TestRunner class. The TestRunner then proceeds to create Queues for each class found in the discovery, and add methods based on their Annotation. Before all is placed at the top of the queue, BeforeEach is placed before every Test, Tests are placed, After Each is palced after every test, and AfterAll is placed after everything else has been completed.

  The TestRunner class then loops through each Queue, and subsequently each list methods, invoking them with null for static methods or creating a testInstance to invoke them on if they are not sttaic methods.

# Issues

  - Currently, all test methods must be static. Creating an instance is not working properly.
  - The project must be compiled first, and the tests run using a CLI command of `java -classpath "target\classes;target\test-classes" com.javatest.JavaTest`



