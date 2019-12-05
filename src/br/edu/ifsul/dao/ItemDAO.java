package br.edu.ifsul.dao;

import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends BaseDAO{
    public List<Item> getItensByPedido(int id){
        List<Item> itens = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM itens WHERE id_pedido = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                itens.add(resultsetToItens(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return itens;
    }

    public boolean softDeleteItemByPedido(int idPedido, int idItem){
        try {
            Connection conn = getConnection();
            String sql = "UPDATE itens SET situacao=0 WHERE id_pedido = ? AND id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idItem);
            int count = stmt.executeUpdate();
            conn.close();
            stmt.close();
            return count > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private Item resultsetToItens(ResultSet rs) throws SQLException{
        Item item = new Item();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        item.setId(rs.getLong("id"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setSituacao(rs.getBoolean("situacao"));
        item.setTotalItem(rs.getDouble("total_item"));
        item.setProduto(produtoDAO.getProdutoId(rs.getInt("id_produto")));
        return item;
    }
}
