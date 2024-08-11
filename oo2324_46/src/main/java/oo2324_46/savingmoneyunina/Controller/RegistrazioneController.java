package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;
import oo2324_46.savingmoneyunina.DAO.Implements.FamigliaImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.UtenteImplements;
import oo2324_46.savingmoneyunina.DAO.Interface.FamigliaDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.UtenteDAO;
import oo2324_46.savingmoneyunina.Entity.Account;
import oo2324_46.savingmoneyunina.Entity.Famiglia;
import oo2324_46.savingmoneyunina.Entity.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegistrazioneController {
    private final App a = new App();
    private final UtenteDAO utenteDAO = new UtenteImplements();
    private final FamigliaDAO famigliaDAO = new FamigliaImplements();
    private static RegistrazioneController controller = null;
    private RegistrazioneController(){}

    public static RegistrazioneController getController(){
        if(controller == null){
            controller = new RegistrazioneController();
        }
        return controller;
    }
    public boolean creaUtente(String username, String email, String password, String nome, String cognome, LocalDate nascita)
            throws SQLException {
        Account account = new Account(username, email, password);
        Utente utente = new Utente(account, nome, cognome, nascita);
        Famiglia famiglia = new Famiglia("defaultNomeFamiglia");

        boolean ret = utenteDAO.save(utente);
        famigliaDAO.save(famiglia, username);

        return ret;
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }
}
