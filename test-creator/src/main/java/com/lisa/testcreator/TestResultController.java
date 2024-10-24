package com.lisa.testcreator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class TestResultController {
    @FXML
    private Label resultPointsLabel;
    @FXML
    private PieChart testResultPieChart;

    private TestManager testManager;

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
        testManager.setTestFinishedListener(() -> showTestResult());
    }

    public void showTestResult() {
        resultPointsLabel.setText("Набранный балл " + testManager.getScore() + "/" + testManager.getMaxPoints());
        testResultPieChart.legendVisibleProperty().set(false);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Верно", testManager.getScore()));
        pieChartData.add(new PieChart.Data("Неверно", testManager.getMaxPoints()-testManager.getScore()));
        testResultPieChart.setData(pieChartData);
    }
}
