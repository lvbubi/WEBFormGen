/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.PieChartModel;
import webformgen.PDFDatas;
import webformgen.ProcedureManager;

/**
 *
 * @author lvbubi
 */
@Named(value = "sharedBean")
@Stateless
public class SharedBean implements Serializable{
    PDFDatas SelectedPDFDatas;
    static List<PDFDatas> pdfDatas;
    static List<PDFDatas> deletedpdfDatas;
    static List<PDFDatas> acceptedpdfDatas;
    byte[] receivedPDF=null;
    int receivedPDFID;
    static int PersonID;
    String neptun,password;
    public int getPersonID() {
        return PersonID;
    }
    int isAdmin=0;
    
    
    
    
    public List<PDFDatas> getDeletedpdfDatas() {
        return deletedpdfDatas;
    }
    private int SzgkCalc_count(){
        int count=0;
        for (PDFDatas p:acceptedpdfDatas)
            if("Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        System.out.println("Darabszam: "+count);
        return count;
    }
    public void setDeletedpdfDatas(List<PDFDatas> deletedpdfDatas) {
        this.deletedpdfDatas = deletedpdfDatas;
    }

    public List<PDFDatas> getAcceptedpdfDatas() {
        return acceptedpdfDatas;
    }
    public PieChartModel getAdminDia() {
        
        PieChartModel pieModel = new PieChartModel();
        pieModel.set("Elfogadott PDF-ek", acceptedpdfDatas.size());
        pieModel.set("Törölt PDF-ek", deletedpdfDatas.size());
        pieModel.set("Ellenőrizetlen PDF-ek", pdfDatas.size());
        return pieModel;
    } 

    public void setAcceptedpdfDatas(List<PDFDatas> acceptedpdfDatas) {
        this.acceptedpdfDatas = acceptedpdfDatas;
    }
    public boolean isAdminrender() {
        if(isAdmin==1)
            return true;
        return false;
    }
    @EJB
    public ProcedureManager SingletonDBMgr;
    
    public boolean isLoggedIn() throws SQLException{
        return PersonID>=1;
    }
    
    public void Login() throws SQLException, IOException{
        PersonID=SingletonDBMgr.getPersonID(neptun, password);
        System.out.println(PersonID);
        setIsAdmin();
        if(isAdmin==0 && PersonID > 0){
            FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
        }else if(isAdmin==1){
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");
            AdminShowPDFS(1);
        }else if(isAdmin==2){
            FacesContext.getCurrentInstance().getExternalContext().redirect("titkar.xhtml");
        }
        else FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    } 
    public int getIsAdmin(){
        return isAdmin;
    }
    //talan msot mar jo
    public void setIsAdmin() throws SQLException{
        isAdmin=SingletonDBMgr.getJogosultsag(neptun, password);
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
    
    public void showPDFS() throws SQLException{
        pdfDatas=SingletonDBMgr.getPDFDatas(PersonID,0,"Szemelygepkocsi Kalkulacio");
        pdfDatas.addAll(SingletonDBMgr.getPDFDatas(PersonID,0,"Utvonal Terv"));
        acceptedpdfDatas=SingletonDBMgr.getPDFDatas(PersonID,1,"Szemelygepkocsi Kalkulacio");
        acceptedpdfDatas.addAll(SingletonDBMgr.getPDFDatas(PersonID,1,"Utvonal Terv"));
    }
    
    public void AdminShowPDFS(int ellenorz) throws SQLException{
        pdfDatas = SingletonDBMgr.getPDFDatasAdmin(0,"Szemelygepkocsi Kalkulacio");
        pdfDatas.addAll(SingletonDBMgr.getPDFDatasAdmin(0,"Utvonal Terv"));
        if(ellenorz==1){//ha az ellenorz 1 akkor admin használja ezt a függvényt
            acceptedpdfDatas = SingletonDBMgr.getPDFDatasAdmin(1,"Szemelygepkocsi Kalkulacio");
            acceptedpdfDatas.addAll(SingletonDBMgr.getPDFDatasAdmin(1,"Utvonal Terv"));
            deletedpdfDatas = SingletonDBMgr.getPDFDatasAdmin(3,"Szemelygepkocsi Kalkulacio");
            deletedpdfDatas.addAll(SingletonDBMgr.getPDFDatasAdmin(3,"Utvonal Terv"));
        }
        
        for(PDFDatas p: pdfDatas){
            System.out.println(p.getDokumentumTipusa());
        }
    }
    public void setEllenoriz() throws SQLException{
        SingletonDBMgr.setEllenorzott(receivedPDFID);
    }
    public void Torol() throws SQLException{
        SingletonDBMgr.DeletePDF(SelectedPDFDatas.getId());
    }
    
    public void prepareDownload() throws SQLException{
        System.out.println("ReceivedPDFID :"+receivedPDFID);
        System.out.println("Doktipus :"+SelectedPDFDatas.getDokumentumTipusa()+" SelectedPDFID :"+SelectedPDFDatas.getId());
        receivedPDF=SingletonDBMgr.downloadPDF(receivedPDFID,SelectedPDFDatas.getDokumentumTipusa());
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
    
    
    public void onRowSelect(SelectEvent event) throws IOException {
        receivedPDFID=((PDFDatas) event.getObject()).getId();
        SelectedPDFDatas=((PDFDatas)event.getObject());
        FacesMessage msg = new FacesMessage("PDF Selected", SelectedPDFDatas.getDokumentumTipusa());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("PDF Unselected", ((PDFDatas) event.getObject()).getHova());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void redirectAccepted() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("showAcceptedPDFS.xhtml");
    }
    public void redirectDeleted() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("showDeletedPDFS.xhtml");
    }
    public void redirectPDFS() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("showPDFS.xhtml");
    }
    
    
    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("utaz")) {
            return "date";
        }
        if(SzgkCalc_count() == 0) {
            return "wattack";
        }
        else {
            return event.getNewStep();
        }
    }
}
