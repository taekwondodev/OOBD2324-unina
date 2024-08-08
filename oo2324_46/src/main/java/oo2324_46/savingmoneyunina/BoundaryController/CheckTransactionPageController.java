package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.CheckTransactionController;
import oo2324_46.savingmoneyunina.Entity.Transazione;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckTransactionPageController implements Initializable {
    @FXML
    private Hyperlink link;
    @FXML
    private ChoiceBox<String> cards;
    private String pickCard = "";
    @FXML
    private ChoiceBox<String> categorie;
    private String pickCategory = "";
    @FXML
    private DatePicker dInizio;
    @FXML
    private DatePicker dFine;
    @FXML
    private Button cerca;
    @FXML
    private Label errore;

    @FXML
    private TableView<Transazione> table;
    @FXML
    private TableColumn<Transazione, String> numeroCarta;
    @FXML
    private TableColumn<Transazione, String> categoria;
    @FXML
    private TableColumn<Transazione, LocalDate> data;
    @FXML
    private TableColumn<Transazione, String> tipo;
    @FXML
    private TableColumn<Transazione, Double> importo;

    /*****************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards.getItems().addAll(CheckTransactionController.getController().getAllNumeroCard());

        cards.setOnAction(this::getPickCard);
        categorie.setOnAction(this::getPickCategory);

        //Callback perchè in Transazione ho l'oggetto, non l'attributo
        numeroCarta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarta().getNumero()));
        categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getNome()));
        data.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
        tipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoTransazione()));
        importo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getImporto()));

        tableInit();
    }

    @FXML
    public void indietro(ActionEvent e) throws IOException {
        pickCard = "";
        pickCategory = "";
        table.getItems().clear();
        errore.setText("");
        CheckTransactionController.getController().navigation("Home");
    }
    @FXML
    public void handleCerca(ActionEvent e){
        if (!pickCard.isEmpty() && !pickCategory.isEmpty() && dInizio.getValue() != null && dFine.getValue() != null){
            table.getItems().setAll(
                    CheckTransactionController.getController().getAllTransazioni(pickCard, pickCategory, dInizio.getValue(), dFine.getValue())
            );
        }
        else {
            errore.setText("dati non completi!");
        }
    }
    private void tableInit(){
        table.getItems().setAll(CheckTransactionController.getController().getFamilyTransazioni());
    }
    public void getPickCard(ActionEvent event){
        pickCard = cards.getValue();
        categorie.getItems().addAll(CheckTransactionController.getController().getAllCategories(pickCard));
    }
    public void getPickCategory(ActionEvent event){
        pickCategory = categorie.getValue();
    }
}
