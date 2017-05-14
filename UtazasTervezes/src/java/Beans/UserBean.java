package Beans;


import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
}
