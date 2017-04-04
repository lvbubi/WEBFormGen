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

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import webformgen.ProcedureManager;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@RequestScoped
@ManagedBean
public class UserBean {
    String neptun,password;

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
    int PersonID=-1;
    @EJB
    private ProcedureManager SingletonDBMgr;
    

    public String Login(){
        PersonID=SingletonDBMgr.getPersonID(neptun, password);
        System.out.println("PERSZONID: "+PersonID);
        if(PersonID!=0)
            return "SIKERES BELÉPÉS";
        return "SIKERTELEN BELÉPÉS";
    }
    
    public String getPersonDatas(){
        webformgen.Person p = SingletonDBMgr.getPersonDatas(PersonID);
        return p.getVnev()+" "+p.getKnev();
    }
    public boolean isLoggedIn(){
        if (PersonID == 0)
            return false;
        return true;
    }
}
