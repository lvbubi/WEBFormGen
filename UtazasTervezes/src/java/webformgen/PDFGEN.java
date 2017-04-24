
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
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PDFGEN {
     private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
     private double Osszkoltseg=0;

    public double getOsszkoltseg() {
        return Osszkoltseg;
    }
     public byte[] genKalkulacio(String nev, String beosztas, String rendszam, double henger, String uzemanyag, double norma, int amortizacio, int ar, double utvonal, String terv, String tipus, double valuta, double palya, double parkolas)
    {
         Document document = new Document();
        try{
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            
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
                  Paragraph p9 = new Paragraph("          Üzemanyag fajta: "+uzemanyag);
                  Paragraph p10 = new Paragraph("          Üzemanyag norma 1/100km(A): "+norma);
                  Paragraph p11 = new Paragraph("          Amortizaciós költseg Ft/km(B): "+amortizacio);
                  Paragraph p12 = new Paragraph("          Üzemanyag egységára Ft-ban(C): "+ ar);
                  Paragraph p13 = new Paragraph("          A teljes útvonal hossza km(D): "+ utvonal);
                  
            
            Chunk p14 = new Chunk("Atalany szerinti üzemanyag es amortizació: ");
            p14.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            Paragraph p15 = new Paragraph("Üzemanyag költség (D/100xAxC): " + (utvonal/100*norma*ar));
                p15.setLeading(30);
            Paragraph p16 = new Paragraph("Amortizációs költseg (BxD):"+(amortizacio*utvonal));
                p16.setLeading(30);
            Paragraph p17 = new Paragraph("__________________________________________________________________________");      
            
            Chunk p18 = new Chunk("Esetleges számlával igazolt további költségek:");
            p18.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
            
            
            Paragraph p19 = new Paragraph("Autópályadij Ft-ban: " + palya);
            Paragraph p20 = new Paragraph("Autopalyadij valutában* és Ft-ban: " );//bekérve deviza, megjelenízve deviza+ft
            Paragraph p21 = new Paragraph("Parkirozási dij valutában* és forintban: "+parkolas);
            Paragraph p22 = new Paragraph("Valuta árfolyam**: "+ valuta);
            Osszkoltseg=((utvonal/100*norma*ar)+(amortizacio*utvonal)+parkolas+palya);
            Paragraph p23 = new Paragraph("Összes költség Ft-ban: " +Osszkoltseg,FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK));
                
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
     
       public byte[] genUtazásiTerv(String nev, String beosztas, String adoszam)
       {
         Document document = new Document();
       try{
            PdfWriter.getInstance(document, new FileOutputStream("Utazasi_terv.pdf"));
            document.open();
            
            Paragraph p1 = new Paragraph("Utazasi terv*",FontFactory.getFont(FontFactory.TIMES_BOLD,15,Font.BOLD,BaseColor.BLACK));              
                p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph("Nev(adoszam)beosztas: " + nev + " " + adoszam + " " + beosztas);
                 p2.setLeading(30);
            Paragraph p3 = new Paragraph("Munkahely(egység): " + "Munkahely");
            Paragraph p4 = new Paragraph("Konferencián valo reszvetel eseten a bejelentett eloadas/poszter* cime: " + "cim"); 
            Paragraph p5 = new Paragraph("Az eloadast/posztert a konferencia szervezoi elfogadtak/még nem fogadtak el: " + "IGEN/NEM");
            Paragraph p6 = new Paragraph("Tanulmany esetén milyen program keretében kerul sor az utazasra*: " + "PROGRAM");
                p6.setLeading(30);
            Paragraph p7 = new Paragraph("A külfoldi tartozkodas hely(orszag,varos,intezmény stb.): " + "Tartozkodas"); 
            Paragraph p8 = new Paragraph("Idopont,idotartam: " + "idotartam"); 
            
            Paragraph p9 = new Paragraph("Költsegterv",FontFactory.getFont(FontFactory.TIMES_BOLD,15,Font.BOLD,BaseColor.BLACK));              
            p9.setAlignment(Element.ALIGN_CENTER);
            Paragraph p10 = new Paragraph("a.) Ideiglenes külfoldi utazasnal "); 
            
           
            PdfPTable table = new PdfPTable(9);
            table.setTotalWidth(500);

                // t.setBorderColor(BaseColor.GRAY);
                // t.setPadding(4);
                // t.setSpacing(4);
                // t.setBorderWidth(1);

                PdfPCell c1 = new PdfPCell(new Phrase(" "));                              
                table.addCell(c1);                               
                table.addCell(c1);
                table.addCell(c1);
                table.addCell(c1);
                table.addCell(c1);               
                c1 = new PdfPCell(new Phrase("Külföldi penznem"));
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(" "));
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Forint"));
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(" "));
                table.addCell(c1);
               // table.setHeaderRows(1);

              

            
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

}

