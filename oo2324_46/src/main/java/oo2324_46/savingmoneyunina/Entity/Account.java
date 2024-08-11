package oo2324_46.savingmoneyunina.Entity;

public class Account {
    private String username;
    private String email;
    private final String password;
    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
