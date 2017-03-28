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
    
    Connection conn=null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WEBFormGen().start();
    }
    public void start(){
        conn=ConnectionManager.getConnection();
        ProcedureManager Pmgr=new ProcedureManager(conn);
        
        String rendszam="MTA662";
        System.out.println(Pmgr.getGyarto(1, rendszam));
        
 
        
    }
}
