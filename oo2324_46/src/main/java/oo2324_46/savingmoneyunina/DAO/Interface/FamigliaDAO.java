package oo2324_46.savingmoneyunina.DAO.Interface;

import oo2324_46.savingmoneyunina.Database.Database;
import oo2324_46.savingmoneyunina.Entity.Famiglia;

import java.sql.SQLException;

public interface FamigliaDAO {
    Database database = Database.getDatabase(); //singlethon database

    void save(Famiglia famiglia, String nomeUtente) throws SQLException; //chiama una procedura che salva e aggiorna utente con quel nome utente
}
