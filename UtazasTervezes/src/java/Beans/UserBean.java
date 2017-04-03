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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;
import webformgen.ProcedureManager;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@RequestScoped
@ManagedBean
public class UserBean {
    
    int PersonID=-1;
    @EJB
    private ProcedureManager StatefulDBMgr;
    

    public String setLoginID(String neptun,String password){
        PersonID=StatefulDBMgr.getPersonID(neptun, password);
        if(PersonID!=0)
            return "SIKERES BELÉPÉS";
        return "SIKERTELEN BELÉPÉS";
    }
    
    public String getPersonDatas(){
        webformgen.Person p = StatefulDBMgr.getPersonDatas(PersonID);
        return p.getVnev()+" "+p.getKnev();
    }
}
