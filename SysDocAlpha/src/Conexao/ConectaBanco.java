package Conexao;

import java.sql.*;

public class ConectaBanco {
 
      
    public static Connection ConectaBanco() throws ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sysdoc","root","kiwi");
                   return con;
        } catch (SQLException error){
            return null;
        }
    }
}
