package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateTestNameViewController {

    @FXML
    private Button confirmTestNameButton;
    @FXML
    private TextField testNameTextField;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    public void handleConfirmTestNameButtonClicked() {

    }
}
