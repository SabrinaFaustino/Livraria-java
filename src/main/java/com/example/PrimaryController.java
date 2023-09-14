package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.VeiculoDao;
import com.example.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    @FXML
    TextField txtMarca;
    @FXML
    TextField txtModelo;
    @FXML
    TextField txtAno;
    @FXML
    TextField txtValor;
    @FXML
    TableView<Veiculo> tabela;
    @FXML
    TableColumn<Veiculo, Integer> colId;
    @FXML
    TableColumn<Veiculo, String> colMarca;
    @FXML
    TableColumn<Veiculo, String> colModelo;
    @FXML
    TableColumn<Veiculo, Integer> colAno;
    @FXML
    TableColumn<Veiculo, BigDecimal> colValor;

    VeiculoDao dao = new VeiculoDao();

    public void cadastrar() {
        var veiculo = new Veiculo(
                txtMarca.getText(),
                txtModelo.getText(),
                Integer.valueOf(txtAno.getText()),
                new BigDecimal(txtValor.getText()));

        try {
            dao.inserir(veiculo);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultar();
    }

    public void consultar() {
        tabela.getItems().clear();
        try {
            dao.buscarTodos().forEach(veiculo -> tabela.getItems().add(veiculo));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar veículo. " + e.getMessage());
        }
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o veículo selecionado? Esta ação não pode ser desfeita.");
        var resposta = alert.showAndWait();
        return resposta.get().getButtonData().equals(ButtonData.OK_DONE);
    }

    public void apagar() {
        var veiculo = tabela.getSelectionModel().getSelectedItem();

        if (veiculo == null) {
            mostrarMensagem("Erro", "Selecione um veículo na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                dao.apagar(veiculo);
                tabela.getItems().remove(veiculo);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar veículo do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void atualizar(Veiculo veiculo) {
        try {
            dao.atualizar(veiculo);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do veículo");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(event -> atualizar(event.getRowValue().marca(event.getNewValue())));

        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> atualizar(event.getRowValue().modelo(event.getNewValue())));

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> atualizar(event.getRowValue().ano(event.getNewValue())));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> atualizar(event.getRowValue().valor(event.getNewValue())));

        tabela.setEditable(true);

        consultar();
    }

}
