// package com.jenkins;

// import org.junit.Test;
// import static org.junit.Assert.*;

// public class HelloJenkinsTest {
//     @Test
//     public void testGreet() {
//         assertEquals("Hello from Jenkins!", main.greet());
//     }
// }


package com.jenkins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HelloJenkins class
 * Demonstrates comprehensive testing for Jenkins CI/CD pipeline
 */
class HelloJenkinsTest {

    private HelloJenkins helloJenkins;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        helloJenkins = new HelloJenkins();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Should return correct greeting message")
    void testGreet() {
        // Given & When
        String result = helloJenkins.greet();
        
        // Then
        assertEquals("Hello from Jenkins!", result);
        assertNotNull(result);
        assertTrue(result.contains("Jenkins"));
    }

    @ParameterizedTest
    @DisplayName("Should correctly add two numbers")
    @CsvSource({
        "1, 2, 3",
        "5, 10, 15", 
        "0, 0, 0",
        "-1, 1, 0",
        "100, 200, 300"
    })
    void testAdd(int a, int b, int expected) {
        // When
        int result = helloJenkins.add(a, b);
        
        // Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify even numbers")
    @ValueSource(ints = {0, 2, 4, 6, 8, 10, 100, -2, -4})
    void testIsEvenWithEvenNumbers(int number) {
        // When
        boolean result = helloJenkins.isEven(number);
        
        // Then
        assertTrue(result, number + " should be even");
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify odd numbers")
    @ValueSource(ints = {1, 3, 5, 7, 9, 11, 99, -1, -3})
    void testIsEvenWithOddNumbers(int number) {
        // When
        boolean result = helloJenkins.isEven(number);
        
        // Then
        assertFalse(result, number + " should be odd");
    }

    @Test
    @DisplayName("Should print correct info message")
    void testPrintInfo() {
        // When
        helloJenkins.printInfo();
        
        // Then
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("sample Java application"));
        assertTrue(output.contains("CI/CD testing"));
        assertTrue(output.contains("SonarQube"));
    }

    @Test
    @DisplayName("Should print farewell message")
    void testFarewell() {
        // When
        helloJenkins.farewell();
        
        // Then
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Goodbye from Jenkins job"));
    }

    @Test
    @DisplayName("Should handle main method execution without errors")
    void testMainMethod() {
        // When & Then (should not throw any exceptions)
        assertDoesNotThrow(() -> {
            HelloJenkins.main(new String[]{});
        });
    }

    @Test
    @DisplayName("Should demonstrate edge case handling for add method")
    void testAddEdgeCases() {
        // Test with maximum integer values
        assertEquals(Integer.MAX_VALUE, helloJenkins.add(Integer.MAX_VALUE, 0));
        assertEquals(Integer.MIN_VALUE, helloJenkins.add(Integer.MIN_VALUE, 0));
        
        // Test with large numbers (be careful of overflow)
        assertEquals(1000000, helloJenkins.add(500000, 500000));
    }

    @Test
    @DisplayName("Should handle zero correctly in isEven method")
    void testIsEvenWithZero() {
        // Zero should be considered even
        assertTrue(helloJenkins.isEven(0));
    }
}