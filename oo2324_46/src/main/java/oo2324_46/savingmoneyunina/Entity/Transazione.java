package oo2324_46.savingmoneyunina.Entity;

import java.time.LocalDate;

public class Transazione {
    private final LocalDate data;
    private final double importo;
    private final String tipoTransazione;
    private final Carta carta;
    private Gruppo categoria; //no nel costruttore, opzionale

    public Transazione(LocalDate data, double importo, String tipoTransazione, Carta carta) {
        this.data = data;
        this.importo = importo;
        this.tipoTransazione = tipoTransazione;
        this.carta = carta;
    }

    public Transazione(LocalDate data, double importo, String tipoTransazione, Carta carta, Gruppo categoria) {
        this.data = data;
        this.importo = importo;
        this.tipoTransazione = tipoTransazione;
        this.carta = carta;
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public double getImporto() {
        return importo;
    }

    public String getTipoTransazione() {
        return tipoTransazione;
    }

    public Gruppo getCategoria() {
        return categoria;
    }

    public Carta getCarta() {
        return carta;
    }
}
