module com.example.spaceinvader {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spaceinvader to javafx.fxml;
    exports com.example.spaceinvader;
    exports com.example.spaceinvader.crafts;
    opens com.example.spaceinvader.crafts to javafx.fxml;
}