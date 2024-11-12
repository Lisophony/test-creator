package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateTestQuestionViewController {
    private TestManager testManager;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField answerTextField;
    @FXML
    private Button addAnswerButton;
    @FXML
    private VBox optionsVBox;
    @FXML
    private Button nextButton;
    @FXML
    private Button finishButton;


    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setCreatingTestQuestionIsSetListener(() -> {
            questionTextField.clear();
            answerTextField.clear();
            optionsVBox.getChildren().clear();
        });
    }

    public void handleAddAnswerButtonClicked() {
        String answer = answerTextField.getText();
        answerTextField.clear();
        answerTextField.requestFocus();
        if(!answer.isEmpty()) {
            optionsVBox.getChildren().add(new CheckBox(answer));
        }
    }

    public void handleNextButtonClicked() {
        gatherQuestion();
    }

    public void gatherQuestion() {
        Question question = new Question();
        ArrayList<String> options = new ArrayList<>();
        ArrayList<Integer> answer = new ArrayList<>();
        List<Node> checkBoxes = optionsVBox.getChildren();
        for(int i = 0; i < checkBoxes.size(); ++i) {
            CheckBox checkBox = (CheckBox) checkBoxes.get(i);
            if(checkBox.isSelected()) {
                answer.add(i + 1);
            }
            options.add(checkBox.getText());
        }
        if(answer.size() > 1) {
            question.setMultipleAnswer(true);
        }
        question.setQuestion(questionTextField.getText());
        question.setAnswer(answer);
        question.setOptions(options);
        question.setPoints(10);
        testManager.addQuestionToCreatingTest(question);
    }

    public void handleFinishButtonClicked () {
        gatherQuestion();
        testManager.finishTestCreating();
    }
}
