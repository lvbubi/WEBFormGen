/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import webformgen.PDFDatas;
import webformgen.PDFGEN;
import webformgen.Person;
import webformgen.ProcedureManager;

/**
 *
 * @author lvbubi
 */
@Named(value = "utazasiTerv")
@Stateless
public class UtazasiTerv {
    @EJB
    private ProcedureManager SingletonDBMgr;
    
    
    
    List<PDFDatas> acceptedSZGK=new ArrayList<>();
    PDFDatas selectedPDFDatas;
    String currency;
    
    public List<PDFDatas> getAcceptedSZGK() {
        return acceptedSZGK;
    }

    public void setAcceptedSZGK(List<PDFDatas> acceptedSZGK) {
        this.acceptedSZGK = acceptedSZGK;
    }

    public PDFDatas getSelectedPDFDatas() {
        return selectedPDFDatas;
    }

    public void setSelectedPDFDatas(PDFDatas selectedPDFDatas) {
        this.selectedPDFDatas = selectedPDFDatas;
    }
    Person person;
    int PersonID=-1;
    Date mettol,meddig;
    String Eloadas_postercim,egyebkeret,utazas_celja;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    @PostConstruct
    void initIt(){
        PersonID=SharedBean.PersonID;
        try {
            person=SingletonDBMgr.getPersonDatas(PersonID);
        } catch (SQLException ex) {
            Logger.getLogger(UtazasiTerv.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(PDFDatas p:SharedBean.acceptedpdfDatas)
            if("Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                acceptedSZGK.add(p);
        
    }
    
   //genUtazasiTerv(String nev , String adoszam, String beosztas, String  munkahely, String e_cim, String elfogadva, String program_tipus, String hely, String idotartam, 
    //String valuta, int napidij , int szallasdij, double arfolyam, int nap_db, String fedezet, int egyeb, String bankszamla, int eloleg,int deviza,int osztondij,
    //int honap_db, String utazasi_mod, int felhasznalt_napok,String velemeny)
  
   /*
    public void genPDF() throws SQLException, IOException{
        System.out.println("ElfogadottSZGK: "+acceptedSZGK.size());
        PDFGEN pdfgen=new PDFGEN();
        byte[] pdfBytes=pdfgen.genUtazasiTerv(person.getVnev()+" "+person.getKnev(), person.getAdoszam(), person.getBeosztas(), "Pannon Egyetem", egyebkeret, egyebkeret,
                utazas_celja, egyebkeret, egyebkeret, egyebkeret, PersonID, PersonID, PersonID, PersonID, egyebkeret,
                PersonID, utazas_celja, PersonID, PersonID, PersonID, PersonID, utazas_celja, PersonID, egyebkeret);
        
        SingletonDBMgr.InsertUtvonal(selectedPDFDatas.getId(), pdfBytes);
        System.out.println("PDF Generálása");
        FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
    }
    */
    public String getUtazas_celja() {
        return utazas_celja;
    }

    public void setUtazas_celja(String utazas_celja) {
        this.utazas_celja = utazas_celja;
    }
    public List<String> getValutaTypesKeret()
    {
        List<String> tmp = Arrays.asList(
                "ATS","AUD","AUP","BEF","BGL","BGN","BRL","CAD","CHF",
                "CNY","CSD","CSK2","CYN","CZK","DDM","DEM","DKK","EEK",
                "EGP","ESP","EUR","FIM","FRF","GBP","GHP","GRD","HKD",
                "HRK","IDR","IEP","ILS","INR","ISK","ITL","JPY","KPW",
                "KRW","KWD","LBP","LTL","LUF","LVL","MNT","MXN","MYR",
                "NLG","NOK","NZD","OAL","OBL","OFR","ORB","PHP","PKR",
                "PLN","PTE","ROL","RON","RSD","RUB","SDP","SEK","SGD",
                "SIT","SKK","SUR","THB","TRY","UAH","USD","VND","XEU",
                "XTR","YUD","ZAR");
        return tmp;
    }
        public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        System.out.println(currency);
        this.currency = currency;
    }
    public String getEgyebkeret() {
        return egyebkeret;
    }

    public void setEgyebkeret(String egyebkeret) {
        this.egyebkeret = egyebkeret;
    }

    public Date getMettol() {
        return mettol;
    }

    public void setMettol(Date mettol) {
        this.mettol = mettol;
    }

    public Date getMeddig() {
        return meddig;
    }

    public void setMeddig(Date meddig) {
        this.meddig = meddig;
    }
    
    public String getEloadas_postercim() {
        return Eloadas_postercim;
    }

    public void setEloadas_postercim(String Eloadas_postercim) {
        this.Eloadas_postercim = Eloadas_postercim;
    }
    
        public List<String> getUtazasiKeret(){
        List<String> tmp = Arrays.asList(
                "TEMPUS", "egyéb EU - szintű együttműködés", "kormányközi együttműködés","intézményi együttműködés",
                "alapítványi támogatás","meghívás","saját szervezés");
        return tmp;
    }
        
    public void vissza() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
    }

}
