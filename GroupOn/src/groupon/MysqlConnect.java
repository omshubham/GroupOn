/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupon;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author ABHICRUISER
 */
public class MysqlConnect {
    Connection conn=null;
    public static Connection ConnectDB() {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
//            Connection conn = null;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testsofta", "root", "");
            System.out.println("connected");
            return conn;
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
}
