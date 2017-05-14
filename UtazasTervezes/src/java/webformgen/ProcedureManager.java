/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author lvbubi
 */

@Singleton
public class ProcedureManager {
    Connection conn=null;
    
    @PostConstruct
    public void activate(){
        conn=ConnectionManager.getConnection();
    }

    
    public void DeletePDF(int id) throws SQLException{
        CallableStatement cStmt;
         cStmt=conn.prepareCall("{call DeletePDF(?)}");
         cStmt.setInt(1, id);
         cStmt.execute();
    }
    
    public int getPersonID(String neptun,String password) throws SQLException{
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call tryBelepes(?,?,?)}");
            cStmt.setString(1, neptun);
            cStmt.setString(2, password);
            cStmt.registerOutParameter("personID", java.sql.Types.INTEGER);
            cStmt.execute();
            return cStmt.getInt("personID");
    }
    public int getJogosultsag(String neptun, String password) throws SQLException{
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call tryBeIsAdmin(?,?,?)}");
            cStmt.setString(1, neptun);
            cStmt.setString(2, password);
            cStmt.registerOutParameter("jogos", java.sql.Types.INTEGER);
            cStmt.execute();
            System.out.println(cStmt.getInt("jogos"));
            return cStmt.getInt("jogos");
    }
    
    public List<String> getRendszamok(int PersonID) throws SQLException{
        List<String> rendszamok=new ArrayList<>();
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery("SELECT rdsz FROM kocsipark where id = "+Integer.toString(PersonID));
            while(rs.next()){
                rendszamok.add(rs.getString("rdsz"));
            }
            rs.close();
        return rendszamok;
    }
    
    public byte[] downloadPDF(int pdfID,String doctype) throws SQLException{
        byte[] receivedPDF=null;
                System.out.println("Preparing PDF ON ID: "+pdfID);
        Statement select;
            select = new ConnectionManager().getConnection().createStatement();
            ResultSet rs;
            if("Szemelygepkocsi Kalkulacio".equals(doctype))
                rs = select.executeQuery("SELECT adatok FROM GeneraltPDF WHERE id = "+pdfID+" and DokumentumTipusa='"+doctype+"'");
            
            else rs = select.executeQuery("SELECT adatok FROM GeneraltUtiterv WHERE id = "+pdfID+" and DokumentumTipusa='"+doctype+"'");
            rs = select.getResultSet();
            rs.next() ;
            receivedPDF = rs.getBytes("adatok");
        return receivedPDF;
    }
    
    
    
    
   public List<PDFDatas> getPDFDatas(int PersonID,int pdfState,String doctype) throws SQLException{
        int id;
        Date Datum;
        String Hova;
        float OsszkoltsegFT;
        String DokumentumTipusa;
        int Ellenorizve;
        List<PDFDatas> pdfKeys=new ArrayList<>();
        Statement st=conn.createStatement();
        ResultSet rs;
        if("Szemelygepkocsi Kalkulacio".equals(doctype))
            rs=st.executeQuery("SELECT id,SzemelyID,Datum,Hova,OsszkoltsegFt,DokumentumTipusa,"
                    + "Ellenorizve FROM GeneraltPDF where SzemelyID = "+Integer.toString(PersonID)
                    +" and Ellenorizve = "+Integer.toString(pdfState)
            );
        else
            rs=st.executeQuery("select GeneraltUtiterv.id,SzemelyID,Datum,Hova,OsszkoltsegFt,GeneraltUtiterv.DokumentumTipusa,GeneraltUtiterv.Ellenorizve,GeneraltUtiterv.adatok from GeneraltUtiterv"+
            " JOIN GeneraltPDF ON GeneraltPDF.id=szgkPDF_id"+
                " where SzemelyID = "+Integer.toString(PersonID)+" and GeneraltUtiterv.Ellenorizve = "+Integer.toString(pdfState));
        while(rs.next()){
            id=rs.getInt("id");
            Datum=rs.getDate("Datum");
            Hova=rs.getString("Hova");
            OsszkoltsegFT=rs.getFloat("OsszkoltsegFt");
            DokumentumTipusa=rs.getString("DokumentumTipusa");
            Ellenorizve=rs.getInt("Ellenorizve");
            
            pdfKeys.add(new PDFDatas(id,PersonID,Datum,Hova,OsszkoltsegFT,DokumentumTipusa,Ellenorizve));
        }
        return pdfKeys;
    } 
    public void setEllenorzott(int id) throws SQLException{
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call setElfogad(?)}");
            cStmt.setInt(1, id);
            cStmt.execute();
    }
   
      public List<PDFDatas> getPDFDatasAdmin(int ellenorzott,String doctype) throws SQLException{
        int id;
        int szemelyID;
        Date Datum;
        String Hova;
        float OsszkoltsegFT;
        String DokumentumTipusa;
        int Ellenorizve;
        
        List<PDFDatas> pdfKeys=new ArrayList<>();
        Statement st=conn.createStatement();
        ResultSet rs;
        if("Szemelygepkocsi Kalkulacio".equals(doctype))
            rs=st.executeQuery("SELECT id,SzemelyID,Datum,Hova,OsszkoltsegFt,DokumentumTipusa,Ellenorizve FROM GeneraltPDF where Ellenorizve = "+ ellenorzott);
        
        else rs=st.executeQuery("select GeneraltUtiterv.id,SzemelyID,Datum,Hova,OsszkoltsegFt,GeneraltUtiterv.DokumentumTipusa,GeneraltUtiterv.Ellenorizve,GeneraltUtiterv.adatok from GeneraltUtiterv"+
            " JOIN GeneraltPDF ON GeneraltPDF.id=szgkPDF_id"+
                " where GeneraltUtiterv.Ellenorizve = "+ellenorzott);
        while(rs.next()){
            id=rs.getInt("id");
            szemelyID=rs.getInt("SzemelyID");
            Datum=rs.getDate("Datum");
            Hova=rs.getString("Hova");
            OsszkoltsegFT=rs.getFloat("OsszkoltsegFt");
            DokumentumTipusa=rs.getString("DokumentumTipusa");
            Ellenorizve=rs.getInt("Ellenorizve");
            
            pdfKeys.add(new PDFDatas(id,szemelyID,Datum,Hova,OsszkoltsegFT,DokumentumTipusa,Ellenorizve));
        }
        return pdfKeys;
    } 
    public Car getCarDatas(String rdsz) throws SQLException{
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call getCarDatas(?,?,?,?,?)}");
            cStmt.setString(1, rdsz);
            cStmt.registerOutParameter("henger", java.sql.Types.INTEGER);
            cStmt.registerOutParameter("gyarto", java.sql.Types.VARCHAR);
            cStmt.registerOutParameter("tipus", java.sql.Types.VARCHAR);
            cStmt.registerOutParameter("uzemanyag", java.sql.Types.VARCHAR);
            cStmt.execute();
            return new Car(rdsz,cStmt.getString("gyarto"),cStmt.getString("tipus"),cStmt.getString("uzemanyag"),cStmt.getInt("henger"));
    }

    public void InsertPDF(int PersonID,String hova,double Osszkoltseg,String DukumentumNev,byte[] pdfBytes) throws SQLException{
        System.out.println("Kezdet:\nID: "+PersonID+" hova: "+hova+" Osszktg: "+Osszkoltseg);
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call AddPDF(?,?,?,?,?)}");
            cStmt.setInt(1, PersonID);
            cStmt.setString(2, hova);
            cStmt.setDouble(3, Osszkoltseg);
            cStmt.setString(4, DukumentumNev);
            cStmt.setBytes(5, pdfBytes);
            cStmt.execute();
        System.out.println("Elment");
    }
    
    public void InsertUtvonal(int szgkPDF_id,byte pdfBytes[]) throws SQLException{
        System.out.println("kivalaszottID: "+szgkPDF_id);
        CallableStatement cStmt;
        cStmt=conn.prepareCall("{call AddUtiterv(?,?)}");
        cStmt.setInt(1, szgkPDF_id);
        cStmt.setBytes(2, pdfBytes);
        cStmt.execute();
    }
    
    
    public Person getPersonDatas(int PersonID) throws SQLException{
        CallableStatement cStmt;
            cStmt=conn.prepareCall("{call getPersonDatas(?,?,?,?,?,?,?)}");
            cStmt.setInt(1, PersonID);
            cStmt.registerOutParameter("vnev", java.sql.Types.VARCHAR);
            cStmt.registerOutParameter("knev", java.sql.Types.VARCHAR);
            cStmt.registerOutParameter("adoszam", java.sql.Types.CHAR);
            cStmt.registerOutParameter("bankszamlaszam", java.sql.Types.VARCHAR);
            cStmt.registerOutParameter("nap20", java.sql.Types.INTEGER);
            cStmt.registerOutParameter("beosztas", java.sql.Types.VARCHAR);
            cStmt.execute();
            return new Person(PersonID,cStmt.getString("vnev") ,cStmt.getString("knev") ,cStmt.getString("adoszam"),
                    cStmt.getString("bankszamlaszam") ,cStmt.getString("beosztas") ,cStmt.getInt("nap20") 
            );
        
    }

}
