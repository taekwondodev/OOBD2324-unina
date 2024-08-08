package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.AddTransactionController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTransactionPageController implements Initializable {
    @FXML
    private Hyperlink link;
    @FXML
    private TextField nome;
    @FXML
    private ChoiceBox<String> cards; //mostro il numero delle carte
    @FXML
    private DatePicker data;
    @FXML
    private ChoiceBox<String> tipo;
    @FXML
    private TextField amount;
    @FXML
    private ChoiceBox<String> gruppi; //mostro le stringhe nome di gruppo
    @FXML
    private Label errore; //per il saldo insufficiente, dati non completi, operazione completata
    @FXML
    private Button buttonT;

    private String pickCard = "";
    private String pickTipo = "";
    private final String[] tipi = {"entrata", "uscita"};
    private String pickCategory = "";

    /*******************************************************************/

    @FXML
    public void indietro(ActionEvent e) throws IOException {
        errore.setText("");
        pickCard = "";
        pickTipo = "";
        pickCategory = "";
        AddTransactionController.getController().navigation("Home");
    }
    @FXML
    public void addT(ActionEvent e){
        if(
                !pickCard.isEmpty() && !pickTipo.isEmpty()
                && !amount.getText().isEmpty() && data.getValue() != null
        ){
            if(pickCategory.isEmpty() && nome.getText().isEmpty()){
                try{
                    boolean check = AddTransactionController.getController().insertTransaction(
                            data.getValue(), amount.getText(),
                            pickTipo, pickCard
                            );
                    if (check){
                        errore.setText("Operazione completata!");
                    }
                    else {
                        errore.setText("saldo insufficiente");
                    }
                }catch (SQLException err){
                    errore.setText("saldo insufficiente");
                    System.out.println(err.getMessage());
                }
            }
            else{
                if (!pickCategory.isEmpty()){
                    try{
                        AddTransactionController.getController().insertCategory(nome.getText());
                        boolean check = AddTransactionController.getController().insertTransactionG(
                                data.getValue(), amount.getText(),
                                pickTipo, pickCard, pickCategory
                        );
                        if (check){
                            errore.setText("Operazione completata!");
                        }
                        else{
                            errore.setText("saldo insufficiente");
                        }
                    }catch (SQLException err){
                        errore.setText("saldo insufficiente");
                        System.out.println(err.getMessage());
                    }
                }
                else {
                    try{
                        AddTransactionController.getController().insertCategory(nome.getText());
                        boolean check = AddTransactionController.getController().insertTransactionG(
                                data.getValue(), amount.getText(),
                                pickTipo, pickCard, nome.getText()
                        );
                        if (check){
                            errore.setText("Operazione completata!");
                        }
                        else{
                            errore.setText("saldo insufficiente");
                        }
                    }catch (SQLException err){
                        errore.setText("saldo insufficiente");
                        System.out.println(err.getMessage());
                    }
                }
            }
        }
        else{
            errore.setText("dati non completi");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards.getItems().addAll(AddTransactionController.getController().getAllNumeroCard());
        tipo.getItems().addAll(tipi);
        gruppi.getItems().addAll(AddTransactionController.getController().getAllCategories());

        cards.setOnAction(this::getPickCard);
        tipo.setOnAction(this::getPickTipo);
        gruppi.setOnAction(this::getPickCategory);
    }

    public void getPickCard(ActionEvent event){
        pickCard = cards.getValue();
    }
    public void getPickTipo(ActionEvent event){
        pickTipo = tipo.getValue();
    }
    public void getPickCategory(ActionEvent event){
        pickCategory = gruppi.getValue();
    }
}
