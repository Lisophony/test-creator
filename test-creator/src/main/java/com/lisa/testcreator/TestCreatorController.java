package com.lisa.testcreator;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TestCreatorController {
    @FXML
    private Label welcomeTextLabel;
    @FXML
    private ListView<String> testsListListView;
    @FXML
    private Label testNameLabel;
    @FXML
    private Button startTestButton;
    @FXML
    private VBox greetingsVBox;
    @FXML
    private VBox questionView;
    @FXML
    private Label questionTextLabel;
    @FXML
    private VBox answersVBox;
    @FXML
    private Button nextQuestionButton;
    private TestManager testManager;


    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setTestSelectedListener(new EventListener() {
            @Override
            public void onTriggered() {
                startTestButton.setVisible(false);
                testNameLabel.setVisible(false);
                questionTextLabel.setVisible(true);
                nextQuestionButton.setVisible(true);
            }
        });
        testManager.setQuestionChangedListener(new EventListener() {
            @Override
            public void onTriggered() {
                showQuestion();
            }
        });
        testManager.setTestFinishedListener(new EventListener() {
            @Override
            public void onTriggered() {
                showTestResult();
            }
        });
    }

    public void addText(String text) {
        testsListListView.getItems().add(text);
    }

    public void testsItemClicked() {
        if(testManager == null) return;
        welcomeTextLabel.setVisible(false);
        testNameLabel.setText(testManager.getTest(getSelectedTestIndex()).getName());
        testNameLabel.setVisible(true);
        startTestButton.setVisible(true);

        //убираем gui вопроса
        answersVBox.getChildren().clear();
        questionTextLabel.setVisible(false);
        nextQuestionButton.setVisible(false);
    }

    public void populateTestsList(List<Test> tests) {
        testsListListView.getItems().clear();
        for(Test test : tests) {
            addText(test.getName());
        }
    }

    public int getSelectedTestIndex() {
        return testsListListView.getSelectionModel().getSelectedIndex();
    }

    public void startTestButtonClicked() {
        testManager.startTest(getSelectedTestIndex());
//        greetingsVBox.getChildren().clear();
//        try {
//            greetingsVBox.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("questionWindow.fxml"))));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void showQuestion() {
        if(testManager.getQuestionId() == testManager.getCurrentTest().getQuestions().size() - 1) {
            nextQuestionButton.setText("Завершить");
        }
        Question currentQuestion = testManager.getCurrentQuestion();
        questionTextLabel.setText(currentQuestion.getQuestion());
        List<String> options = currentQuestion.getOptions();
        for(String option : options) {
            answersVBox.getChildren().add(new CheckBox(option));
        }
    }

    public void handleNextQuestionButtonClicked() {
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

    public void showTestResult() {
        answersVBox.getChildren().add(new Label("Вы набрали " + testManager.getPoints()));
        questionTextLabel.setVisible(false);
        nextQuestionButton.setVisible(false);
        nextQuestionButton.setText("Далее");
    }

    public void setTestStartScreen() {

    }
}