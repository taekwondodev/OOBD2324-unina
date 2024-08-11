package oo2324_46.savingmoneyunina.Entity;

import java.time.LocalDate;

public class Carta {
    private final String numero;
    private String cvv;
    private LocalDate scadenza;
    private String tipoCarta;
    private double saldo;
    private Account proprietario;

    public Carta(String numero, String cvv, LocalDate scadenza, String tipoCarta, double saldo, Account proprietario) {
        this.numero = numero;
        this.cvv = cvv;
        this.scadenza = scadenza;
        this.tipoCarta = tipoCarta;
        this.saldo = saldo;
        this.proprietario = proprietario;
    }

    public Carta(String numero, String cvv, LocalDate scadenza, String tipoCarta, double saldo) {
        this.numero = numero;
        this.cvv = cvv;
        this.scadenza = scadenza;
        this.tipoCarta = tipoCarta;
        this.saldo = saldo;
    }

    public Carta(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public String getTipoCarta() {
        return tipoCarta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Account getProprietario() {
        return proprietario;
    }
}
