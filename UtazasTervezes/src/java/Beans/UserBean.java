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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import webformgen.Car;
import webformgen.DWNLDATA;
import webformgen.PDFDatas;
import webformgen.PDFGEN;
import webformgen.Person;
import webformgen.ProcedureManager;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@SessionScoped
@ManagedBean
public class UserBean implements Serializable {
    //Weboldalról bekér
    String neptun,password,honnan,hova,rendszam,Eloadas_postercim,egyebkeret; 
    double autopalyFT,autopalyaDevizva,ParkirozasDeviza;
    
    //Adatbázisból lekér
    Person szemely;
    Car kocsi;
    int PersonID=-1;
    PDFDatas SelectedPDFDatas;
    List<PDFDatas> pdfDatas;
    byte[] receivedPDF=null;
    int receivedPDFID;
    int isAdmin=0;
    
    @EJB
    private ProcedureManager SingletonDBMgr;
    
    public int getIsAdmin(){
        return isAdmin;
    }
    public void setIsAdmin(int value){
        isAdmin=value;
    }
    
    public double getAutopalyFT() {
        return autopalyFT;
    }

    public void setAutopalyFT(double autopalyFT) {
        this.autopalyFT = autopalyFT;
    }

    public double getAutopalyaDevizva() {
        return autopalyaDevizva;
    }

    public void setAutopalyaDevizva(double autopalyaDevizva) {
        this.autopalyaDevizva = autopalyaDevizva;
    }

    public double getParkirozasDeviza() {
        return ParkirozasDeviza;
    }

    public void setParkirozasDeviza(double ParkirozasDeviza) {
        this.ParkirozasDeviza = ParkirozasDeviza;
    }
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
        if(PersonID>=1){
            szemely=SingletonDBMgr.getPersonDatas(PersonID);
            return true;
        }
        return false;
    }
    
    public String getNev(){
        return szemely.getVnev()+szemely.getKnev();
    }
    
    public String getBeosztas(){
        return szemely.getBeosztas();
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
    public void SelectCar(){
        kocsi=SingletonDBMgr.getCarDatas(rendszam);
    }
    
    public double getNorma() throws IOException{
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
    public void genPDF() throws SQLException, IOException//PDF generálása,küldése adatbázisba
    {
        PDFGEN pdfgen=new PDFGEN();
        byte[] pdfBytes=pdfgen.genKalkulacio(
                getNev(), szemely.getBeosztas(), rendszam, kocsi.getHenger(), kocsi.getUzemanyag(), 
                //        amortizacio,uzemanyagar,utvonal hossza
                getNorma(), PersonID, PersonID, PersonID,
                //                                 valutaarfolyam
                honnan+" - "+hova, kocsi.getGyarto()+" "+kocsi.getTipus(), PersonID, autopalyFT, ParkirozasDeviza);
        SingletonDBMgr.InsertPDF(PersonID, hova, pdfgen.getOsszkoltseg(), "Szemelygepkocsi Kalkulacio", pdfBytes);
    }
    public void onRowSelect(SelectEvent event) throws IOException {
        receivedPDFID=((PDFDatas) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("PDF Selected", ((PDFDatas) event.getObject()).getHova());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("PDF Unselected", ((PDFDatas) event.getObject()).getHova());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void prepareDownload() throws SQLException{
        receivedPDF=SingletonDBMgr.downloadPDF(receivedPDFID);
    }
    public void download() throws IOException{
        String pdfFileName = "PDF_id"+receivedPDFID+".pdf";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();  
        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength(receivedPDF.length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + pdfFileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        ByteArrayInputStream bis = new ByteArrayInputStream(receivedPDF);
        OutputStream responseOutputStream = ec.getResponseOutputStream();
        int bytes;
        while ((bytes = bis.read()) != -1)
            responseOutputStream.write(bytes);
        fc.responseComplete();
    }
}
