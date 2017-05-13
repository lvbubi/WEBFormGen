/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author lvbubi
 */
@Named(value = "utazasiTerv")
@Stateless
public class UtazasiTerv {
    Date mettol,meddig;
    String Eloadas_postercim,egyebkeret,utazas_celja;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public String getUtazas_celja() {
        return utazas_celja;
    }

    public void setUtazas_celja(String utazas_celja) {
        this.utazas_celja = utazas_celja;
    }

    public String getEgyebkeret() {
        return egyebkeret;
    }

    public void setEgyebkeret(String egyebkeret) {
        this.egyebkeret = egyebkeret;
    }

    public Date getMettol() {
        return mettol;
    }

    public void setMettol(Date mettol) {
        this.mettol = mettol;
    }

    public Date getMeddig() {
        return meddig;
    }

    public void setMeddig(Date meddig) {
        this.meddig = meddig;
    }
    
    public String getEloadas_postercim() {
        return Eloadas_postercim;
    }

    public void setEloadas_postercim(String Eloadas_postercim) {
        this.Eloadas_postercim = Eloadas_postercim;
    }
    
        public List<String> getUtazasiKeret(){
        List<String> tmp = Arrays.asList(
                "TEMPUS", "egyéb EU - szintű együttműködés", "kormányközi együttműködés","intézményi együttműködés",
                "alapítványi támogatás","meghívás","saját szervezés");
        return tmp;
    }
}
