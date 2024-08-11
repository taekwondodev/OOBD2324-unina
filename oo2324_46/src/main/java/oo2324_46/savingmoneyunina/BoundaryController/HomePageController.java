package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import oo2324_46.savingmoneyunina.Controller.HomeController;
import oo2324_46.savingmoneyunina.Entity.SessioneUtente;

import java.io.IOException;

public class HomePageController {
    @FXML
    private Hyperlink link;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Label welcome;
    /******************************/

    @FXML
    private void initialize(){
        String username = SessioneUtente.getSessioneUtente().getAccount().getUsername();
        welcome.setText("Ciao, " + username);
    }

    @FXML
    public void goToAddCarta(ActionEvent event) throws IOException {
        HomeController.getController().navigation("AddCartaFamiglia");
    }
    @FXML
    public void goToAddTransaction(ActionEvent event) throws IOException {
        HomeController.getController().navigation("AddTransactionGroup");
    }
    @FXML
    public void goToCheckTransaction(ActionEvent event) throws IOException {
        HomeController.getController().navigation("CheckTransaction");
    }
    @FXML
    public void goToReport(ActionEvent event) throws IOException {
        HomeController.getController().navigation("Report");
    }
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        SessioneUtente.getSessioneUtente().logOut();
        HomeController.getController().navigation("login");
    }
}
