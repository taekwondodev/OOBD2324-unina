package oo2324_46.savingmoneyunina.DAO.Interface;

import oo2324_46.savingmoneyunina.Database.Database;
import java.sql.SQLException;
import java.util.List;

public interface GruppoDAO {
    Database database = Database.getDatabase();
    boolean save(String nomeGruppo) throws SQLException;
    int getId(String nomeGruppo) throws SQLException;
    List<String> getAllCategories(String nomeUtente) throws SQLException;
    List<String> getFamilyCategories(int idFamiglia, String numeroCarta) throws SQLException;
}
