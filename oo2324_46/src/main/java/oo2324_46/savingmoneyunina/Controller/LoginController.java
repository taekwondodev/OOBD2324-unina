package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;
import oo2324_46.savingmoneyunina.DAO.Implements.UtenteImplements;
import oo2324_46.savingmoneyunina.DAO.Interface.UtenteDAO;
import oo2324_46.savingmoneyunina.Entity.Account;
import oo2324_46.savingmoneyunina.Entity.SessioneUtente;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private final App a = new App();
    private final UtenteDAO utenteDAO = new UtenteImplements();
    private static LoginController controller = null;
    private LoginController(){}

    public static LoginController getController(){
        if(controller == null){
            controller = new LoginController();
        }
        return controller;
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }

    public boolean checkLogin(Account account) throws SQLException {
        return utenteDAO.exist(account);
    }
    public void saveAccount(Account account) {
        SessioneUtente.getSessioneUtente().setAccount(account); //mi salvo in un singlethon l'account
    }

}