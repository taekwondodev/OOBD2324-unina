package oo2324_46.savingmoneyunina.Controller;

import oo2324_46.savingmoneyunina.App;
import oo2324_46.savingmoneyunina.DAO.Implements.CartaImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.GruppoImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.TransazioneImplements;
import oo2324_46.savingmoneyunina.DAO.Implements.UtenteImplements;
import oo2324_46.savingmoneyunina.DAO.Interface.CartaDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.GruppoDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.TransazioneDAO;
import oo2324_46.savingmoneyunina.DAO.Interface.UtenteDAO;
import oo2324_46.savingmoneyunina.Entity.Carta;
import oo2324_46.savingmoneyunina.Entity.Report;
import oo2324_46.savingmoneyunina.Entity.SessioneUtente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private final App a = new App();
    private final UtenteDAO utenteDAO = new UtenteImplements();
    private final CartaDAO cartaDAO = new CartaImplements();
    private final GruppoDAO gruppoDAO = new GruppoImplements();
    private final TransazioneDAO transazioneDAO = new TransazioneImplements();

    private static ReportController controller = null;
    private ReportController(){}

    public static ReportController getController(){
        if(controller == null){
            controller = new ReportController();
        }
        return controller;
    }

    public void navigation(String fxml) throws IOException {
        a.changeScene(fxml + ".fxml");
    }

    public List<String> getAllNumeroCard(){
        List<String> listNumeroCarte = new ArrayList<>();
        try{
            List<Carta> tmp = cartaDAO.getAllFamilyCards(
                    utenteDAO.getIdFamiglia(SessioneUtente.getSessioneUtente().getAccount().getUsername())
            );

            for (Carta carta: tmp){
                listNumeroCarte.add(carta.getNumero());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listNumeroCarte;
    }
    public List<String> getAllCategories(String numeroCarta){
        List<String> listNomeCategorie = new ArrayList<>();
        try {
            listNomeCategorie = gruppoDAO.getFamilyCategories(
                    utenteDAO.getIdFamiglia(SessioneUtente.getSessioneUtente().getAccount().getUsername()),
                    numeroCarta
            );
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return listNomeCategorie;
    }

    public Report getReport(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine){
        try {
            return transazioneDAO.reportValues(numeroCarta, nomeCategoria, dataInizio, dataFine);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public double[] getPercentage(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine){
        try{
            return transazioneDAO.reportPercent(numeroCarta, nomeCategoria, dataInizio, dataFine);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
