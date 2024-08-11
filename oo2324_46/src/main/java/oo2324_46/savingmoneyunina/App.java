package oo2324_46.savingmoneyunina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 550);
        stage.setScene(scene);
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Scene newScene = new Scene(pane);
        stg.setScene(newScene);
    }

    public static void main(String[] args) {
        launch();
    }
}