package oo2324_46.savingmoneyunina.DAO.Implements;

import oo2324_46.savingmoneyunina.DAO.Interface.FamigliaDAO;
import oo2324_46.savingmoneyunina.Entity.Famiglia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FamigliaImplements implements FamigliaDAO {
    @Override
    public void save(Famiglia famiglia, String nomeUtente) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "CALL addFamigliaUpdateUtente(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, famiglia.getNome());
        ps.setString(2, nomeUtente);

        ps.executeUpdate(); //il numero di righe inserito

        database.closePreparedStatement(ps);
        database.closeConnection(conn);
    }

    @Override
    public void update(String nuovoNome, int idFamiglia) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "UPDATE famiglia SET nome = ? WHERE id_famiglia = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nuovoNome);
        ps.setInt(2, idFamiglia);

        ps.executeUpdate();

        database.closePreparedStatement(ps);
        database.closeConnection(conn);
    }
}
