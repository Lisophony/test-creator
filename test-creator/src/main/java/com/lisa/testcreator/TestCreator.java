package com.lisa.testcreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestCreator  extends Application {
    private TestManager testManager = new TestManager();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("hello-view.fxml"));
        FXMLLoader questionViewFxmlLoader = new FXMLLoader(TestCreator.class.getResource("question-view.fxml"));
        FXMLLoader testResultViewFxmlLoader = new FXMLLoader(TestResultViewController.class.getResource("test-result-view.fxml"));
        FXMLLoader greetingVieFxmlLoader = new FXMLLoader(TestCreator.class.getResource("greetings-view.fxml"));
        Scene scene = new Scene(mainViewFxmlLoader.load());
        stage.setTitle("Пройди тест");
        stage.setResizable(false);

        MainViewController mainViewController = mainViewFxmlLoader.getController();
        mainViewController.setQuestionFxmlView(questionViewFxmlLoader.load());
        mainViewController.setTestResultFxmlView(testResultViewFxmlLoader.load());
        mainViewController.setGreetingsFxmlView(greetingVieFxmlLoader.load());

        mainViewController.setTestManager(testManager);
        mainViewController.setStage(stage);

        QuestionViewController questionViewController = questionViewFxmlLoader.getController();
        questionViewController.setTestManager(testManager);

        TestResultViewController testResultViewController = testResultViewFxmlLoader.getController();
        testResultViewController.setTestManager(testManager);

        GreetingViewController greetingViewController = greetingVieFxmlLoader.getController();
        greetingViewController.setTestManager(testManager);

        mainViewController.showGreetingView();

        testManager.loadTests("src/main/resources/tests");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}