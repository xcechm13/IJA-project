module com.example.ija_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ija_project to javafx.fxml;
    exports com.example.ija_project;
}