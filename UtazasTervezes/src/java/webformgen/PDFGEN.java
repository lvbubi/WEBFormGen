/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webformgen;

import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Balint
 */
public class PDFGEN {
    
    private String nev;
    private String beosztas;
    private String rendszam;
    private double henger;
    private String uzemanyag;
    private double norma;
    private int amortizacio;
    private int ar;
    private double utvonal;
    private String terv;
    private String tipus;
    private double valuta;
    private int palya;
    private int parkolas;

    public PDFGEN(String nev, String beosztas, String rendszam, double henger, String uzemanyag, double norma, int amortizacio, int ar, double utvonal, String terv, String tipus, double valuta, int palya, int parkolas) {
        this.nev = nev;
        this.beosztas = beosztas;
        this.rendszam = rendszam;
        this.henger = henger;
        this.uzemanyag = uzemanyag;
        this.norma = norma;
        this.amortizacio = amortizacio;
        this.ar = ar;
        this.utvonal = utvonal;
        this.terv = terv;
        this.tipus = tipus;
        this.valuta=valuta;
        this.parkolas=parkolas;
        this.palya=palya;
    }
    
  
    
    
    
     public void genKalkulacio()
    {
         Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream("Kalkuláció.pdf"));
            document.open();
            
            Paragraph p1 = new Paragraph("Kalkuláció*",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD,BaseColor.BLACK));              
                p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("Saját szgk-val történo külföldi utazáshoz");
                 p2.setAlignment(Element.ALIGN_CENTER);             
            Paragraph p3 = new Paragraph("Az utazo neve,beosztasa: " + getNev() + " " + getBeosztas(),FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                 p3.setLeading(30);
            Paragraph p4 = new Paragraph("Tervezett utvonal: " + getTerv(),FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
            Paragraph p5 = new Paragraph("A gépkocsi adatai: ",FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                  Paragraph p6 = new Paragraph("          Rendszam: "+ getRendszam());     
                  Paragraph p7 = new Paragraph("          Tipus: " + getTipus());  
                  Paragraph p8 = new Paragraph("          Hengerurtatralom: " + getHenger());  
                  Paragraph p9 = new Paragraph("          Üzemanyag fajta: " + getUzemanyag());
                  Paragraph p10 = new Paragraph("          Üzemanyag norma 1/100km(A): " + getNorma());
                  Paragraph p11 = new Paragraph("          Amortizaciós költseg Ft/km(B): " + getAmortizacio());
                  Paragraph p12 = new Paragraph("          Üzemanyag egységára Ft-ban(C): " + getAr());
                  Paragraph p13 = new Paragraph("          A teljes útvonal hossza k (D): " + getUtvonal()) ;
                  
            
            Chunk p14 = new Chunk("Atalany szerinti üzemanyag es amortizació: ");
            p14.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            Paragraph p15 = new Paragraph("Üzemanyag költség (D/100xAxC): " + (getUtvonal()/100*getNorma()*getAr()));
                p15.setLeading(30);
            Paragraph p16 = new Paragraph("Amortizációs költseg (BxD):"+ (getAmortizacio()*getUtvonal()));
                p16.setLeading(30);
            Paragraph p17 = new Paragraph("__________________________________________________________________________");      
            
            Chunk p18 = new Chunk("Esetleges számlával igazolt további költségek:");
            p18.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            
            Paragraph p19 = new Paragraph("Autópályadij Ft-ban: ");
            Paragraph p20 = new Paragraph("Autopalyadij valutában* és Ft-ban: ");
            Paragraph p21 = new Paragraph("Parkirozási dij valutában* és forintban: ");
            Paragraph p22 = new Paragraph("Valuta árfolyam**: " + getValuta());
            
            Paragraph p23 = new Paragraph("Összes költség Ft-ban: " + 
                    (getUtvonal()/100*getNorma()*getAr()) + (getAmortizacio()*getUtvonal()) + getParkolas() + getPalya()
                    ,FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                
            Chunk p24 = new Chunk("Megjegyzesek: ");
            p24.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location   
           
            Paragraph p25 = new Paragraph
            ("* Bizonylat csatolando\n"
             + "* A valuta váltás bizonylatán szereplő árfolyam, ennek hiányáan az utazást megelőző hónap 15-dikei MNB deviza középárfolyam.\n"
             + "Utazási tervhez a kalkulációs lap kitöltése szükséges, elszámoláskor azonban kiküldetési rendelvényt kell kitölteni. "
            );
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
           
            
            Paragraph p26 = new Paragraph("Veszrpém, "+dateFormat.format(date));
            
            Paragraph p27 = new Paragraph("Aláírások: ");
            
             Paragraph p28 = new Paragraph("Utazo: ___________   Logisztikai csoport: __________    Engedélyező Hivatal: ____________ ");
             
           
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p5);
            document.add(p6);
            document.add(p7);
            document.add(p8);
            document.add(p9);
            document.add(p10);
            document.add(p11);
            document.add(p12);
            document.add(p13);
            document.add(new Phrase("\n")); 
            document.add(p14);
            document.add(new Phrase("\n")); 
            document.add(p15);
            document.add(p16);
            document.add(p17);
            document.add(new Phrase("\n"));
            document.add(p18);
            document.add(new Phrase("\n"));
            document.add(p19);
            document.add(p20);
            document.add(p21);
            document.add(p22);
            document.add(p17);
            document.add(new Phrase("\n"));
            document.add(p23);
            document.add(new Phrase("\n"));           
            document.add(p24);
            document.add(p25);
            document.add(new Phrase("\n"));
            document.add(p26);
            document.add(new Phrase("\n"));
            document.add(p27);
            document.add(p28);
         
          
         
          
        }
        catch(Exception e){
            System.out.println(e);
        }
        document.close();
        
     
    }

    public String getNev() {
        return nev;
    }

    public String getBeosztas() {
        return beosztas;
    }

    public String getRendszam() {
        return rendszam;
    }

    public double getHenger() {
        return henger;
    }

    public String getUzemanyag() {
        return uzemanyag;
    }

    public double getNorma() {
        return norma;
    }

    public int getAmortizacio() {
        return amortizacio;
    }

    public int getAr() {
        return ar;
    }

    public double getUtvonal() {
        return utvonal;
    }

    public String getTerv() {
        return terv;
    }

    public String getTipus() {
        return tipus;
    }

    public double getValuta() {
        return valuta;
    }

    public int getPalya() {
        return palya;
    }

    public int getParkolas() {
        return parkolas;
    }
     
     
     
    
}
