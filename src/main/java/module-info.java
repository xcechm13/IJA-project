module com.example.ija_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens Game to javafx.fxml;
    exports Game;
    exports ConstantsEnums;
    opens ConstantsEnums to javafx.fxml;
    exports Game.Views;
    opens Game.Views to javafx.fxml;
    exports Game.Objects;
    opens Game.Objects to javafx.fxml;
    exports Game.Records;
    opens Game.Records to javafx.fxml;
}