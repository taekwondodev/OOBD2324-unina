package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;
import oo2324_46.savingmoneyunina.DAO.Implements.CartaImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.GruppoImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.TransazioneImplements;
import oo2324_46.savingmoneyunina.DAO.Interface.CartaDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.GruppoDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.TransazioneDAO;
import oo2324_46.savingmoneyunina.Entity.Carta;
import oo2324_46.savingmoneyunina.Entity.SessioneUtente;
import oo2324_46.savingmoneyunina.Entity.Transazione;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddTransactionController {
    private final App a = new App();
    private final GruppoDAO gruppoDAO = new GruppoImplements();
    private final TransazioneDAO transazioneDAO = new TransazioneImplements();
    private final CartaDAO cartaDAO = new CartaImplements();

    private static AddTransactionController controller = null;
    private AddTransactionController(){}
    public static AddTransactionController getController(){
        if (controller == null){
            controller = new AddTransactionController();
        }

        return controller;
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }
    public void insertCategory(String nome) throws SQLException {
        gruppoDAO.save(nome);
    }
    public boolean insertTransaction(LocalDate data, String importo, String tipoTransazione, String numeroCarta)
            throws SQLException {
        Carta carta = new Carta(numeroCarta);
        double importoTransazione = textToDouble(importo);
        Transazione transazione = new Transazione(data, importoTransazione, tipoTransazione, carta);

        return transazioneDAO.save(transazione);
    }
    public boolean insertTransactionG(LocalDate data, String importo, String tipoTransazione, String numeroCarta, String nomeGruppo)
            throws SQLException{
        Carta carta = new Carta(numeroCarta);
        double importoTransazione = textToDouble(importo);
        int idGruppo = gruppoDAO.getId(nomeGruppo);

        Transazione transazione = new Transazione(data, importoTransazione, tipoTransazione, carta);
        return transazioneDAO.saveG(transazione, idGruppo);
    }
    public List<String> getAllNumeroCard(){
        List<String> listNumeroCarte = new ArrayList<>();
        try{
            List<Carta> tmp = cartaDAO.getAllCards(SessioneUtente.getSessioneUtente().getAccount().getUsername());

            for (Carta carta: tmp){
                listNumeroCarte.add(carta.getNumero());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listNumeroCarte;
    }
    public List<String> getAllCategories(){
        List<String> listNomeCategorie = new ArrayList<>();
        try {
            listNomeCategorie = gruppoDAO.getAllCategories(SessioneUtente.getSessioneUtente().getAccount().getUsername());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return listNomeCategorie;
    }
    private double textToDouble(String s){
        s = s.replace(",", ".");
        return Double.parseDouble(s);
    }
}
