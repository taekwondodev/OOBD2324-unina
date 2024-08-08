module com.example.oobd2324_46 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens oo2324_46.savingmoneyunina to javafx.fxml;
    exports oo2324_46.savingmoneyunina;
    exports oo2324_46.savingmoneyunina.Controller;
    exports oo2324_46.savingmoneyunina.BoundaryController;
    opens oo2324_46.savingmoneyunina.BoundaryController to javafx.fxml;
}