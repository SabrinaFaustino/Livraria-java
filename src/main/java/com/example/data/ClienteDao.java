package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;

public class ClienteDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "pf1389";
    private final String PASS = "fiap23";
    
    public void inserir(Cliente cliente) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)");
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getEmail());
        ps.setString(3, cliente.getTelefone());

        ps.executeUpdate();
        con.close();
    }

    public List<Cliente> buscarTodos() throws SQLException{
        var clientes = new ArrayList<Cliente>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT * FROM clientes");

        while(rs.next()){
            clientes.add(new Cliente(
                rs.getInt("id"),
                rs.getString("nome"), 
                rs.getString("email"), 
                rs.getString("telefone")
            ));
        }

        con.close();
        return clientes;
    }

    public void apagar(Cliente cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM clientes WHERE id=?"); 
        ps.setInt(1, cliente.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Cliente cliente) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE clientes SET nome=?, email=?, telefone=? WHERE id=?");
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getEmail());
        ps.setString(3, cliente.getTelefone());
        ps.setInt(4, cliente.getId());
        
        ps.executeUpdate();
        con.close();

    }

}
