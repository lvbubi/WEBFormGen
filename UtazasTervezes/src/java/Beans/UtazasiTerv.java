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
    int PersonID=-1;
    List<PDFDatas> acceptedSZGK=new ArrayList<>();
    PDFDatas selectedPDFDatas;

    
    
    
    String currency;
    Person person;
    Date mettol,meddig;
    String eloadas_cime,elfogadva,program_tipus,
            program_helye,idotartam;

    //ideiglenes kulfoldi itazasnal
    String nap,osszeg_nap,fedezet,
            ejjszaka,osszeg_ejjszaka,
            reszveteli_dij,
            egyeb_espedig,egyeb_osszeg,
            felvEloleg,
            devizabankszamla,deviszaosszeg;

    
        //tartos kulfoldi utazasnal
    String honapra,osztondij,
           egyeb2,egyeb2_osszeg,uzatasimod;
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public String getOsszeg_nap() {
        return osszeg_nap;
    }

    public void setOsszeg_nap(String osszeg_nap) {
        this.osszeg_nap = osszeg_nap;
    }

    public String getFedezet() {
        return fedezet;
    }

    public void setFedezet(String fedezet) {
        this.fedezet = fedezet;
    }

    public String getEjjszaka() {
        return ejjszaka;
    }

    public void setEjjszaka(String ejjszaka) {
        this.ejjszaka = ejjszaka;
    }

    public String getOsszeg_ejjszaka() {
        return osszeg_ejjszaka;
    }

    public void setOsszeg_ejjszaka(String osszeg_ejjszaka) {
        this.osszeg_ejjszaka = osszeg_ejjszaka;
    }

    public String getReszveteli_dij() {
        return reszveteli_dij;
    }

    public void setReszveteli_dij(String reszveteli_dij) {
        this.reszveteli_dij = reszveteli_dij;
    }

    public String getEgyeb_espedig() {
        return egyeb_espedig;
    }

    public void setEgyeb_espedig(String egyeb_espedig) {
        this.egyeb_espedig = egyeb_espedig;
    }

    public String getEgyeb_osszeg() {
        return egyeb_osszeg;
    }

    public void setEgyeb_osszeg(String egyeb_osszeg) {
        this.egyeb_osszeg = egyeb_osszeg;
    }

    public String getDevizabankszamla() {
        return devizabankszamla;
    }

    public void setDevizabankszamla(String devizabankszamla) {
        this.devizabankszamla = devizabankszamla;
    }

    public String getDeviszaosszeg() {
        return deviszaosszeg;
    }

    public void setDeviszaosszeg(String deviszaosszeg) {
        this.deviszaosszeg = deviszaosszeg;
    }
    public String getEloadas_cime() {
        return eloadas_cime;
    }

    public void setEloadas_cime(String eloadas_cime) {
        this.eloadas_cime = eloadas_cime;
    }

    public String getElfogadva() {
        return elfogadva;
    }

    public void setElfogadva(String elfogadva) {
        this.elfogadva = elfogadva;
    }

    public String getProgram_tipus() {
        return program_tipus;
    }

    public void setProgram_tipus(String program_tipus) {
        this.program_tipus = program_tipus;
    }

    public String getProgram_helye() {
        return program_helye;
    }

    public void setProgram_helye(String program_helye) {
        this.program_helye = program_helye;
    }

    public String getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(String idotartam) {
        this.idotartam = idotartam;
    }
    
    public String getHonapra() {
        return honapra;
    }

    public void setHonapra(String honapra) {
        this.honapra = honapra;
    }

    public String getOsztondij() {
        return osztondij;
    }

    public void setOsztondij(String osztondij) {
        this.osztondij = osztondij;
    }

    public String getEgyeb2() {
        return egyeb2;
    }

    public void setEgyeb2(String egyeb2) {
        this.egyeb2 = egyeb2;
    }

    public String getEgyeb2_osszeg() {
        return egyeb2_osszeg;
    }

    public void setEgyeb2_osszeg(String egyeb2_osszeg) {
        this.egyeb2_osszeg = egyeb2_osszeg;
    }

    

    public String getUzatasimod() {
        return uzatasimod;
    }

    public void setUzatasimod(String uzatasimod) {
        this.uzatasimod = uzatasimod;
    }
    public String getFelvEloleg() {
        return felvEloleg;
    }

    public void setFelvEloleg(String felvEloleg) {
        this.felvEloleg = felvEloleg;
    }
    
    
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
    
       /* public byte[] genUtazasiTerv(Person szemely,
            String  munkahely, String e_cim, String elfogadva, String program_tipus,
            String hely, String idotartam, String valuta, int napidij , int szallasdij,
            double arfolyam, int nap_db, String fedezet, int egyeb,int eloleg,int deviza,
            int osztondij,int honap_db, String utazasi_mod,int felhasznalt_napok,String velemeny)*/
        //tartos kulfoldi utazasnal
    /*String honapra,osztondij,
           egyeb2,egyeb2_osszeg,uzatasimod;*/
    public void genPDF() throws SQLException, IOException{
        System.out.println("ElfogadottSZGK: "+acceptedSZGK.size());
        PDFGEN pdfgen=new PDFGEN();
        
        double arfolyam=-1;
        //felhasznalt_napok,String velemeny
        byte[] pdfBytes=pdfgen.genUtazasiTerv(person, person.getBeosztas(), eloadas_cime, elfogadva, program_tipus, program_helye, idotartam, currency,
                Integer.parseInt(osszeg_nap), Integer.parseInt(osszeg_ejjszaka), arfolyam, Integer.parseInt(nap),
                fedezet, Integer.parseInt(egyeb_osszeg), Integer.parseInt(felvEloleg), Integer.parseInt(deviszaosszeg), Integer.parseInt(osztondij), 
                Integer.parseInt(honapra), uzatasimod, 0, "nincs vélemény");
        SingletonDBMgr.InsertUtvonal(selectedPDFDatas.getId(), pdfBytes);
        System.out.println("PDF Generálása");
        FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
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
