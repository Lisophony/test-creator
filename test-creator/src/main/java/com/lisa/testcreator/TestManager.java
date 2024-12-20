package com.lisa.testcreator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class TestManager {
    private Event testItemSelectedListener = new Event();
    private Event testSelectedListener = new Event();
    private Event questionChangedListener = new Event();
    private Event testFinishedListener = new Event();
    private Event testsListChangedListener = new Event();
    private Event testCreationStartedListener = new Event();
    private Event creatingTestNameIsSet = new Event();
    private Event creatingTestQuestionIsSetListener = new Event();
    private Event creatingTestFinishedListener= new Event();

    private boolean testCreationRunning = false;
    private Test testToCreate;
    private ArrayList<Question> createdTestQuestions;

    private List<Test> tests;
    private int selectedTestId = -1;
    private int currentTestId = -1;
    private int questionId = -1;
    private int score = 0;
    private boolean testIsRunning = false;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setTestSelectedListener(EventListener testSelectedListener) {
        this.testSelectedListener.addListener(testSelectedListener);
    }

    public void setQuestionChangedListener(EventListener questionChangedListener) {
        this.questionChangedListener.addListener(questionChangedListener);
    }

    public void setTestFinishedListener(EventListener testFinishedListener) {
        this.testFinishedListener.addListener(testFinishedListener);
    }

    public void setTestItemSelectedListener(EventListener testItemSelectedListener) {
        this.testItemSelectedListener.addListener(testItemSelectedListener);
    }

    public void setTestsListChangedListener(EventListener testsListChangedListener) {
        this.testsListChangedListener.addListener(testsListChangedListener);
    }

    public void setTestCreationStartedListener(EventListener testCreationStartedListener) {
        this.testCreationStartedListener.addListener(testCreationStartedListener);
    }

    public void setCreatingTestNameIsSet(EventListener creatingTestNameIsSetListener) {
        this.creatingTestNameIsSet.addListener(creatingTestNameIsSetListener);
    }

    public void setCreatingTestQuestionIsSetListener(EventListener creatingTestQuestionIsSetListener) {
        this.creatingTestQuestionIsSetListener.addListener(creatingTestQuestionIsSetListener);
    }

    public void setCreatingTestFinishedListener(EventListener creatingTestFinishedListener) {
        this.creatingTestFinishedListener.addListener(creatingTestFinishedListener);
    }

    public int getSelectedTestId() {
        return selectedTestId;
    }

    public int getCurrentTestId() {
        return currentTestId;
    }

    public boolean isTestCreationRunning() {
        return testCreationRunning;
    }

    public void setSelectedTestId(int selectedTestId) {
        this.selectedTestId = selectedTestId;
        testItemSelectedListener.onTriggered();
    }

    private void resetAllStates() {
        selectedTestId = -1;
        currentTestId = -1;
        questionId = -1;
        score = 0;
        testIsRunning = false;
//        testSelectedListener.onTriggered();
//        testItemSelectedListener.onTriggered();
    }

    public void setTestIsRunning(boolean testIsRunning) {
        this.testIsRunning = testIsRunning;
    }

    public boolean isTestIsRunning() {
        return testIsRunning;
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
        testIsRunning = false;
        testsListChangedListener.onTriggered();
    }

    public void loadTests(List<File> files) {
        if(files != null) {
            for (File testfile : files) {
                try {
                    tests.add(objectMapper.readValue(testfile, Test.class));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        testIsRunning = false;
        testsListChangedListener.onTriggered();
    }
    public Test getTest(int id) {
        return tests.get(id);
    }

    public List<Test> getTests() {
        return tests;
    }

    public void startTest(int testId) {
        currentTestId = testId;
        questionId = 0;
        score = 0;
        testIsRunning = true;
        if(testSelectedListener != null) {
            testSelectedListener.onTriggered();
        }
        if(questionChangedListener != null) questionChangedListener.onTriggered();
    }

    public Test getCurrentTest() {
        return tests.get(currentTestId);
    }

    public Question getCurrentQuestion() {
        return tests.get(currentTestId).getQuestions().get(questionId);
    }

    public void submitAnswer(List<Integer> checkedAnswers) {
        List<Integer> validAnswer = getCurrentQuestion().getAnswer();
        if(checkedAnswers.equals(validAnswer)) {
            score += getCurrentQuestion().getPoints();
        }
        questionId++;
        if(questionId < getCurrentTest().getQuestions().size()) {
            if(questionChangedListener != null) questionChangedListener.onTriggered();
        }
        else {
            testIsRunning = false;
            if(testFinishedListener != null) testFinishedListener.onTriggered();
        }
    }

    public int getScore() {
        return score;
    }

    public int getMaxPoints() {
        int maxPoints = 0;
        for(Question question : getCurrentTest().getQuestions()){
            maxPoints += question.getPoints();
        }
        return maxPoints;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void startTestCreationMode() {
        resetAllStates();
        testCreationRunning = true;
        testToCreate = new Test();
        createdTestQuestions = new ArrayList<>();
        testCreationStartedListener.onTriggered();
    }

    public void setTestToCreateName(String testName) {
        testToCreate.setName(testName);
        creatingTestNameIsSet.onTriggered();
    }

    public void addQuestionToCreatingTest(Question question) {
        createdTestQuestions.add(question);
        creatingTestQuestionIsSetListener.onTriggered();
    }

    public void finishTestCreating() {
        testToCreate.setQuestions(createdTestQuestions);
        creatingTestFinishedListener.onTriggered();
    }

    public void saveCreatedTest(String path) {
        try {
            objectMapper.writeValue(new File(path + "/newTest.json"), testToCreate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
