package oo2324_46.savingmoneyunina.BoundaryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.AddCartaFamigliaController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCartaFamigliaPageController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private TextField username;
    @FXML
    private Button buttonF;
    @FXML
    private Label labelF;
    @FXML
    private Button buttonAF;
    @FXML
    private Label labelAF;
    /***************************/
    @FXML
    private TextField numero;
    @FXML
    private DatePicker scadenza;
    @FXML
    private TextField cvv;
    @FXML
    private TextField saldo;
    @FXML
    private Button buttonC;
    @FXML
    private Label labelC;
    @FXML
    private ChoiceBox<String> tipo;
    private final String[] items = {"debito", "credito"};
    private String choice = "";

    @FXML
    private Hyperlink link;
    /*********************************/

    @FXML
    public void addF(ActionEvent e) throws SQLException {
        AddCartaFamigliaController.getController().addFamily(nome.getText());
        labelF.setText("Operazione completata!");
    }

    @FXML
    public void addAF(ActionEvent e){
        if (!username.getText().isEmpty()) {
            try {
                boolean check = AddCartaFamigliaController.getController().addParent(username.getText());
                if (check) {
                    labelAF.setText("Operazione completata!");
                }
                else {
                    labelAF.setText("username non valido");
                }
            }
            catch (SQLException err) {
                System.out.println(err.getMessage());
                labelAF.setText("username non valido");
            }
        }
    }
    @FXML
    public void addC(ActionEvent e) {
        if (
                !numero.getText().isEmpty() && !cvv.getText().isEmpty() && !choice.isEmpty()
                        && !saldo.getText().isEmpty() && scadenza.getValue() != null
        ) {
            boolean check = false;
            try {
                check = AddCartaFamigliaController.getController().addCard(numero.getText(), cvv.getText(), scadenza.getValue(),
                        choice, saldo.getText());
            }
            catch (SQLException error) {
                labelC.setText("carta non valida");
                System.out.println(error.getMessage());
            }
            if (check) {
                labelC.setText("Operazione completata!");
            }
        }
        else {
            labelC.setText("dati non completi");
        }
    }

    @FXML
    public void indietro(ActionEvent e) throws IOException {
        labelF.setText("");
        labelAF.setText("");
        labelC.setText("");
        choice = "";
        AddCartaFamigliaController.getController().navigation("Home");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipo.getItems().addAll(items);
        tipo.setOnAction(this::getTipo);
    }
    public void getTipo(ActionEvent event){
        choice = tipo.getValue();
    }
}