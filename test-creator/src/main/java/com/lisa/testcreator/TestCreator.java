package com.lisa.testcreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCreator  extends Application {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private TestManager testManager = new TestManager();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("hello-view.fxml"));
        FXMLLoader questionViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("question-view.fxml"));
        FXMLLoader testResultViewFxmlLoader = new FXMLLoader(TestResultController.class.getResource("test-result-view.fxml"));
        FXMLLoader greetingVieFxmlLoader = new FXMLLoader(TestCreator.class.getResource("greetings-view.fxml"));
        Scene scene = new Scene(mainViewFxmlLoader.load());
        stage.setTitle("Пройди тест");

        TestCreatorController testCreatorController = mainViewFxmlLoader.getController();
        testCreatorController.setQuestionFxmlView(questionViewFxmlLoader.load());
        testCreatorController.setTestResultFxmlView(testResultViewFxmlLoader.load());
        testCreatorController.setGreetingsFxmlView(greetingVieFxmlLoader.load());

        testManager.loadTests("src/main/resources/tests");

        testCreatorController.setTestManager(testManager);
        testCreatorController.populateTestsList(testManager.getTests());

        QuestionViewController questionViewController = questionViewFxmlLoader.getController();
        questionViewController.setTestManager(testManager);

        TestResultController testResultController = testResultViewFxmlLoader.getController();
        testResultController.setTestManager(testManager);

        GreetingViewController greetingViewController = greetingVieFxmlLoader.getController();
        greetingViewController.setTestManager(testManager);

        testCreatorController.init();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}