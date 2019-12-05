package br.edu.ifsul.dao;

import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;
import org.mariadb.jdbc.internal.com.read.resultset.UpdatableResultSet;

import java.awt.event.PaintEvent;
import java.sql.*;
import java.util.ArrayList;

public class PedidoDAO extends BaseDAO {
    public Pedido getPedidoById(int id){
        Pedido p = new Pedido();
        Connection conn = getConnection();
        try {
            //conn.setAutoCommit(false);
            String sql = "SELECT * FROM pedidos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                p = resultSetToPedido(rs);
            }
            //conn.commit();
            rs.close();
            conn.close();
            stmt.close();
            return p;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(Pedido pedido){
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO pedidos (pagamento, estado, data_criacao, data_modificacao, id_cliente, total_pedido, situacao, notaFiscal) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, pedido.getFormaPagamento());
            stmt.setString(2, pedido.getEstado());
            stmt.setDate(3, new Date(new java.util.Date().getTime()));
            stmt.setDate(4, new Date(new java.util.Date().getTime()));
            stmt.setLong(5, pedido.getCliente().getId());
            stmt.setDouble(6, pedido.getTotalPedido());
            stmt.setBoolean(7, true);
            stmt.setInt(8, 0);
            int cont = stmt.executeUpdate();
            int id = 0;
            if (cont > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close();
            }
            for (Item i : pedido.getItens()){
                try {
                    String sqlItem = "INSERT INTO itens (id_produto, id_pedido, quantidade, total_item, situacao) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
                    stmtItem.setLong(1, i.getProduto().getId());
                    stmtItem.setInt(2, id);
                    stmtItem.setInt(3, i.getQuantidade());
                    stmtItem.setDouble(4, i.getTotalItem());
                    stmtItem.setBoolean(5, true);
                    int count = stmtItem.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
            stmt.close();
            conn.close();
            return cont > 0;
        }catch (SQLException e){
            e.printStackTrace();
            try {
                conn.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }

    public boolean updateSituacao(Pedido pedido){
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            String sql = "UPDATE pedidos set estado = 'faturado', notaFiscal = ? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, pedido.getId());
            stmt.setLong(2, pedido.getId());
            int count = stmt.executeUpdate();
            conn.commit();
            conn.close();
            stmt.close();
            return count > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Pedido resultSetToPedido(ResultSet rs) throws SQLException{
        Pedido pedido = new Pedido();
        ClienteDAO clienteDAO = new ClienteDAO();
        ItemDAO itemDAO = new ItemDAO();

        pedido.setId(rs.getInt("id"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setSituacao(rs.getBoolean("situacao"));
        pedido.setDataCriacao(rs.getDate("data_criacao"));
        pedido.setDataModificacao(rs.getDate("data_modificacao"));
        pedido.setFormaPagamento(rs.getString("pagamento"));
        pedido.setTotalPedido(rs.getDouble("total_pedido"));
        pedido.setNotaFiscal(rs.getInt("notaFiscal"));
        pedido.setCliente(clienteDAO.getClienteById(rs.getLong("id_cliente")));
        pedido.setItens(itemDAO.getItensByPedido(rs.getInt("id")));
        return pedido;
    }
}
