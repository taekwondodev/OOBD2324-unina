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

        int result = ps.executeUpdate(); //il numero di righe inserito

        database.closePreparedStatement(ps);
        database.closeConnection(conn);
    }
}
