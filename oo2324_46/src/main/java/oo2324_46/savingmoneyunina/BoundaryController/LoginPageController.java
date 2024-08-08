package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.LoginController;
import oo2324_46.savingmoneyunina.Entity.Account;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    private Label errore;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink link;
    @FXML
    private Button button;

    /**************************************/

    @FXML
    public void userLogIn(ActionEvent event) throws IOException, SQLException {
        String accountUsername = username.getText();
        String accountPassword = password.getText();
        Account account = new Account(accountUsername, accountPassword);

        boolean check = LoginController.getController().checkLogin(account);
        if (check){
            errore.setText("");
            LoginController.getController().saveAccount(account);
            LoginController.getController().navigation("Home");
        }
        else {
            errore.setText("username o password errata");
        }
    }
    @FXML
    public void goToRegister(ActionEvent event) throws IOException {
        LoginController.getController().navigation("Registrazione");
    }
}
