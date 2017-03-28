/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lvbubi
 */
class ProcedureManager {
    private Connection conn=null;
    
    public ProcedureManager(Connection con){
        this.conn=con;
    }
    public String getGyarto(int id,String rendszam){
        CallableStatement cStmt;
        try {   
            //  procedure neve,paraméterek száma: 2 (?,?) KIMENETI ÉS BEMENETI PARAMÉTEREK
            cStmt=conn.prepareCall("{call getGyarto(?,?)}");
            
            //setString ha string a bemeneti paraméter, setInt ha int etc
            //1-es azt jelöli hogy hanyadik BEMENETI paraméter (ha több van)
            cStmt.setString(1, rendszam);
            
            //Kimeneti paraméter neve, típusa
            cStmt.registerOutParameter("parmOUT", java.sql.Types.VARCHAR);
            cStmt.execute();
            
            //A Kimeneti paramétrer lekérdezése
            return cStmt.getString("parmOUT");
        } catch (SQLException ex) {
            Logger.getLogger(WEBFormGen.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
