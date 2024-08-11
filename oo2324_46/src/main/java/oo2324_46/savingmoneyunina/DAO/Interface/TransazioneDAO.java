package oo2324_46.savingmoneyunina.DAO.Interface;

import oo2324_46.savingmoneyunina.Database.Database;
import oo2324_46.savingmoneyunina.Entity.Report;
import oo2324_46.savingmoneyunina.Entity.Transazione;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TransazioneDAO {
    Database database = Database.getDatabase();

    boolean save(Transazione transazione) throws SQLException;
    boolean saveG(Transazione transazione, int idGruppo) throws SQLException;
    List<Transazione> filter(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException;
    List<Transazione> familyTransactions(int idFamiglia) throws SQLException;
    Report reportValues(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException;
    double[] reportPercent(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException;
}
