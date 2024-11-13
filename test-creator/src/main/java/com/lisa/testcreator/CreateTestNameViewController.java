package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateTestNameViewController {

    @FXML
    private Button confirmTestNameButton;
    @FXML
    private TextField testNameTextField;
    @FXML
    private Label testNameEmptyLabel;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    public void handleConfirmTestNameButtonClicked() {
        String testName = testNameTextField.getText();
        if(testName.isEmpty()) {
            testNameEmptyLabel.setVisible(true);
        }
        else {
            testNameTextField.clear();
            testNameTextField.setVisible(false);
            testManager.setTestToCreateName(testName);
        }
    }
}
