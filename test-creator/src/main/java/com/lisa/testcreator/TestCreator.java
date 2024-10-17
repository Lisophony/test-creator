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
        FXMLLoader fxmlLoader = new FXMLLoader(TestCreator.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Пройди тест");

        stage.setScene(scene);
        stage.show();
        TestCreatorController controller = fxmlLoader.getController();
        testManager.loadTests("src/main/resources/tests");
        controller.setTestManager(testManager);
        controller.populateTestsList(testManager.getTests());
    }

    public static void main(String[] args) throws IOException {

        launch();

//       Test test = objectMapper.readValue(new File("src/main/resources/tests/Chemistry.json"), Test.class);

    }
}