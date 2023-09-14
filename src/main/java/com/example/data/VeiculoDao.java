package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Veiculo;

public class VeiculoDao {

    private final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private final String USER = "pf1389";
    private final String PASS = "fiap23";
    
    public void inserir(Veiculo veiculo) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);

        var ps = con.prepareStatement("INSERT INTO veiculos (marca, modelo, ano, preco) VALUES (?, ?, ?, ?)");
        ps.setString(1, veiculo.getMarca());
        ps.setString(2, veiculo.getModelo());
        ps.setInt(3, veiculo.getAno());
        ps.setBigDecimal(4, veiculo.getValor());

        ps.executeUpdate();
        con.close();
    }

    public List<Veiculo> buscarTodos() throws SQLException{
        var veiculos = new ArrayList<Veiculo>();
        var con = DriverManager.getConnection(URL, USER, PASS);
        var rs = con.createStatement().executeQuery("SELECT * FROM veiculos");

        while(rs.next()){
            veiculos.add(new Veiculo(
                rs.getInt("id"),
                rs.getString("marca"), 
                rs.getString("modelo"), 
                rs.getInt("ano"), 
                rs.getBigDecimal("preco")
            ));
        }

        con.close();
        return veiculos;
    }

    public void apagar(Veiculo veiculo) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("DELETE FROM veiculos WHERE id=?"); 
        ps.setInt(1, veiculo.getId());
        ps.executeUpdate();
        con.close();
    }

    public void atualizar(Veiculo veiculo) throws SQLException {
        var con = DriverManager.getConnection(URL, USER, PASS);
        var ps = con.prepareStatement("UPDATE veiculos SET marca=?, modelo=?, ano=?, preco=? WHERE id=?");
        ps.setString(1, veiculo.getMarca());
        ps.setString(2, veiculo.getModelo());
        ps.setInt(3, veiculo.getAno());
        ps.setBigDecimal(4, veiculo.getValor());
        ps.setInt(5, veiculo.getId());
        ps.executeUpdate();
        con.close();

    }

}
