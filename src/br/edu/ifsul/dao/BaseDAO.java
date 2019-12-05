package br.edu.ifsul.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {

    public Connection getConnection(){
        try {
            //string com a url para o banco de dados
            //sintaxe: protocolo:tecnologia://domínioDoServidor:porta/database
            String url = "jdbc:mariadb://localhost:3306/venda";
            String user = "root";
            String pass = "123";
            //argumentos: url para o banco, usuário, senha.
            //retorna um objeto da classe Connection (do pacote java.sql -> que segue a especificação JDBC).
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            //se não conectar, retorna null
            return null;
        }
    }

    public static void main(String[] args) {
        BaseDAO dao = new BaseDAO();
        System.out.println(dao.getConnection());
    }
}
