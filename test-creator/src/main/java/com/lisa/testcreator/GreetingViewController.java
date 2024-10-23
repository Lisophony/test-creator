package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GreetingViewController {
    @FXML
    private Label greetingsLabel;
    @FXML
    private Button startTestButton;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        this.testManager.setTestItemSelectedListener(new EventListener() {
            @Override
            public void onTriggered() {
                greetingsLabel.setText(testManager.getTest(testManager.getSelectedTestId()).getName());
                startTestButton.setVisible(true);
            }
        });
    }

    public void handleStartTestButtonClicked() {
        testManager.startTest(testManager.getSelectedTestId());
    }
}
