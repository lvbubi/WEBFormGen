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

    @EJB
    private SharedBean sharedBean;
    //Weboldalról bekér
    String honnan,hova,rendszam,Eloadas_postercim,egyebkeret; 
    double autopalyFT,autopalyaDevizva,ParkirozasDeviza;
    boolean skip;
    //Adatbázisból lekér
    Person szemely;
    Car kocsi;
    int PersonID=-1;
    

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int PersonID) {
        this.PersonID=PersonID;
        
    }
    String distance;
    
    String currency;

    @EJB
    private ProcedureManager SingletonDBMgr;
 
    @PostConstruct
    void initIt(){
        PersonID=sharedBean.PersonID;
        System.out.println(PersonID);
        try {
            szemely=SingletonDBMgr.getPersonDatas(PersonID);
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        System.out.println(currency);
        this.currency = currency;
    }
    public void setDistance(String value)
    {
        distance=value;
    }
    
    public String getDistance(){
        return distance;
    }
    
    public double getAutopalyFT() {
        return autopalyFT;
    }

    public void setAutopalyFT(double autopalyFT) {
        this.autopalyFT = autopalyFT;
    }

    public double getAutopalyaDevizva() {
        return autopalyaDevizva;
    }

    public void setAutopalyaDevizva(double autopalyaDevizva) {
        this.autopalyaDevizva = autopalyaDevizva;
    }

    public double getParkirozasDeviza() {
        return ParkirozasDeviza;
    }

    public void setParkirozasDeviza(double ParkirozasDeviza) {
        this.ParkirozasDeviza = ParkirozasDeviza;
    }

    public String getEgyebkeret() {
        return egyebkeret;
    }

    public void setEgyebkeret(String egyebkeret) {
        this.egyebkeret = egyebkeret;
    }

    public String getRendszam() {
        return rendszam;
    }

    public void setRendszam(String rendszam) throws SQLException {
        this.rendszam = rendszam;
        kocsi=SingletonDBMgr.getCarDatas(rendszam);
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
        System.out.println(hova);
        System.out.println(sharedBean.neptun);
        this.hova = hova;
    }

    
    
    public String getNev(){
        return szemely.getVnev()+" "+szemely.getKnev();
    }
    
    public String getBeosztas(){
        return szemely.getBeosztas();
    }
    
    public List<String> getRendszamok() throws SQLException{
        return SingletonDBMgr.getRendszamok(PersonID);
    }
    public List<String> getUtazasiKeret(){
        List<String> tmp = Arrays.asList(
                "TEMPUS", "egyéb EU - szintű együttműködés", "kormányközi együttműködés","intézményi együttműködés",
                "alapítványi támogatás","meghívás","saját szervezés");
        return tmp;
    }

    public List<String> getValutaTypesKeret()
    {
        List<String> tmp = Arrays.asList(
                "ATS","AUD","AUP","BEF","BGL","BGN","BRL","CAD","CHF",
                "CNY","CSD","CSK2","CYN","CZK","DDM","DEM","DKK","EEK",
                "EGP","ESP","EUR","FIM","FRF","GBP","GHP","GRD","HKD",
                "HRK","IDR","IEP","ILS","INR","ISK","ITL","JPY","KPW",
                "KRW","KWD","LBP","LTL","LUF","LVL","MNT","MXN","MYR",
                "NLG","NOK","NZD","OAL","OBL","OFR","ORB","PHP","PKR",
                "PLN","PTE","ROL","RON","RSD","RUB","SDP","SEK","SGD",
                "SIT","SKK","SUR","THB","TRY","UAH","USD","VND","XEU",
                "XTR","YUD","ZAR");
        return tmp;
    }
    
    public void SelectCar() throws SQLException{
        kocsi=SingletonDBMgr.getCarDatas(rendszam);
        
    }
    
    public double getNorma() throws IOException{
        DWNLDATA ddata=new DWNLDATA();
        if("diesel".equals(kocsi.getUzemanyag()))
            return ddata.selectNorma("gazolaj", kocsi.getHenger());
        else
            return ddata.selectNorma("benzin", kocsi.getHenger());
    }
    public int getUzemanyag() throws IOException{
        DWNLDATA ddata=new DWNLDATA();
        if(kocsi.getTipus().equals("benzin"))
            return ddata.selectUzemanyag("ESZ-95");
        return ddata.selectUzemanyag("Gázolaj");
    }
    
    public double getArfolyam(String valuta) throws IOException
    {
        DWNLDATA ddata=new DWNLDATA();
        return ddata.selectArfolyam(valuta);
    }

    
    public void genPDF() throws SQLException, IOException//PDF generálása,küldése adatbázisba
    {
        System.out.println(distance);
            PDFGEN pdfgen=new PDFGEN();
            double arfolyam=0;
            //arfolyam=getArfolyam(currency);
            byte[] pdfBytes=pdfgen.genKalkulacio(
                getNev(), szemely.getBeosztas(), rendszam, kocsi.getHenger(), kocsi.getUzemanyag(), 
                //        amortizacio
                getNorma(), PersonID, getUzemanyag(), Integer.parseInt(distance),
                honnan+" - "+hova, kocsi.getGyarto()+" "+kocsi.getTipus(), arfolyam, autopalyFT, ParkirozasDeviza
            );
            SingletonDBMgr.InsertPDF(PersonID, hova, pdfgen.getOsszkoltseg(), "Szemelygepkocsi Kalkulacio", pdfBytes);
            System.out.println("Generálva, adatbázisra elküldve");
        
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
