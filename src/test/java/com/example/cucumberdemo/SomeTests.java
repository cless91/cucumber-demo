package com.example.cucumberdemo;

import org.junit.jupiter.api.Test;

public class SomeTests {
    @Test
    void name() {
        System.out.println("https://www.facebook.com/login/".matches("https://www\\.facebook\\.com/login.*"));
//        System.out.println("https://www.facebook.com/login/".equals("https://www.facebook.com/login"));
    }
}
