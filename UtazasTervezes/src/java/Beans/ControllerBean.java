/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import webformgen.ProcedureManager;

/**
 *
 * @author lvbubi
 */
@Named
@RequestScoped
public class ControllerBean {


    @Inject
    private SharedBean sharedbean;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private ProcedureManager SingletonDBMgr;
    
    @EJB
    private UserBean userBean;
    
    @PostConstruct
    void initIt(){
        //PersonID=sharedbean.PersonID;
        System.out.println("ELINDUUUULT");
        System.out.println(sharedbean.PersonID);
        userBean.setPersonID(sharedbean.PersonID);
        userBean.szemely=SingletonDBMgr.getPersonDatas(userBean.PersonID);
    }
    public void Start(){
        System.out.println("START");
    }
}
