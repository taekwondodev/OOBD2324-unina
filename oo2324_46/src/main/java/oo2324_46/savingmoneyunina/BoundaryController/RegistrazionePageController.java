package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.RegistrazioneController;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrazionePageController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private DatePicker dataDiNascita;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button annulla;
    @FXML
    private Button continua;
    @FXML
    private Label errore;

    /******************************/

    @FXML
    public void avanti(ActionEvent event) throws IOException {
        if (
                !nome.getText().isEmpty() && !cognome.getText().isEmpty()
                        && !username.getText().isEmpty() && !email.getText().isEmpty()
                        && !password.getText().isEmpty() && dataDiNascita.getValue() != null
        ){
            try {
                boolean check = RegistrazioneController.getController().creaUtente(
                        username.getText(), email.getText(), password.getText(),
                        nome.getText(), cognome.getText(), dataDiNascita.getValue()
                );
                if (check){
                    errore.setText("");
                    RegistrazioneController.getController().navigation("login");
                }
                else {
                    errore.setText("username o email già usati");
                }
            }catch(SQLException er){
                errore.setText("username o email già usati");
            }
        }
    }
    @FXML
    public void indietro(ActionEvent event) throws IOException {
        RegistrazioneController.getController().navigation("login");
    }
}
