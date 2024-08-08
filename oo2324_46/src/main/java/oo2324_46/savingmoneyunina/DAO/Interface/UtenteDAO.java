package oo2324_46.savingmoneyunina.DAO.Interface;

import oo2324_46.savingmoneyunina.Database.Database;
import oo2324_46.savingmoneyunina.Entity.Account;
import oo2324_46.savingmoneyunina.Entity.Utente;

import java.sql.SQLException;

public interface UtenteDAO {
    Database database = Database.getDatabase(); //singlethon database

    boolean create (Utente utente) throws SQLException;
    int getIdFamiglia(String nomeUtente) throws SQLException; //recupero id da un username quello del singlethon
    boolean update (String nomeUtente, int id) throws SQLException;
    boolean exist(Account account) throws SQLException;
}