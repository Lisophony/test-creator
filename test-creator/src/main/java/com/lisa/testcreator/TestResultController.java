package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestResultController {
    @FXML
    private Label resultPointsLabel;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setTestFinishedListener(new EventListener() {
            @Override
            public void onTriggered() {
                showTestResult();
            }
        });
    }

    public void showTestResult() {
        resultPointsLabel.setText("Набранный балл " + testManager.getPoints());
    }
}
