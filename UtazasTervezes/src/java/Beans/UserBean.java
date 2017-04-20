/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import webformgen.Car;
import webformgen.DWNLDATA;
import webformgen.PDFDatas;
import webformgen.PDFGEN;
import webformgen.ProcedureManager;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@SessionScoped
@ManagedBean
public class UserBean implements Serializable {
    String neptun,password;
    String honnan,hova;
    String rendszam;
    String Eloadas_postercim,egyebkeret;
    String SelectedPDFKey;
    PDFDatas SelectedPDFDatas;
    List<PDFDatas> pdfDatas;
    
    PDFGEN pdfgen=new PDFGEN();
    
    int PersonID=-1;
    @EJB
    private ProcedureManager SingletonDBMgr;

    public List<PDFDatas> getPdfDatas() {
        return pdfDatas;
    }

    public void setPdfDatas(List<PDFDatas> pdfDatas) {
        this.pdfDatas = pdfDatas;
    }

    public PDFDatas getSelectedPDFDatas() {
        return SelectedPDFDatas;
    }

    public void setSelectedPDFDatas(PDFDatas SelectedPDFDatas) {
        this.SelectedPDFDatas = SelectedPDFDatas;
    }

    public String getSelectedPDFKey() {
        return SelectedPDFKey;
    }
    public String getEgyebkeret() {
        return egyebkeret;
    }

    public void setEgyebkeret(String egyebkeret) {
        this.egyebkeret = egyebkeret;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) {
        this.rendszam = rendszam;
    }
    
    public String getEloadas_postercim() {
        return Eloadas_postercim;
    }

    public void setEloadas_postercim(String Eloadas_postercim) {
        this.Eloadas_postercim = Eloadas_postercim;
    }
    public String getHonnan() {
        return honnan;
    }

    public void setHonnan(String honnan) {
        this.honnan = honnan;
    }

    public String getHova() {
        return hova;
    }

    public void setHova(String hova) {
        this.hova = hova;
    }

    
    public String getNeptun() {
        return neptun;
    }
    public void setNeptun(String neptun) {
        this.neptun = neptun;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void Login(){
        PersonID=SingletonDBMgr.getPersonID(neptun, password);
    }
    public boolean isLoggedIn() throws SQLException{
        return PersonID>=1;
    }
    public String getPersonDatas(){
        webformgen.Person p = SingletonDBMgr.getPersonDatas(PersonID);
        return p.getVnev()+" "+p.getKnev();
    }
    public List<String> getRendszamok(){
        return SingletonDBMgr.getRendszamok(PersonID);
    }
    public List<String> getUtazasiKeret(){
        List<String> tmp = Arrays.asList(
                "TEMPUS", "egyéb EU - szintű együttműködés", "kormányközi együttműködés","intézményi együttműködés",
                "alapítványi támogatás","meghívás","saját szervezés");
        return tmp;
    }
    public double getNorma() throws IOException{
        Car kocsi=SingletonDBMgr.getCarDatas(rendszam);
        DWNLDATA ddata=new DWNLDATA();
        if("diesel".equals(kocsi.getUzemanyag()))
            return ddata.selectNorma("gazolaj", kocsi.getHenger());
        else
            return ddata.selectNorma("benzin", kocsi.getHenger());
    }
    public String getUzemanyag() throws IOException{
        DWNLDATA ddata=new DWNLDATA();
        return "Hogyiskellkinéznieahónapnak? Egy samplet írjmár pls";
        //return ddata.selectUzemanyag("Gázolaj", "December");
    }
    public void showPDFS() throws SQLException{
        pdfDatas=SingletonDBMgr.getPDFDatas(PersonID);
    }
    public void genPDF() throws SQLException//PDF generálása,küldése adatbázisba
    {
        byte[] pdfBytes=pdfgen.genKalkulacio(hova, rendszam, rendszam, PersonID, honnan, PersonID, PersonID, PersonID, PersonID, hova, neptun, PersonID, PersonID, PersonID);
        SingletonDBMgr.InsertPDF(PersonID, hova, pdfgen.getOsszkoltseg(), "Utazasi Terv", pdfBytes);
    }
    
    public List<String> getPDFKEYS() throws SQLException{
        return SingletonDBMgr.getPDFKeys(PersonID);
    }
    
    public void Faszom(){
        System.out.println( honnan+hova+rendszam);
    }
    
    public void onRowSelect(SelectEvent event) throws IOException {
        FacesMessage msg = new FacesMessage("Car Selected", ((PDFDatas) event.getObject()).getHova());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((PDFDatas) event.getObject()).getHova());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
