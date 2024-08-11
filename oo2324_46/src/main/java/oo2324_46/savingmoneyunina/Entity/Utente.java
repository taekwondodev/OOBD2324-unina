package oo2324_46.savingmoneyunina.Entity;

import java.time.LocalDate;

public class Utente {
    private final Account account;
    private final String nome;
    private final String cognome;
    private final LocalDate dataDiNascita;
    private Famiglia famiglia; //no nel costruttore, opzionale

    public Utente(Account account, String nome, String cognome, LocalDate dataDiNascita) {
        this.account = account;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
    }

    public Account getAccount() {
        return account;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Famiglia getFamiglia() {
        return famiglia;
    }

    public void setFamiglia(Famiglia famiglia) {
        this.famiglia = famiglia;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }
}
