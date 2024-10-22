package com.lisa.testcreator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestManager {
    private EventListener testSelectedListener;
    private EventListener questionChangedListener;
    private EventListener testFinishedListener;
    private List<Test> tests;
    private int selectedTestId = -1;
    private int questionId = -1;
    private int points = 0;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setTestSelectedListener(EventListener testSelectedListener) {
        this.testSelectedListener = testSelectedListener;
    }

    public void setQuestionChangedListener(EventListener questionChangedListener) {
        this.questionChangedListener = questionChangedListener;
    }

    public void setTestFinishedListener(EventListener testFinishedListener) {
        this.testFinishedListener = testFinishedListener;
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

    public void startTest(int testId) {
        selectedTestId = testId;
        questionId = 0;
        if(testSelectedListener != null) {
            testSelectedListener.onTriggered();
        }
        if(questionChangedListener != null) questionChangedListener.onTriggered();
    }

    public Test getCurrentTest() {
        return tests.get(selectedTestId);
    }

    public Question getCurrentQuestion() {
        return tests.get(selectedTestId).getQuestions().get(questionId);
    }

    public void submitAnswer(List<Integer> checkedAnswers) {
        //бпосчитать баллы
        List<Integer> validAnswer = getCurrentQuestion().getAnswer();
        if(checkedAnswers.equals(validAnswer)) {
            points += getCurrentQuestion().getPoints();
        }
        questionId++;
        if(questionId < getCurrentTest().getQuestions().size()) {
            if(questionChangedListener != null) questionChangedListener.onTriggered();
        }
        else {
            if(testFinishedListener != null) testFinishedListener.onTriggered();
        }
    }

    public int getPoints() {
        return points;
    }

    public int getQuestionId() {
        return questionId;
    }
}
