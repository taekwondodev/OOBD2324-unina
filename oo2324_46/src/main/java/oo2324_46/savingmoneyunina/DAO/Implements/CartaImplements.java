package oo2324_46.savingmoneyunina.DAO.Implements;

import oo2324_46.savingmoneyunina.DAO.Interface.CartaDAO;
import oo2324_46.savingmoneyunina.Entity.Carta;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CartaImplements implements CartaDAO {
    @Override
    public boolean save(Carta carta) throws SQLException {
        //ho l'account, nella table sql vuole un id, quindi devo prima recuperare l'id
        Connection conn = database.getConnection();

        int id = -1;
        String idSql = "SELECT id_utente FROM Utente WHERE nomeutente = ?";
        PreparedStatement ps1 = conn.prepareStatement(idSql);
        ps1.setString(1, carta.getProprietario().getUsername());
        ResultSet rs = ps1.executeQuery();

        if (rs.next()){
            id = rs.getInt("id_utente");
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps1);

        String sqlDuplicates = "SELECT 1 FROM Carta WHERE numero = ?";
        PreparedStatement psD = conn.prepareStatement(sqlDuplicates);
        psD.setString(1, carta.getNumero());
        ResultSet rs2 = psD.executeQuery();

        if (rs2.next()) {
            database.closeResultSet(rs);
            database.closePreparedStatement(psD);
            return false;
        }
        else {
            database.closeResultSet(rs);
            database.closePreparedStatement(psD);

            String sql = "INSERT INTO carta(numero, cvv, scadenza, tipo_carta, saldo, id_utente) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(sql);
            ps2.setString(1, carta.getNumero());
            ps2.setString(2, carta.getCvv());
            ps2.setDate(3, Date.valueOf(carta.getScadenza()));
            ps2.setString(4, carta.getTipoCarta());
            ps2.setDouble(5, carta.getSaldo());
            ps2.setInt(6, id);

            int result = ps2.executeUpdate(); //il numero di righe inserito

            database.closePreparedStatement(ps2);
            database.closeConnection(conn);

            return result > 0;
        }
    }

    @Override
    public List<Carta> getAllCards(String nomeUtente) throws SQLException {    //l'idUtente preso dal singlethon
        Connection conn = database.getConnection();
        List<Carta> listCards = new ArrayList<>();

        String sql = "SELECT C.numero, C.cvv, C.scadenza, C.tipo_carta, C.saldo FROM Carta AS C" +
                " JOIN utente as U on U.id_utente = C.id_utente" +
                " WHERE nomeutente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nomeUtente);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String numero = rs.getString("numero");
            String cvv = rs.getString("cvv");
            LocalDate scadenza = rs.getDate("scadenza").toLocalDate();
            String tipo_carta = rs.getString("tipo_carta");
            double saldo = rs.getDouble("saldo");

            Carta carta = new Carta(numero, cvv, scadenza, tipo_carta, saldo);
            listCards.add(carta);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return listCards;
    }

    @Override
    public List<Carta> getAllFamilyCards(int idFamiglia) throws SQLException {
        Connection conn = database.getConnection();
        List<Carta> listCards = new ArrayList<>();

        String sql = "SELECT C.numero, C.cvv, C.scadenza, C.tipo_carta, C.saldo FROM Carta AS C" +
                " JOIN utente as U on U.id_utente = C.id_utente" +
                " WHERE id_famiglia = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idFamiglia);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Carta carta = addCardToList(rs);
            listCards.add(carta);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return listCards;
    }

    private Carta addCardToList(ResultSet rs) throws SQLException {
        String numero = rs.getString("numero");
        String cvv = rs.getString("cvv");
        LocalDate scadenza = rs.getDate("scadenza").toLocalDate();
        String tipo_carta = rs.getString("tipo_carta");
        double saldo = rs.getDouble("saldo");

        return new Carta(numero, cvv, scadenza, tipo_carta, saldo);
    }
}
