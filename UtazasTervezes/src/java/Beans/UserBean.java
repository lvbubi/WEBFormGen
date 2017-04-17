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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import webformgen.DWNLDATA;
import webformgen.ProcedureManager;

/**
 *
 * @author juhasz
 */
@Named(value = "userBean")
@SessionScoped
@ManagedBean
public class UserBean {
    String neptun,password;
    String honnan,hova;
    String rendszam,Eloadas_postercim,egyebkeret;

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
    int PersonID=-1;
    @EJB
    private ProcedureManager SingletonDBMgr;
    
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
    public boolean isLoggedIn(){
        return PersonID >= 1;
    }
    public String getPersonDatas(){
        webformgen.Person p = SingletonDBMgr.getPersonDatas(PersonID);
        return p.getVnev()+" "+p.getKnev();
    }
    public List<String> getRendszamok(){
        return SingletonDBMgr.getRendszamok(PersonID);
    }
    public List<String> getUtazasiKeret(){
        List<String> tmp=new ArrayList<>();
        tmp.add("TEMPUS");
        tmp.add("egyéb EU - szintű együttműködés");
        tmp.add("kormányközi együttműködés");
        tmp.add("intézményi együttműködés");
        tmp.add("alapítványi támogatás");
        tmp.add("meghívás");
        tmp.add("saját szervezés");
        tmp.add("egyéb:");
        return tmp;
    }
    public int getPrice(){
        /*try{
            DWNLDATA ddata=new DWNLDATA();
            return ddata.selectUzemanyag("uzemanyagtipus", "honap");
           
        }
        catch(IOException ex){
            return -1;
        }*/
        return 0;
    }
    
    public void SelectedRendszam(){
        System.out.println(rendszam);
        System.out.println("MIAFASZVAN");
    }
}
