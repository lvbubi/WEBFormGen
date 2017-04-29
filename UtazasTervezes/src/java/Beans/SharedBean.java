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
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@ManagedBean
@Stateless
public class SharedBean implements Serializable{
    PDFDatas SelectedPDFDatas;
    List<PDFDatas> pdfDatas;
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
    
    public String Login() throws SQLException{
        PersonID=SingletonDBMgr.getPersonID(neptun, password);
        System.out.println(PersonID);
        setIsAdmin();
        if(isAdmin==0){
            return "user";
        }else if(isAdmin==1){
            return "admin";
        }else if(isAdmin==2){
            return "titkar";
        }
        return "index";
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
        pdfDatas=SingletonDBMgr.getPDFDatas(PersonID);
    }
    
    public void AdminShowPDFS(int ellenorz) throws SQLException{
        pdfDatas = SingletonDBMgr.getPDFDatasAdmin(ellenorz);
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
}
