package br.edu.ifsul.dao;

import br.edu.ifsul.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends BaseDAO{
    public List<Produto> getProduto(){
        List<Produto> produtos = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                produtos.add(resultsetToProduto(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return produtos;
    }
    public Produto getProdutoId(int id){
        Produto produto = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                produto = resultsetToProduto(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produto;
        }
    }

    public List<Produto> getProdutoSituacao(boolean situacao){
        List<Produto> produtos = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE situacao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, situacao);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                produtos.add(resultsetToProduto(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Produto> getProdutoName(String nome){
        List<Produto> produtos = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE nome LIKE ? ORDER BY nome";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                produtos.add(resultsetToProduto(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean insert(Produto produto){
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO produtos (nome, valor, descricao, situacao, quantidade) VALUES (?, ?, ?, 1, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getDescricao());
            stmt.setInt(4, produto.getEstoque());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Produto produto){
        try {
            Connection conn = getConnection();
            String sql = "UPDATE produtos SET nome=?, valor=?, descricao=?, situacao=?, quantidade=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getDescricao());
            stmt.setBoolean(4, produto.getSituacao());
            stmt.setInt(5, produto.getEstoque());
            stmt.setInt(6, produto.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean softDelete(Produto produto){
        try {
            Connection conn = getConnection();
            String sql = "UPDATE produtos SET situacao=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setLong(2, produto.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Produto resultsetToProduto(ResultSet rs) throws SQLException{
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setValor(rs.getDouble("valor"));
        p.setDescricao(rs.getString("descricao"));
        p.setSituacao(rs.getBoolean("situacao"));
        p.setEstoque(rs.getInt("quantidade"));
        return p;
    }

    public static void main(String[] args) {
        ProdutoDAO produtoDAO= new ProdutoDAO();
        //Produto produto = new Produto(1,"Fone de ouvido", 2.5, "para ouvir", true, 23);
        //produtoDAO.insert(produto);
        System.out.println("Produtos:\n"+produtoDAO.getProduto());
    }
}
