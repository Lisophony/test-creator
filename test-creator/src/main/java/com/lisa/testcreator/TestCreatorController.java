package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class TestCreatorController {
    @FXML
    private Label welcomeTextLabel;
    @FXML
    private ListView<String> testsListListView;
    @FXML
    private Label testNameLabel;
    @FXML
    private Button startTestButton;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }


    public void addText(String text) {
        testsListListView.getItems().add(text);
    }

    public void testsItemClicked() {
        if(testManager == null) return;
        welcomeTextLabel.setVisible(false);
        testNameLabel.setText(testManager.getTest(testsListListView.getSelectionModel().getSelectedIndex()).getName());
        testNameLabel.setVisible(true);
        startTestButton.setVisible(true);
    }

    public void populateTestsList(List<Test> tests) {
        testsListListView.getItems().clear();
        for(Test test : tests) {
            addText(test.getName());
        }
    }

    public void startTestButtonClicked() {
        startTestButton.setVisible(false);
        testNameLabel.setVisible(false);
    }
}