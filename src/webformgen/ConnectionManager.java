/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lvbubi
 */
class ConnectionManager {
    private static final String url = "jdbc:sqlserver://adatb-mssql.mik.uni-pannon.hu;databaseName=webuser";    
    private static final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String username = "webuser";   
    private static String password = "Veszprem2017";
    private static Connection con;

     public static Connection getConnection(){
         try {
            Class.forName(driverName);
            try {
                //csatlakozás adatbázishoz
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found."); 
        }
        return con;
     }
}
