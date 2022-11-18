module com.example.shatty {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.shatty to javafx.fxml;
    exports com.example.shatty;
}