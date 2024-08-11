package oo2324_46.savingmoneyunina.Entity;

public class SessioneUtente {
    private static SessioneUtente sessioneUtente = null;
    private Account account;
    private SessioneUtente(){}
    public static SessioneUtente getSessioneUtente(){
        if (sessioneUtente == null){
            sessioneUtente = new SessioneUtente();
        }
        return sessioneUtente;
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public void logOut(){
        sessioneUtente = null;
    }
}
