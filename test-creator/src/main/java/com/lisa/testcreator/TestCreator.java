package com.lisa.testcreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCreator  extends Application {
    private TestManager testManager = new TestManager();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("hello-view.fxml"));
        FXMLLoader questionViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("question-view.fxml"));
        FXMLLoader testResultViewFxmlLoader = new FXMLLoader(TestResultController.class.getResource("test-result-view.fxml"));
        FXMLLoader greetingVieFxmlLoader = new FXMLLoader(TestCreator.class.getResource("greetings-view.fxml"));
        Scene scene = new Scene(mainViewFxmlLoader.load());
        stage.setTitle("Пройди тест");
        stage.setResizable(false);

        TestCreatorController testCreatorController = mainViewFxmlLoader.getController();
        testCreatorController.setQuestionFxmlView(questionViewFxmlLoader.load());
        testCreatorController.setTestResultFxmlView(testResultViewFxmlLoader.load());
        testCreatorController.setGreetingsFxmlView(greetingVieFxmlLoader.load());

        testCreatorController.setTestManager(testManager);
        testCreatorController.setStage(stage);

        QuestionViewController questionViewController = questionViewFxmlLoader.getController();
        questionViewController.setTestManager(testManager);

        TestResultController testResultController = testResultViewFxmlLoader.getController();
        testResultController.setTestManager(testManager);

        GreetingViewController greetingViewController = greetingVieFxmlLoader.getController();
        greetingViewController.setTestManager(testManager);

        testCreatorController.showGreetingView();

        testManager.loadTests("src/main/resources/tests");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}