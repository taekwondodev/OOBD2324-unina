package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;
import oo2324_46.savingmoneyunina.DAO.Implements.CartaImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.FamigliaImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.UtenteImplements;
import oo2324_46.savingmoneyunina.DAO.Interface.CartaDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.FamigliaDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.UtenteDAO;
import oo2324_46.savingmoneyunina.Entity.Carta;
import oo2324_46.savingmoneyunina.Entity.SessioneUtente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddCartaFamigliaController {
    private final App a = new App();
    private final FamigliaDAO famigliaDAO = new FamigliaImplements();
    private final UtenteDAO utenteDAO = new UtenteImplements();
    private final CartaDAO cartaDAO = new CartaImplements();
    private static AddCartaFamigliaController controller = null;

    private AddCartaFamigliaController() {
    }

    public static AddCartaFamigliaController getController() {
        if (controller == null) {
            controller = new AddCartaFamigliaController();
        }

        return controller;
    }

    public void addFamily(String nomeFamiglia) throws SQLException {
        if(!nomeFamiglia.isEmpty()) {
            int idFamiglia = utenteDAO.getIdFamiglia(SessioneUtente.getSessioneUtente().getAccount().getUsername());
            famigliaDAO.update(nomeFamiglia, idFamiglia);
        }
    }

    public boolean addParent(String username) throws SQLException {
        int idFamiglia = utenteDAO.getIdFamiglia(SessioneUtente.getSessioneUtente().getAccount().getUsername());
        return utenteDAO.update(username, idFamiglia);
    }

    public boolean addCard(String numero, String cvv, LocalDate scadenza, String choice, String saldo) throws SQLException {
            double saldoCarta = textToDouble(saldo);
            Carta carta = new Carta(numero, cvv, scadenza,
                    choice, saldoCarta, SessioneUtente.getSessioneUtente().getAccount());

            return cartaDAO.save(carta);
    }

    private double textToDouble(String s){
        s = s.replace(",", ".");
        return Double.parseDouble(s);
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }
}
