package com.example;

public class App {
    public static void main(String[] args) {
        // This main class simply calls our Scraper
        // Run this with: mvn exec:java -Dexec.mainClass="com.example.App"
        try {
            Scraper.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

