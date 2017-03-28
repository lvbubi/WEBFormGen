/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.Connection;

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
        int PersonID=Pmgr.getPersonID("ASD123", "ASD123");//bejelentkezés 0val tér vissza ha nincs ilyen, PersonID ha van
        
        System.out.println(PersonID);
        System.out.println(Pmgr.getGyarto(rendszam));
        
 
        
    }
}
