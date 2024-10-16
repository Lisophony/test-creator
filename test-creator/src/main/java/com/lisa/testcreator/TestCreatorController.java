package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestCreatorController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}