package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import java.util.List;


public class TestCreatorController {
    @FXML
    private ListView<String> testsListListView;
    @FXML
    private VBox greetingsVBox;

    private TestManager testManager;
    private Node questionFxmlView;
    private Node testResultFxmlView;
    private Node greetingsFxmlView;

    public void setQuestionFxmlView(Node questionFxmlView) {
        this.questionFxmlView = questionFxmlView;
    }

    public void setTestResultFxmlView(Node testResultFxmlView) {
        this.testResultFxmlView = testResultFxmlView;
    }

    public void setGreetingsFxmlView(Node greetingsFxmlView) {
        this.greetingsFxmlView = greetingsFxmlView;
    }

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setTestSelectedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(questionFxmlView);
        });
        testManager.setTestFinishedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(testResultFxmlView);
        });
        testManager.setTestItemSelectedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(greetingsFxmlView);
        });
    }

    public void init() {
        greetingsVBox.getChildren().clear();
        greetingsVBox.getChildren().add(greetingsFxmlView);
    }

    public void addText(String text) {
        testsListListView.getItems().add(text);
    }

    public void testsItemClicked() {
        if(testManager == null) return;
        testManager.setSelectedTestId(getSelectedTestIndex());
    }

    public void populateTestsList(List<Test> tests) {
        testsListListView.getItems().clear();
        for(Test test : tests) {
            addText(test.getName());
        }
    }

    public int getSelectedTestIndex() {
        return testsListListView.getSelectionModel().getSelectedIndex();
    }
}