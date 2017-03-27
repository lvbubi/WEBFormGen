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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lvbubi
 */
public class WEBFormGen {
String rendszam="MTA662";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WEBFormGen().start();
    }
    public void start(){
        
        Connection conn;
         CallableStatement cStmt;
        try {
            //csatlakozás adatbázishoz
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://adatb-mssql.mik.uni-pannon.hu;databaseName=webuser","webuser","Veszprem2017");
            
            
            //  procedure neve,paraméterek száma: 2 (?,?) KIMENETI ÉS BEMENETI PARAMÉTEREK
            cStmt=conn.prepareCall("{call getGyarto(?,?)}");
            
            //setString ha string a bemeneti paraméter, setInt ha int etc
            //1-es azt jelöli hogy hanyadik BEMENETI paraméter (ha több van)
            cStmt.setString(1, rendszam);
            
            
            //Kimeneti paraméter neve, típusa
            cStmt.registerOutParameter("parmOUT", java.sql.Types.VARCHAR);
            cStmt.execute();
            
            //A Kimeneti paramétrer lekérdezése
            System.out.println(cStmt.getString("parmOUT"));
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(WEBFormGen.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        
    }
}
