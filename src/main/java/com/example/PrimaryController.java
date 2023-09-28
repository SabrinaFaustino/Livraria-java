package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.AutorDao;
import com.example.data.LivroDao;
import com.example.model.Autor;
import com.example.model.Livro;

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
    TextField txtEditora;
    @FXML
    TextField txtTitulo;
    @FXML
    TextField txtAno;
    @FXML
    TextField txtValor;
    @FXML
    TableView<Livro> tabelaLivro;
    @FXML
    TableColumn<Livro, Integer> colId;
    @FXML
    TableColumn<Livro, String> colEditora;
    @FXML
    TableColumn<Livro, String> colTitulo;
    @FXML
    TableColumn<Livro, Integer> colAno;
    @FXML
    TableColumn<Livro, BigDecimal> colValor;

    @FXML TableColumn<Autor, String> colAutor;
    @FXML ComboBox<Autor> cboAutor;


    //campos do autor
    @FXML TextField txtNome;
    @FXML TextField txtNacionalidade;
    @FXML TableView<Autor> tabelaAutor;
    @FXML TableColumn<Autor, Integer> colIdAutor;
    @FXML TableColumn<Autor, String> colNome;
    @FXML TableColumn<Autor, String> colNacionalidade;

    LivroDao livroDao = new LivroDao();
    AutorDao autorDao = new AutorDao();

    // métodos do Livro
    public void cadastrarLivro() {
        var livro = new Livro(
                txtEditora.getText(),
                txtTitulo.getText(),
                Integer.valueOf(txtAno.getText()),
                new BigDecimal(txtValor.getText()),
                cboAutor.getSelectionModel().getSelectedItem()
            );

        try {
            livroDao.inserir(livro);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultarLivros();
    }

    public void consultarLivros() {
        tabelaLivro.getItems().clear();
        try {
            livroDao.buscarTodos().forEach(livro -> tabelaLivro.getItems().add(livro));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar livro. " + e.getMessage());
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

    public void apagarLivro() {
        var livro = tabelaLivro.getSelectionModel().getSelectedItem();

        if (livro == null) {
            mostrarMensagem("Erro", "Selecione um livro na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                livroDao.apagar(livro);
                tabelaLivro.getItems().remove(livro);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar livro do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void atualizarLivro(Livro livro) {
        try {
            livroDao.atualizar(livro);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do livro");
            e.printStackTrace();
        }
    }


    //métodos do autor
    public void cadastrarAutor() {
        var autor = new Autor(
                txtNome.getText(),
                txtNacionalidade.getText()
            );

        try {
            autorDao.inserir(autor);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultarAutores();
    }

    public void consultarAutores() {
        tabelaAutor.getItems().clear();
        try {
            autorDao.buscarTodos().forEach(autor -> tabelaAutor.getItems().add(autor));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar autor. " + e.getMessage());
        }
    }

    public void apagarAutor() {
        var autor = tabelaAutor.getSelectionModel().getSelectedItem();

        if (autor == null) {
            mostrarMensagem("Erro", "Selecione um livro na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                autorDao.apagar(autor);
                tabelaLivro.getItems().remove(autor);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar livro do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

        consultarAutores();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));
        colEditora.setCellFactory(TextFieldTableCell.forTableColumn());
        colEditora.setOnEditCommit(event -> atualizarLivro(event.getRowValue().editora(event.getNewValue())));

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTitulo.setOnEditCommit(event -> atualizarLivro(event.getRowValue().titulo(event.getNewValue())));

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> atualizarLivro(event.getRowValue().ano(event.getNewValue())));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> atualizarLivro(event.getRowValue().valor(event.getNewValue())));

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        colIdAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));

        tabelaLivro.setEditable(true);

        try {
            cboAutor.getItems().addAll(autorDao.buscarTodos());
        } catch (SQLException e) {
            mostrarMensagem("Err", "Erro ao carregar autores");
        }

        consultarLivros();
        consultarAutores();
    }

}   
