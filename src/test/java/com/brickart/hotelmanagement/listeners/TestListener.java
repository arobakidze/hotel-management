package com.brickart.hotelmanagement.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("STARTING: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long durationMs = result.getEndMillis() - result.getStartMillis();
        System.out.println("PASSED: " + result.getName() + " (" + durationMs + " ms)");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long durationMs = result.getEndMillis() - result.getStartMillis();
        System.out.println("FAILED: " + result.getName() + " (" + durationMs + " ms)");
        if (result.getThrowable() != null) {
            System.out.println("  Reason: " + result.getThrowable().getMessage());
        }
    }
}
