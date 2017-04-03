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
      /*  conn=ConnectionManager.getConnection();
        ProcedureManager Pmgr=new ProcedureManager(conn);
        
        int PersonID=Pmgr.getPersonID("AY440J", "FakePW12");//bejelentkezés 0val tér vissza ha nincs ilyen, PersonID ha van
        
        System.out.println(PersonID);
        int i=0;
        
        Person szemely=Pmgr.getPersonDatas(PersonID);
        System.out.println(szemely.getAdoszam());
        System.out.println(szemely.getBankszamlaszam());
        System.out.println(szemely.getBeosztas());
        System.out.println(szemely.getKnev());
        System.out.println(szemely.getVnev());
        System.out.println(szemely.getNap20());
        System.out.println(szemely.getPersonID());
        
        for(String rendszam :Pmgr.getRendszamok(PersonID)){
            Car kocsi=Pmgr.getCarDatas(rendszam);
            System.out.print(i++);
            System.out.print(".) ");
            System.out.println(kocsi.getHenger());
            System.out.println(kocsi.getGyarto());
            System.out.println(kocsi.getRdsz());
            System.out.println(kocsi.getTipus());
            System.out.println(kocsi.getUzemanyag());
        }*/
    }
}
