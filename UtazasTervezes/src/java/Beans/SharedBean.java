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
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
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
    public List<PDFDatas> getDeletedpdfDatas() {
        return deletedpdfDatas;
    }

    public void setDeletedpdfDatas(List<PDFDatas> deletedpdfDatas) {
        this.deletedpdfDatas = deletedpdfDatas;
    }

    public List<PDFDatas> getAcceptedpdfDatas() {
        return acceptedpdfDatas;
    }

    public void setAcceptedpdfDatas(List<PDFDatas> acceptedpdfDatas) {
        this.acceptedpdfDatas = acceptedpdfDatas;
    }
    
    byte[] receivedPDF=null;
    int receivedPDFID;
    static int PersonID;
    String neptun,password;
    public int getPersonID() {
        return PersonID;
    }
    int isAdmin=0;
    

    @EJB
    public ProcedureManager SingletonDBMgr;
    
    public boolean isLoggedIn() throws SQLException{
        return PersonID>=1;
    }
    
    public void Login() throws SQLException, IOException{
        PersonID=SingletonDBMgr.getPersonID(neptun, password);
        System.out.println(PersonID);
        setIsAdmin();
        if(isAdmin==0){
            FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");return;
        }else if(isAdmin==1){
            FacesContext.getCurrentInstance().getExternalContext().redirect("admin.xhtml");return;
        }else if(isAdmin==2){
            FacesContext.getCurrentInstance().getExternalContext().redirect("titkar.xhtml");
            return;
        }
        //FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
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
        pdfDatas=SingletonDBMgr.getPDFDatas(PersonID,0);
        acceptedpdfDatas=SingletonDBMgr.getPDFDatas(PersonID,1);
    }
    
    public void AdminShowPDFS(int ellenorz) throws SQLException{
        pdfDatas = SingletonDBMgr.getPDFDatasAdmin(0);
        if(ellenorz==1){//ha az ellenorz 1 akkor admin használja ezt a függvényt
            acceptedpdfDatas=SingletonDBMgr.getPDFDatasAdmin(1);
            deletedpdfDatas=SingletonDBMgr.getPDFDatasAdmin(3);
        }
    }
    public void setEllenoriz() throws SQLException{
        SingletonDBMgr.setEllenorzott(receivedPDFID);
    }
    public void Torol() throws SQLException{
        SingletonDBMgr.DeletePDF(SelectedPDFDatas.getId());
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
    
    
    public void onRowSelect(SelectEvent event) throws IOException {
        receivedPDFID=((PDFDatas) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("PDF Selected", ((PDFDatas) event.getObject()).getHova());
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
}
