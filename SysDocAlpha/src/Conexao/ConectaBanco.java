package Conexao;

import java.sql.*;

public class ConectaBanco {

    private static Connection con;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public static Connection ConectaBanco() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sysdoc", "root", "kiwi");
            return con;
        } catch (SQLException error) {
            return null;
        }
    }

    /**
     * @return the con
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * @param aCon the con to set
     */
    public static void setCon(Connection aCon) {
        con = aCon;
    }

    /**
     * @return the pst
     */
    public static PreparedStatement getPst() {
        return pst;
    }

    /**
     * @param aPst the pst to set
     */
    public static void setPst(PreparedStatement aPst) {
        pst = aPst;
    }

    /**
     * @return the rs
     */
    public static ResultSet getRs() {
        return rs;
    }

    /**
     * @param aRs the rs to set
     */
    public static void setRs(ResultSet aRs) {
        rs = aRs;
    }
    
}
