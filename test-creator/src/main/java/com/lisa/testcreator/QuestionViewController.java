package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class QuestionViewController {
    @FXML
    private Label questionTextLabel;
    @FXML
    private Button nextButton;
    @FXML
    private VBox answersVBox;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setQuestionChangedListener(new EventListener() {
            @Override
            public void onTriggered() {
                showQuestion();
            }
        });
        testManager.setTestSelectedListener(new EventListener() {
            @Override
            public void onTriggered() {
                answersVBox.getChildren().clear();
                nextButton.setText("Далее");
            }
        });
    }

    public void showQuestion() {
        if(testManager.getQuestionId() == testManager.getCurrentTest().getQuestions().size() - 1) {
            nextButton.setText("Завершить");
        }
        Question currentQuestion = testManager.getCurrentQuestion();
        questionTextLabel.setText(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        for(String option : options) {
            answersVBox.getChildren().add(new CheckBox(option));
        }
    }

    public void nextButtonClicked() {
        List<Integer> checkedAnswersId = new ArrayList<>();
        int checkBoxIndex = 1;
        for(Node element : answersVBox.getChildren()) {
            if(element instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) element;
                if(checkBox.isSelected()) {
                    checkedAnswersId.add(checkBoxIndex);
                }
                checkBoxIndex++;
            }
        }
        answersVBox.getChildren().clear();
        testManager.submitAnswer(checkedAnswersId);
    }
}
