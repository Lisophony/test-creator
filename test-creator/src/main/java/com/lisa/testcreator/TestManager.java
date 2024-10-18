package com.lisa.testcreator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestManager {
    EventListener testSelectedListener;
    private List<Test> tests;
    private int selectedTestId;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setTestSelectedListener(EventListener testSelectedListener) {
        this.testSelectedListener = testSelectedListener;
    }


    public void loadTests(String path) {
        File directory = new File(path);
        File[] testFiles = directory.listFiles();
        tests = new ArrayList<>();
        if(testFiles != null) {
            for (File testfile : testFiles) {
                try {
                    tests.add(objectMapper.readValue(testfile, Test.class));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public Test getTest(int id) {
        return tests.get(id);
    }
    public List<Test> getTests() {
        return tests;
    }

    public void selectTest(int testId) {
        selectedTestId = testId;
        if(testSelectedListener != null) {
            testSelectedListener.onTriggered();
        }
    }
}
