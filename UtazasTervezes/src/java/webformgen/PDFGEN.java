
package webformgen;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PDFGEN {
     private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
     private double Osszkoltseg=0;
     
     
    public double getOsszkoltseg() {
        return Osszkoltseg;
    }

    public void setOsszkoltseg(double Osszkoltseg) {
        this.Osszkoltseg = Osszkoltseg;
    }
     public byte[] genKalkulacio(String nev, String beosztas, String rendszam, double henger, String uzemanyag, double norma, int amortizacio, int ar, double utvonal, String terv, String tipus, double valuta, double palya, double parkolas)
    {
         Document document = new Document();
        try{
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            amortizacio = 9;
            Paragraph p1 = new Paragraph("Kalkuláció*",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD,BaseColor.BLACK));              
                p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("Saját szgk-val történo külföldi utazáshoz");
                 p2.setAlignment(Element.ALIGN_CENTER);             
            Paragraph p3 = new Paragraph("Az utazo neve,beosztasa: " +nev + " " + beosztas,FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                 p3.setLeading(30);
            Paragraph p4 = new Paragraph("Tervezett utvonal: " + terv,FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
            Paragraph p5 = new Paragraph("A gépkocsi adatai: ",FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                  Paragraph p6 = new Paragraph("          Rendszam: " + rendszam);     
                  Paragraph p7 = new Paragraph("          Tipus: " + tipus);  
                  Paragraph p8 = new Paragraph("          Hengerurtatralom: "+henger);  
                  Paragraph p9 = new Paragraph("          Uzemanyag fajta: "+uzemanyag);
                  Paragraph p10 = new Paragraph("          Uzemanyag norma 1/100km(A): "+norma);
                  Paragraph p11 = new Paragraph("          Amortizacios koltseg Ft/km(B): "+amortizacio);
                  Paragraph p12 = new Paragraph("          Uzemanyag egysegara Ft-ban(C): "+ ar);
                  Paragraph p13 = new Paragraph("          A teljes utvonal hossza km(D): "+ utvonal);
                  
            
            Chunk p14 = new Chunk("Atalany szerinti uzemanyag es amortizacio: ");
            p14.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            Paragraph p15 = new Paragraph("üzemanyag koltség (D/100xAxC): " + (utvonal/100*norma*ar));
                p15.setLeading(30);
            Paragraph p16 = new Paragraph("Amortizaciós koltseg (BxD):"+(amortizacio*utvonal));
                p16.setLeading(30);
            Paragraph p17 = new Paragraph("__________________________________________________________________________");      
            
            Chunk p18 = new Chunk("Esetleges szamlaval igazolt tovabbi koltsegek:");
            p18.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            
            Paragraph p19 = new Paragraph("Autopalyadij Ft-ban: " + palya);
            Paragraph p20 = new Paragraph("Autopalyadij valutaban* es Ft-ban: " + palya );
            Paragraph p21 = new Paragraph("Parkirozasi dij valutaban* és forintban: " + parkolas);
            Paragraph p22 = new Paragraph("Valuta arfolyam**: "+ valuta);
            Osszkoltseg=((utvonal/100*norma*ar)+(amortizacio*utvonal)+parkolas+palya);
            Paragraph p23 = new Paragraph("Osszes koltség Ft-ban: " +Osszkoltseg,FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                
            Chunk p24 = new Chunk("Megjegyzesek: ");
            p24.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location   
           
            Paragraph p25 = new Paragraph
            ("* Bizonylat csatolando\n"
             + "* A valuta valtas bizonylatan szereplo árfolyam, ennek hianyaan az utazast megelozo honap 15-dikei MNB deviza középarfolyam.\n"
             + "Utazasi tervhez a kalkulacios lap kitoltese szukseges, elszamolaskor azonban kiküldetesi rendelvenyt kell kitolteni. "
            );
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
           
            
            Paragraph p26 = new Paragraph("Veszrpem, "+dateFormat.format(date));
            
            Paragraph p27 = new Paragraph("Alairasok: ");
            
             Paragraph p28 = new Paragraph("Utazo: ___________   Logisztikai csoport: __________    Engedelyezo Hivatal: ____________ ");
             
           
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
           
            document.close();
            //Convert PDF to byteARRAY
            //Convert PDF to byteARRAY
            //Convert PDF to byteARRAY
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            return pdfBytes;
        }
        catch(Exception e){
            System.out.println("Couldnt Generate PDF");
            return null;
        }
    }
     
    public byte[] genUtazasiTerv(Person szemely,
            String  munkahely, String e_cim, String elfogadva, String program_tipus,
            String hely, String idotartam, String valuta, int napidij , int szallasdij,
            double arfolyam, int nap_db, String fedezet, int egyeb,int eloleg,int deviza,
            int osztondij,int honap_db, String utazasi_mod,int felhasznalt_napok,String velemeny)
    {
         Document document = new Document();
        try{
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
             munkahely= "Pannon Egyetem";
            Paragraph p1 = new Paragraph("Utazasi terv*",FontFactory.getFont(FontFactory.TIMES_BOLD,15,Font.BOLD,BaseColor.BLACK));              
                p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("Nev(adoszam)beosztas: " + szemely.getVnev() + " " + szemely.getKnev() + " " + szemely.getAdoszam() +" "+ szemely.getBeosztas());
                 p2.setLeading(30);
            Paragraph p3 = new Paragraph("Munkahely(egyseg): " + munkahely);
            Paragraph p4 = new Paragraph("Konferencian valo reszvetel eseten a bejelentett eloadas/poszter* cime: " + e_cim); 
            Paragraph p5 = new Paragraph("Az eloadast/posztert a konferencia szervezoi elfogadtak/meg nem fogadtak el: " + elfogadva );
            Paragraph p6 = new Paragraph("Tanulmany eseten milyen program keretében kerul sor az utazasra*: " + program_tipus);
                p6.setLeading(30);
            Paragraph p7 = new Paragraph("A külfoldi tartozkodas hely(orszag,varos,intezmény stb.): " + hely); 
            Paragraph p8 = new Paragraph("Idopont,idotartam: " + idotartam); 
            
            Paragraph p9 = new Paragraph("Költsegterv",FontFactory.getFont(FontFactory.TIMES_BOLD,15,Font.BOLD,BaseColor.BLACK));              
            p9.setAlignment(Element.ALIGN_CENTER);
            Paragraph p10 = new Paragraph("a.) Ideiglenes külfoldi utazasnal "); 
            
           
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);


            PdfPCell c1 = new PdfPCell(new Phrase(" "));   
            c1.setColspan(5);
            table.addCell(c1);           
            c1 = new PdfPCell(new Phrase("kulfoldi penznem"));  
            c1.setColspan(2);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Forint")); 
            c1.setColspan(2);
            table.addCell(c1);
            c1.setColspan(1);
            
            
            for (int i=0; i<5; i++) 
            {   
                c1 = new PdfPCell(new Phrase(" ")); 
                table.addCell(c1);
              
            }
            c1 = new PdfPCell(new Phrase("osszeg")); table.addCell(c1);  
            c1 = new PdfPCell(new Phrase("valutanem")); table.addCell(c1); 
            c1 = new PdfPCell(new Phrase("osszeg ")); table.addCell(c1); 
            c1 = new PdfPCell(new Phrase("fedezeti forras")); table.addCell(c1); 
            
            c1 = new PdfPCell(new Phrase("1.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("napidij"));
            c1.setColspan(1);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + napidij));
            c1.setColspan(1);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("napra"));            
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("osszesen"));           
            table.addCell(c1);
            double osszeg = round(napidij * nap_db/arfolyam,2);
            c1 = new PdfPCell(new Phrase(" " + osszeg ));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + (napidij*nap_db)));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("2.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("szallasdij"));
            c1.setColspan(1);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + szallasdij));
            c1.setColspan(1);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("ejszakara"));            
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("osszesen"));           
            table.addCell(c1);
            osszeg = round((szallasdij * nap_db)/arfolyam,2);
             c1 = new PdfPCell(new Phrase(" " + osszeg ));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + (szallasdij*nap_db)));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
            
            
            c1 = new PdfPCell(new Phrase("3.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("reszveteli dij"));
            c1.setColspan(4);
            table.addCell(c1);
            
            osszeg = round((szallasdij * nap_db+napidij*nap_db)/arfolyam,2);
            c1 = new PdfPCell(new Phrase(" " + osszeg ));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + (szallasdij*nap_db+napidij*nap_db)));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
            
            
             c1 = new PdfPCell(new Phrase("4.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("utikoltseg (szg-knal kalkulacios lap)"));
            c1.setColspan(4);
            table.addCell(c1);
            osszeg = round(Osszkoltseg/arfolyam,2);
            c1 = new PdfPCell(new Phrase(" " + osszeg ));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + Osszkoltseg));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
           
             c1 = new PdfPCell(new Phrase("5.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("egyeb/es pedig............"));
            c1.setColspan(4);
            table.addCell(c1);
            osszeg=(round(egyeb/arfolyam,2));
            c1 = new PdfPCell(new Phrase(" " + osszeg));  table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + egyeb));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1); 
            
            
             c1 = new PdfPCell(new Phrase("6.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("Felvenni kivant eloleg: bankszamla(" +  szemely.getBankszamlaszam() +")"));
            c1.setColspan(6);
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + eloleg));  table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
            
             c1 = new PdfPCell(new Phrase("7.)"));       
            table.addCell(c1);
            c1 = new PdfPCell(new Phrase("atutalni kivant deviza"));
            c1.setColspan(4);
            table.addCell(c1);
            osszeg=(round(deviza/arfolyam,2));
            c1 = new PdfPCell(new Phrase(" " + osszeg));  table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + deviza));  table.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table.addCell(c1);
            
            Paragraph p11 = new Paragraph("b.) Tartos külfoldi utazasnal ");
            
            PdfPTable table2 = new PdfPTable(9);
            table2.setWidthPercentage(100);
            
             c1 = new PdfPCell(new Phrase("1.)"));       
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase("osztondij"));
            c1.setColspan(1);
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + osztondij));
            c1.setColspan(1);
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase("honapra"));            
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase("osszesen"));           
            table2.addCell(c1);
            osszeg = round(osztondij * honap_db/arfolyam,2);
            c1 = new PdfPCell(new Phrase(" " + osszeg ));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + (osztondij*honap_db)));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table2.addCell(c1);
            
            
            c1 = new PdfPCell(new Phrase("2.)"));       
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase("utikoltseg (szg-knal kalkulációs lap)"));
            c1.setColspan(4);
            table2.addCell(c1);
            osszeg = round(Osszkoltseg/arfolyam,2);
            c1 = new PdfPCell(new Phrase(" " + osszeg ));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + Osszkoltseg));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table2.addCell(c1);
            
            
             c1 = new PdfPCell(new Phrase("3.)"));       
            table2.addCell(c1);
            c1 = new PdfPCell(new Phrase("egyeb/es pedig............"));
            c1.setColspan(4);
            table2.addCell(c1);
            osszeg=(round(egyeb/arfolyam,2));
            c1 = new PdfPCell(new Phrase(" " + osszeg));  table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + valuta));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + egyeb));   table2.addCell(c1);
            c1 = new PdfPCell(new Phrase(" " + fedezet));   table2.addCell(c1); 
            
            Paragraph p12 = new Paragraph("Uzatas modja:  "+ utazasi_mod);
            
            int year = Calendar.getInstance().get(Calendar.YEAR);
            
            Paragraph p13 = new Paragraph("A " + year + "." + "evre rendelkezesre allo 20 nap rendkívüli kiküldetesből eddig "+ felhasznalt_napok + " napot vettem igenybe");
            Paragraph p14 = new Paragraph("Kerem      nap kiküldetes /..... nap rendes/.....  nap fizetes nelküli szabadsag és az atutalás  \nengedelyezeset.**    ");
            Paragraph p15 = new Paragraph("Mellekletek:      - konferencia kiadvany\n"
                    + "                         - deviza atutalás eseten az utalashoz szükseges adatokkal kitöltött urlap*\n" +
"	                         - gepkocsi kalkulacio*\n" +
"	                         - eloadas visszaigazolas\n" +
"	                         - meghívo level; fogadokeszseg igazolasa\n" +
"	                         - ösztöndijas tanulmanyut eseten nyilatkozat arrol, hogy ösztöndíjat kap\n" +
"	                         - palyazati tamogatasokról masolat  ");
            Paragraph p16 = new Paragraph("Megjegyzések:\n* az urlap a honlaprol letöltheto\n" +
"**a nem kivant resz törlendo  ");
            
             Paragraph p17 = new Paragraph("Tudomasul veszem, hogy a hazaérkezesemet követo 15 munkanapon belül a rendelkezesemre bocsátott elolegrol, (kp+atutalas) a  KÜLFÖLDI KIKÜLDETESI UTASÍTAS ÉS KÖLTSEGELSZAMOLAS nyomtatvány kitöltesevel, a hozzakapcsolodo szamlak és az uti jelentes egyideju leadasaval az engedelyezo hivatalban el kell szamolnom. Az elszamolas szerint fel nem hasznalhato összege, az elszamolas elfogadasat követoen a penztarba  vissza kell fizetnem.  ");
             DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
           
            
            Paragraph p18 = new Paragraph("Datum: "+dateFormat.format(date));
            Paragraph p19 = new Paragraph("                                                                             Utazo alairasa:_________________ "); 
            Paragraph p20 = new Paragraph("Tanszekvezetoi/egysegvezetoi velemeny (kerjük a helyettesítes szüksegesseget megadni):\n"); 
            Paragraph p21 = new Paragraph(velemeny);
            Paragraph p22 = new Paragraph("                                                                             Utazo alairasa:_________________ ");
            
             
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p5);
            document.add(p6);
            document.add(p7);
            document.add(p8);
            document.add(new Phrase("\n\n")); 
            document.add(p9);
            document.add(p10);
            document.add(table);
            document.add(new Phrase("\n\n")); 
            document.add(p11);
            document.add(table2);
            document.add(p12);
            document.newPage();
            document.add(p13);
            document.add(new Phrase("\n")); 
            document.add(p14);
            document.add(new Phrase("\n")); 
            document.add(p15);
            document.add(new Phrase("\n")); 
            document.add(p16);
            document.add(new Phrase("\n\n")); 
            document.add(p17);
            document.add(new Phrase("\n")); 
            document.add(p18);
            document.add(new Phrase("\n")); 
            document.add(p19);
            document.add(new Phrase("\n")); 
            document.add(p20);
            document.add(new Phrase("\n")); 
            document.add(p21);
            document.add(new Phrase("\n")); 
            document.add(p18);
            document.add(new Phrase("\n")); 
            document.add(p22);
            
           
            document.close();
            //Convert PDF to byteARRAY
            //Convert PDF to byteARRAY
            //Convert PDF to byteARRAY
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            return pdfBytes;
        }
        catch(Exception e){
            System.out.println("Couldnt Generate PDF");
            return null;
        }
    }
     
      public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}

}
