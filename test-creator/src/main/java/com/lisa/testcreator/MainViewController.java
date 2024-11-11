package com.lisa.testcreator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;


public class MainViewController {
    @FXML
    private ListView<String> testsListListView;
    @FXML
    private VBox greetingsVBox;
    @FXML
    private Button createTestButton;

    private TestManager testManager;
    private Node questionFxmlView;
    private Node testResultFxmlView;
    private Node greetingsFxmlView;
    private Node createTestNameView;
    private Node createTestQuestionView;
    private Stage stage;

    ButtonType okAlertButton = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelAlertButton = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final Alert testIsRunningAlert = new Alert(Alert.AlertType.CONFIRMATION, "", okAlertButton, cancelAlertButton);

    public void setQuestionFxmlView(Node questionFxmlView) {
        this.questionFxmlView = questionFxmlView;
    }

    public void setTestResultFxmlView(Node testResultFxmlView) {
        this.testResultFxmlView = testResultFxmlView;
    }

    public void setGreetingsFxmlView(Node greetingsFxmlView) {
        this.greetingsFxmlView = greetingsFxmlView;
    }

    public void setCreateTestNameView(Node createTestNameView) {
        this.createTestNameView = createTestNameView;
    }

    public void setCreateTestQuestionView(Node createTestQuestionView) {
        this.createTestQuestionView = createTestQuestionView;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setTestSelectedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(questionFxmlView);
        });
        testManager.setTestFinishedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(testResultFxmlView);
        });
        testManager.setTestItemSelectedListener(() -> {
            if(testManager.isTestIsRunning()) {
                testIsRunningAlert.setHeaderText("Прервать прохождение теста?");
                Optional<ButtonType> buttonType = testIsRunningAlert.showAndWait();
                ButtonType button = buttonType.orElse(cancelAlertButton);
                if(button == okAlertButton) {
                    showGreetingView();
                    testManager.setTestIsRunning(false);
                }
                else {
                     testsListListView.getSelectionModel().select(testManager.getCurrentTestId());
                }
            }
            else {
                showGreetingView();
            }
        });
        testManager.setTestsListChangedListener(() -> {
            showGreetingView();
            populateTestsList(testManager.getTests());
        });
        testManager.setTestCreationStartedListener(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(createTestNameView);
        });
        testManager.setCreatingTestNameIsSet(() -> {
            greetingsVBox.getChildren().clear();
            greetingsVBox.getChildren().add(createTestQuestionView);
        });
        testManager.setCreatingTestFinishedListener(() -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(stage);
            testManager.saveCreatedTest(file.getPath());
        });
    }

    public void showGreetingView() {
        greetingsVBox.getChildren().clear();
        greetingsVBox.getChildren().add(greetingsFxmlView);
    }

    public void addText(String text) {
        testsListListView.getItems().add(text);
    }

    public void testsItemClicked() {
        if(testManager == null) return;
        testManager.setSelectedTestId(getSelectedTestIndex());
    }

    public void populateTestsList(List<Test> tests) {
        testsListListView.getItems().clear();
        for(Test test : tests) {
            addText(test.getName());
        }
    }

    public void loadTestsFromPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(stage);
        if(file != null)
            testManager.loadTests(file.getPath());
    }

    public int getSelectedTestIndex() {
        return testsListListView.getSelectionModel().getSelectedIndex();
    }

    public void handleCreateTestButtonClicked() {
        testManager.startTestCreationMode();
    }
}