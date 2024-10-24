package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
        testManager.setQuestionChangedListener(() -> showQuestion());
        testManager.setTestSelectedListener(() -> {
            answersVBox.getChildren().clear();
            nextButton.setText("Далее");
        });
    }

    public void showQuestion() {
        if(testManager.getQuestionId() == testManager.getCurrentTest().getQuestions().size() - 1) {
            nextButton.setText("Завершить");
        }
        Question currentQuestion = testManager.getCurrentQuestion();
        questionTextLabel.setText(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        ToggleGroup radioButtonsGroup = new ToggleGroup();
        for(String option : options) {
            if(currentQuestion.isMultipleAnswer())
                answersVBox.getChildren().add(new CheckBox(option));
            else {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(radioButtonsGroup);
                answersVBox.getChildren().add(radioButton);
            }
        }
    }

    public void nextButtonClicked() {
        List<Integer> checkedAnswersId = new ArrayList<>();
        int checkBoxIndex = 1;
        List<Node> answersVBoxElements = answersVBox.getChildren();
        for(int i = 0; i < answersVBoxElements.size(); ++i) {
            Node element = answersVBoxElements.get(i);
            if(element instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) element;
                checkBox.setWrapText(true);
                if(checkBox.isSelected()) {
                    checkedAnswersId.add(checkBoxIndex);
                }
                checkBoxIndex++;
            }
            else if(element instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) element;
                radioButton.setWrapText(true);
                if(radioButton.isSelected()) {
                    checkedAnswersId.add(i + 1);
                    break;
                }
            }
        }
        answersVBox.getChildren().clear();
        testManager.submitAnswer(checkedAnswersId);
    }
}
