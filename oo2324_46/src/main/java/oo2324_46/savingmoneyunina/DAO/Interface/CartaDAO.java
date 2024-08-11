package oo2324_46.savingmoneyunina.DAO.Interface;

import oo2324_46.savingmoneyunina.Database.Database;
import oo2324_46.savingmoneyunina.Entity.Carta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CartaDAO {
    Database database = Database.getDatabase(); //singlethon database
    boolean save (Carta carta) throws SQLException;
    List<Carta> getAllCards(String nomeUtente) throws SQLException;
    List<Carta> getAllFamilyCards(int idFamiglia) throws SQLException;
}
