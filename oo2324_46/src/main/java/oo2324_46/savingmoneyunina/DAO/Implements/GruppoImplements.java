package oo2324_46.savingmoneyunina.DAO.Implements;

import oo2324_46.savingmoneyunina.DAO.Interface.GruppoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GruppoImplements implements GruppoDAO {
    @Override
    public boolean save(String nomeGruppo) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "INSERT INTO gruppo(nome) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nomeGruppo);

        int result = ps.executeUpdate(); //il numero di righe inserito

        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return result > 0;
    }

    @Override
    public int getId(String nomeGruppo) throws SQLException {
        Connection conn = database.getConnection();
        int id = -1;

        String sql = "SELECT id_gruppo FROM Gruppo WHERE nome = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nomeGruppo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id_gruppo");
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return id;
    }

    @Override
    public List<String> getAllCategories(String nomeUtente) throws SQLException {
        Connection conn = database.getConnection();
        List<String> list = new ArrayList<>();

        String sql = "SELECT DISTINCT G.id_gruppo, G.nome FROM gruppo as G" +
                " JOIN transazione as T on G.id_gruppo = T.id_gruppo" +
                " join carta as c on c.numero = T.numero_carta" +
                " join utente u on u.id_utente = c.id_utente" +
                " WHERE nomeutente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nomeUtente);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String nome = rs.getString("nome");

            list.add(nome);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return list;
    }

    @Override
    public List<String> getFamilyCategories(int idFamiglia, String numeroCarta) throws SQLException {
        Connection conn = database.getConnection();
        List<String> list = new ArrayList<>();

        String sql = "SELECT DISTINCT G.id_gruppo, G.nome FROM gruppo as G" +
                " JOIN transazione as T on G.id_gruppo = T.id_gruppo" +
                " join carta as c on c.numero = T.numero_carta" +
                " join utente u on u.id_utente = c.id_utente" +
                " WHERE id_famiglia = ? AND t.numero_carta = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idFamiglia);
        ps.setString(2, numeroCarta);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String nome = rs.getString("nome");

            list.add(nome);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return list;
    }
}
