package com.jenkins;

// A simple demo class for Jenkins CI/CD pipeline
public class HelloJenkins {

    public static void main(String[] args) {
        System.out.println("Hello, Jenkins CI/CD!");
        HelloJenkins hello = new HelloJenkins();

        // Call greeting methods
        System.out.println(hello.greet());
        hello.printInfo();

        // Simulate a small task
        int result = hello.add(5, 10);
        System.out.println("5 + 10 = " + result);

        // Simulate testable logic
        if (hello.isEven(result)) {
            System.out.println(result + " is even.");
        } else {
            System.out.println(result + " is odd.");
        }

        // Say goodbye
        hello.farewell();
    }

    // Returns a greeting message
    public String greet() {
        return "Hello from Jenkins!";
    }

    // Prints extra information
    public void printInfo() {
        System.out.println("This is a sample Java application for CI/CD testing.");
        System.out.println("It includes build, test, and SonarQube stages.");
    }

    // Adds two numbers
    public int add(int a, int b) {
        return a + b;
    }

    // Checks if a number is even
    public boolean isEven(int num) {
        return num % 2 == 0;
    }

    // Farewell method
    public void farewell() {
        System.out.println("Goodbye from Jenkins job.");
    }
}
