module com.lisa.testcreator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.lisa.testcreator to javafx.fxml;
    exports com.lisa.testcreator;
}