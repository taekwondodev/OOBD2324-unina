package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;

import java.io.IOException;

public class HomeController {
    private final App a = new App();
    private static HomeController controller = null;
    private HomeController(){}
    public static HomeController getController(){
        if (controller == null){
            controller = new HomeController();
        }
        return controller;
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }
}
