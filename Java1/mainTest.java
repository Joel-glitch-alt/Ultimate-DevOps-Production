package com.jenkins;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloJenkinsTest {
    @Test
    public void testGreet() {
        assertEquals("Hello from Jenkins!", main.greet());
    }
}
