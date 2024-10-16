module com.lisa.testcreator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.lisa.testcreator to javafx.fxml;
    exports com.lisa.testcreator;
}