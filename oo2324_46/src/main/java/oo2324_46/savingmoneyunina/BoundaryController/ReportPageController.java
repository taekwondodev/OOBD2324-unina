package oo2324_46.savingmoneyunina.BoundaryController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import oo2324_46.savingmoneyunina.Controller.ReportController;
import oo2324_46.savingmoneyunina.Entity.Report;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportPageController implements Initializable {
    @FXML
    private Hyperlink link;
    @FXML
    private ChoiceBox<String> cards;
    private String pickCard;
    @FXML
    private DatePicker dataI;
    @FXML
    private DatePicker dataF;
    @FXML
    private ChoiceBox<String> categorie;
    private String pickCategory;
    @FXML
    private Button button;


    @FXML
    private Label entrataMax;
    @FXML
    private Label entrataMin;
    @FXML
    private Label entrataMid;
    @FXML
    private Label uscitaMax;
    @FXML
    private Label uscitaMin;
    @FXML
    private Label uscitaMid;
    @FXML
    private Label saldoI;
    @FXML
    private Label saldoF;
    @FXML
    private PieChart chart;

    /************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards.getItems().addAll(ReportController.getController().getAllNumeroCard());

        cards.setOnAction(this::getPickCard);
        categorie.setOnAction(this::getPickCategory);
    }

    public void indietro(ActionEvent e) throws IOException {
        pickCard = "";
        pickCategory = "";
        entrataMax.setText("");
        entrataMin.setText("");
        entrataMid.setText("");
        uscitaMax.setText("");
        uscitaMin.setText("");
        uscitaMid.setText("");
        saldoI.setText("");
        saldoF.setText("");
        chart.getData().clear();

        ReportController.getController().navigation("Home");
    }
    @FXML
    public void genera(ActionEvent e) {
        Report report = ReportController.getController().getReport(pickCard, pickCategory, dataI.getValue(), dataF.getValue());
        double[] percentages = ReportController.getController().getPercentage(pickCard, pickCategory, dataI.getValue(), dataF.getValue());

        chartSetup(pickCategory, percentages[0], percentages[1]);

        entrataMax.setText(report.maxEntrata() + "€");
        entrataMin.setText(report.minEntrata() + "€");
        entrataMid.setText(report.midEntrata() + "€");
        uscitaMax.setText(report.maxUscita() + "€");
        uscitaMin.setText(report.minUscita() + "€");
        uscitaMid.setText(report.midUscita() + "€");
        saldoI.setText(report.saldoTotale() + "€");
        saldoF.setText(report.saldoCorrente() + "€");
    }
    @FXML
    public void getPickCard(ActionEvent event){
        pickCard = cards.getValue();
        categorie.getItems().addAll(ReportController.getController().getAllCategories(pickCard));
    }
    @FXML
    public void getPickCategory(ActionEvent event){
        pickCategory = categorie.getValue();
    }

    private void chartSetup(String nomeCategoria, double percentCategory, double other){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(nomeCategoria, percentCategory),
                        new PieChart.Data("Altro", other)
                );
        chart.setData(pieChartData);
        chart.setStartAngle(180);

        pieChartData.forEach(data -> {
            String percentage = String.format("%.2f%%", data.getPieValue());
            data.nameProperty().set(data.getName() + " " + percentage);
        });
    }

}
