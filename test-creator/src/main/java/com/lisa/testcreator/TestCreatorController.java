package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TestCreatorController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> testsList;

    public void addText(String text) {
        testsList.getItems().add(text);
    }
}