package Beans;


import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import webformgen.Car;
import webformgen.DWNLDATA;
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
    boolean skip;
    String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    public void utazasiTerv() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("UtazasiTerv.xhtml");
    }
    
    public void szgkKalk() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("SzgkKalk.xhtml");
    }
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    
    
}
