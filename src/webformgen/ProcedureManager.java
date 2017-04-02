/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    public String getGyarto(String rendszam){
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

    public int getPersonID(String neptun,String password){
        CallableStatement cStmt;
        try {
            cStmt=conn.prepareCall("{call tryBelepes(?,?,?)}");
            cStmt.setString(1, neptun);
            cStmt.setString(2, password);
            cStmt.registerOutParameter("personID", java.sql.Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt("personID");
        } catch (SQLException ex) {
            Logger.getLogger(ProcedureManager.class.getName()).log(Level.SEVERE, null, ex); 
            return 0;
        }
        
    }
    public List<String> getRendszamok(int PersonID){
        List<String> rendszamok=new ArrayList<>();
        
        try {
                    Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("SELECT rdsz FROM kocsipark where id = "+Integer.toString(PersonID));
            while(rs.next()){
                rendszamok.add(rs.getString("rdsz"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProcedureManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rendszamok;
    }


}
