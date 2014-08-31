package Conexao;

import java.sql.*;

public class ConnectionFactory {
    public Connection con;
    public Statement stmt;
    public ResultSet rs;
    
     String url = "jdbc:mysql://localhost:3306/sysdoc";
     String driver = "com.mysql.jdbc.Driver";
     String user = "root";
     String password = "kiwi";
    
    public void abrirConexao(){
        try {
            Class.forName(driver);
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
        } catch (Exception e){
        }
    }
}
