package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autor;

public class AutorDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "rm99570";
    private final String PASS = "310505";
    
    public void inserir(Autor cliente) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO autores (nome, nacionalidade) VALUES (?, ?)");
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getNacionalidade());

        ps.executeUpdate();
        con.close();
    }

    public List<Autor> buscarTodos() throws SQLException{
        var clientes = new ArrayList<Autor>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT * FROM autores");

        while(rs.next()){
            clientes.add(new Autor(
                rs.getInt("id"),
                rs.getString("nome"), 
                rs.getString("nacionalidade")
            ));
        }

        con.close();
        return clientes;
    }

    public void apagar(Autor cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM autores WHERE id=?"); 
        ps.setInt(1, cliente.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Autor cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE autores SET nome=?, nacionalidade=? WHERE id=?");
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getNacionalidade());
        ps.setInt(4, cliente.getId());
        
        ps.executeUpdate();
        con.close();

    }

}
