package Beans;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.PieChartModel;
import webformgen.PDFDatas;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@SessionScoped
@ManagedBean
public class UserBean implements Serializable {

    public void utazasiTerv() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("UtazasiTerv.xhtml");
    }
    
    public void szgkKalk() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("SzgkKalk.xhtml");
    }
    
    
    public PieChartModel getUserDiaSZGK() {
        PieChartModel pieModel = new PieChartModel();
        int count=0;
        for( PDFDatas p : SharedBean.acceptedpdfDatas)
            if("Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        
        pieModel.set("Elfogadott Szemelygepkocsi Kalkulaciok", count);
        count=0;
        for( PDFDatas p : SharedBean.pdfDatas)
            if("Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        pieModel.set("Ellenőrizetlen Szemelygepkocsi Kalkulaciok", count);
        return pieModel;
    } 
    
    public PieChartModel getUserDiaUTV() {
        PieChartModel pieModel = new PieChartModel();
        int count=0;
        for( PDFDatas p : SharedBean.acceptedpdfDatas)
            if(!"Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        
        pieModel.set("Elfogadott Utvonal Tervek", count);
        count=0;
        for( PDFDatas p : SharedBean.pdfDatas)
            if(!"Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        pieModel.set("Ellenőrizetlen Utvonal Tervek", count);
        
        count=0;
        for( PDFDatas p : SharedBean.completepdfDatas)
            if(!"Szemelygepkocsi Kalkulacio".equals(p.getDokumentumTipusa()))
                count++;
        pieModel.set("Végleges Leadott Utvonal Tervek", count);
        return pieModel;
    } 
}
