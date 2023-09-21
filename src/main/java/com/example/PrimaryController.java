package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.ClienteDao;
import com.example.data.VeiculoDao;
import com.example.model.Cliente;
import com.example.model.Veiculo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    // Campos dos Veículos
    @FXML
    TextField txtMarca;
    @FXML
    TextField txtModelo;
    @FXML
    TextField txtAno;
    @FXML
    TextField txtValor;
    @FXML
    TableView<Veiculo> tabelaVeiculo;
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
    @FXML TableColumn<Cliente, String> colCliente;
    @FXML ComboBox<Cliente> cboCliente;


    //campos do cliente
    @FXML TextField txtNome;
    @FXML TextField txtEmail;
    @FXML TextField txtTelefone;
    @FXML TableView<Cliente> tabelaCliente;
    @FXML TableColumn<Cliente, Integer> colIdCliente;
    @FXML TableColumn<Cliente, String> colNome;
    @FXML TableColumn<Cliente, String> colEmail;
    @FXML TableColumn<Cliente, String> colTelefone;

    VeiculoDao veiculoDao = new VeiculoDao();
    ClienteDao clienteDao = new ClienteDao();

    // métodos do veículo
    public void cadastrarVeiculo() {
        var veiculo = new Veiculo(
                txtMarca.getText(),
                txtModelo.getText(),
                Integer.valueOf(txtAno.getText()),
                new BigDecimal(txtValor.getText()),
                cboCliente.getSelectionModel().getSelectedItem()
            );

        try {
            veiculoDao.inserir(veiculo);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultarVeiculos();
    }

    public void consultarVeiculos() {
        tabelaVeiculo.getItems().clear();
        try {
            veiculoDao.buscarTodos().forEach(veiculo -> tabelaVeiculo.getItems().add(veiculo));
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
        alert.setContentText("Tem certeza que deseja apagar o item selecionado? Esta ação não pode ser desfeita.");
        var resposta = alert.showAndWait();
        return resposta.get().getButtonData().equals(ButtonData.OK_DONE);
    }

    public void apagarVeiculo() {
        var veiculo = tabelaVeiculo.getSelectionModel().getSelectedItem();

        if (veiculo == null) {
            mostrarMensagem("Erro", "Selecione um veículo na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                veiculoDao.apagar(veiculo);
                tabelaVeiculo.getItems().remove(veiculo);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar veículo do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void atualizarVeiculo(Veiculo veiculo) {
        try {
            veiculoDao.atualizar(veiculo);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do veículo");
            e.printStackTrace();
        }
    }


    //métodos do cliente
    public void cadastrarCliente() {
        var cliente = new Cliente(
                txtNome.getText(),
                txtEmail.getText(),
                txtTelefone.getText()
            );

        try {
            clienteDao.inserir(cliente);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultarClientes();
    }

    public void consultarClientes() {
        tabelaCliente.getItems().clear();
        try {
            clienteDao.buscarTodos().forEach(cliente -> tabelaCliente.getItems().add(cliente));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar clientes. " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(event -> atualizarVeiculo(event.getRowValue().marca(event.getNewValue())));

        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> atualizarVeiculo(event.getRowValue().modelo(event.getNewValue())));

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> atualizarVeiculo(event.getRowValue().ano(event.getNewValue())));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> atualizarVeiculo(event.getRowValue().valor(event.getNewValue())));

        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tabelaVeiculo.setEditable(true);

        try {
            cboCliente.getItems().addAll(clienteDao.buscarTodos());
        } catch (SQLException e) {
            mostrarMensagem("Err", "Erro ao carregar clientes");
        }

        consultarVeiculos();
        consultarClientes();
    }

}
