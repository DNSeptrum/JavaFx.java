module com.example.japierdole {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.AIOM3 to javafx.fxml;
    exports com.example.AIOM3;
}