package oo2324_46.savingmoneyunina.DAO.Implements;

import oo2324_46.savingmoneyunina.DAO.Interface.TransazioneDAO;
import oo2324_46.savingmoneyunina.Entity.Carta;
import oo2324_46.savingmoneyunina.Entity.Gruppo;
import oo2324_46.savingmoneyunina.Entity.Report;
import oo2324_46.savingmoneyunina.Entity.Transazione;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransazioneImplements implements TransazioneDAO {
    @Override
    public boolean save(Transazione transazione) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "INSERT INTO transazione(data, importo, tipo_transazione, numero_carta) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(transazione.getData()));
        ps.setDouble(2, transazione.getImporto());
        ps.setString(3, transazione.getTipoTransazione());
        ps.setString(4, transazione.getCarta().getNumero());

        int result = ps.executeUpdate(); //il numero di righe inserito

        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return result > 0;
    }
    @Override
    public boolean saveG(Transazione transazione, int idGruppo) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "INSERT INTO transazione(data, importo, tipo_transazione, numero_carta, id_gruppo) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(transazione.getData()));
        ps.setDouble(2, transazione.getImporto());
        ps.setString(3, transazione.getTipoTransazione());
        ps.setString(4, transazione.getCarta().getNumero());
        ps.setInt(5, idGruppo);

        int result = ps.executeUpdate(); //il numero di righe inserito

        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return result > 0;
    }

    @Override
    public List<Transazione> filter(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException {
        Connection conn = database.getConnection();
        List<Transazione> list = new ArrayList<>();

        String sql = "SELECT t.numero_carta, g.nome, t.data, t.tipo_transazione, t.importo" +
                " FROM transazione t" +
                " JOIN gruppo g on g.id_gruppo = t.id_gruppo" +
                " WHERE t.numero_carta = ? AND g.nome = ? AND t.data BETWEEN ? AND ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, numeroCarta);
        ps.setString(2, nomeCategoria);
        ps.setDate(3, Date.valueOf(dataInizio));
        ps.setDate(4, Date.valueOf(dataFine));
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Transazione transazione = addTransazioneToList(rs);
            list.add(transazione);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return list;
    }

    @Override
    public List<Transazione> familyTransactions(int idFamiglia) throws SQLException {
        Connection conn = database.getConnection();
        List<Transazione> list = new ArrayList<>();

        String sql = "SELECT t.numero_carta, g.nome, t.data, t.tipo_transazione, t.importo" +
                " FROM transazione t" +
                " LEFT JOIN gruppo g on g.id_gruppo = t.id_gruppo" +
                " JOIN carta c on c.numero = t.numero_carta" +
                " JOIN utente u on c.id_utente = u.id_utente" +
                " WHERE u.id_famiglia = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idFamiglia);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Transazione transazione = addTransazioneToList(rs);
            list.add(transazione);
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return list;
    }

    @Override
    public Report reportValues(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "WITH TransazioneFilt AS (" +
                "    SELECT t.numero_carta, g.nome, t.data, t.tipo_transazione, t.importo" +
                "    FROM transazione t" +
                "    JOIN gruppo g ON g.id_gruppo = t.id_gruppo" +
                "    WHERE t.numero_carta = ? AND g.nome = ? AND t.data BETWEEN ? AND ?" +
                ") " +
                "SELECT" +
                " COALESCE(MAX(CASE WHEN tf.tipo_transazione = 'entrata' THEN tf.importo END), 0) AS max_entrata," +
                " COALESCE(MIN(CASE WHEN tf.tipo_transazione = 'entrata' THEN tf.importo END), 0) AS min_entrata," +
                " COALESCE(AVG(CASE WHEN tf.tipo_transazione = 'entrata' THEN tf.importo END), 0) AS avg_entrata," +
                " COALESCE(MAX(CASE WHEN tf.tipo_transazione = 'uscita' THEN tf.importo END), 0) AS max_uscita," +
                " COALESCE(MIN(CASE WHEN tf.tipo_transazione = 'uscita' THEN tf.importo END), 0) AS min_uscita," +
                " COALESCE(AVG(CASE WHEN tf.tipo_transazione = 'uscita' THEN tf.importo END), 0) AS avg_uscita," +
                " COALESCE(SUM(CASE WHEN tf.tipo_transazione = 'entrata' THEN tf.importo ELSE 0 END), 0)," +
                " COALESCE(SUM(CASE WHEN tf.tipo_transazione = 'uscita' THEN tf.importo ELSE 0 END), 0) AS saldo_totale," +
                " c.saldo " +
                "FROM TransazioneFilt tf " +
                "JOIN carta c ON c.numero = tf.numero_carta " +
                "GROUP BY c.saldo";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, numeroCarta);
            ps.setString(2, nomeCategoria);
            ps.setDate(3, Date.valueOf(dataInizio));
            ps.setDate(4, Date.valueOf(dataFine));

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Report(
                            rs.getDouble("max_entrata"), rs.getDouble("min_entrata"), rs.getDouble("avg_entrata"),
                            rs.getDouble("max_uscita"), rs.getDouble("min_uscita"), rs.getDouble("avg_uscita"),
                            rs.getDouble("saldo_totale"), rs.getDouble("saldo")
                    );
                }
                else {
                    return null;
                }
            }
        }
    }

    @Override
    public double[] reportPercent(String numeroCarta, String nomeCategoria, LocalDate dataInizio, LocalDate dataFine) throws SQLException {
        Connection conn = database.getConnection();

        String sql = "SELECT " +
                "    (SELECT COUNT(*) FROM transazione t " +
                "     JOIN gruppo g ON g.id_gruppo = t.id_gruppo " +
                "     WHERE t.numero_carta = ? AND g.nome = ? AND t.data BETWEEN ? AND ?) AS countGruppo, " +
                "    (SELECT COUNT(*) FROM transazione t " +
                "     WHERE t.numero_carta = ? AND t.data BETWEEN ? AND ?) AS countCarta";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, numeroCarta);
        ps.setString(2, nomeCategoria);
        ps.setDate(3, Date.valueOf(dataInizio));
        ps.setDate(4, Date.valueOf(dataFine));
        ps.setString(5, numeroCarta);
        ps.setDate(6, Date.valueOf(dataInizio));
        ps.setDate(7, Date.valueOf(dataFine));

        ResultSet rs = ps.executeQuery();
        double percentGruppo = 0.0;
        double percentOther = 0.0;
        if (rs.next()){
            int countGruppo = rs.getInt("countGruppo");
            int countCarta = rs.getInt("countCarta");

            if (countCarta > 0){
                percentGruppo = ((double) countGruppo / countCarta) * 100;
                percentOther = ((double) (countCarta - countGruppo) / countCarta) * 100;
            }
        }

        database.closeResultSet(rs);
        database.closePreparedStatement(ps);
        database.closeConnection(conn);

        return new double[]{percentGruppo, percentOther};
    }

    private Transazione addTransazioneToList(ResultSet rs) throws SQLException{
        LocalDate data = rs.getDate("data").toLocalDate();
        double importo = rs.getDouble("importo");
        String tipo_transazione = rs.getString("tipo_transazione");
        String numero_carta = rs.getString("numero_carta");
        String nome = rs.getString("nome");

        Carta carta = new Carta(numero_carta);
        Gruppo gruppo = new Gruppo(nome);
        return new Transazione(data, importo, tipo_transazione, carta, gruppo);
    }
}
