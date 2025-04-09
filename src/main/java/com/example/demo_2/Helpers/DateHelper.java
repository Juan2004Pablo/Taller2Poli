package com.example.demo_2.Helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static String generateTestString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return "TEST_" + now.format(formatter);
    }
}
