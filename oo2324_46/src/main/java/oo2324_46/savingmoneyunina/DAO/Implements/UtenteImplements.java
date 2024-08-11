package oo2324_46.savingmoneyunina.DAO.Implements;

import oo2324_46.savingmoneyunina.DAO.Interface.UtenteDAO;
import oo2324_46.savingmoneyunina.Entity.Account;
import oo2324_46.savingmoneyunina.Entity.Utente;

import java.sql.*;

public class UtenteImplements implements UtenteDAO {
    @Override
    public boolean save(Utente utente) throws SQLException {
        Connection conn = database.getConnection();

        String sqlDuplicates = "SELECT 1 FROM Utente WHERE email = ? OR nomeUtente = ?";
        PreparedStatement psD = conn.prepareStatement(sqlDuplicates);
        psD.setString(1, utente.getAccount().getEmail());
        psD.setString(2, utente.getAccount().getUsername());
        ResultSet rs = psD.executeQuery();

        if (rs.next()) {
            database.closeResultSet(rs);
            database.closePreparedStatement(psD);
            return false;
        }
        else {
            database.closeResultSet(rs);
            database.closePreparedStatement(psD);

            String sql = "INSERT INTO Utente(nomeUtente, email, password, nome, cognome, dataDiNascita) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, utente.getAccount().getUsername());
            ps.setString(2, utente.getAccount().getEmail());
            ps.setString(3, utente.getAccount().getPassword());
            ps.setString(4, utente.getNome());
            ps.setString(5, utente.getCognome());
            ps.setDate(6, Date.valueOf(utente.getDataDiNascita()));

            int result = ps.executeUpdate(); //il numero di righe inserito

            database.closePreparedStatement(ps);
            database.closeConnection(conn);

            return result > 0;
        }
    }

    @Override
    public int getIdFamiglia(String nomeUtente) throws SQLException {
        int id = -1;
        Connection conn = database.getConnection();

        String sql = "SELECT id_famiglia FROM Utente WHERE nomeUtente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nomeUtente);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id_famiglia");
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return id;
    }

    @Override
    public boolean update(String nomeUtente, int id) throws SQLException {
        //recupero l'id da una query, poi aggiorno il campo famiglia con quell'username
        Connection conn = database.getConnection();

        String sql = "UPDATE Utente SET id_famiglia = ? WHERE nomeUtente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, nomeUtente);

        int result = ps.executeUpdate();

        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return result > 0;
    }

    @Override
    public boolean exist(Account account) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "SELECT 1 FROM Utente WHERE nomeUtente = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());
        ResultSet rs = ps.executeQuery();

        boolean check = rs.next();

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return check;
    }
}
