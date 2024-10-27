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
        Double validAnswersPercent = ((double) testManager.getScore())/ testManager.getMaxPoints() * 100;
        Double invalidAnswersPercent = 100 - validAnswersPercent;
        String validMarker = String.format("Верно %.2f %%", validAnswersPercent);
        String invalidMarker = String.format("Неверно %.2f %%", invalidAnswersPercent);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data(validMarker, testManager.getScore()));
        pieChartData.add(new PieChart.Data(invalidMarker, testManager.getMaxPoints()-testManager.getScore()));
        testResultPieChart.setData(pieChartData);
    }
}
