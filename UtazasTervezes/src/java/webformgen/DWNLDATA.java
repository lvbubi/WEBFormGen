package webformgen;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class DWNLDATA {
    
    List<String> uzemanyag= new ArrayList();
    Map benzinnorma= new HashMap();
    Map gazolajnorma= new HashMap();
    Map motornorma= new HashMap();
    
    public DWNLDATA() throws IOException
    {
         uzemanyag=getFuel("http://www.nav.gov.hu/nav/szolgaltatasok/uzemanyag/uzemanyagarak/");
         benzinnorma=getBenzinFogyasztas();
         gazolajnorma=getGazolajFogyasztas(); 
         motornorma=getMotorFogyasztas();
         
    }
    
    // Fügvéyek az adatok leszedésére a weboldalakról
    
    private List<String> getFuel(String cim) throws IOException 
    {   
        final Document document;
        document = Connect(cim);
      // System.out.println(document.outerHtml());
        Elements paragraphs = document.select("p");
        List<String> tmp=new ArrayList();
        List<String> tmp2=new ArrayList();
        List<String> last=new ArrayList();
        for (Element p : paragraphs)
            tmp.add(p.toString());
        
      //  for(int i=0; i<tmp.size(); i++)
       //     System.out.println(tmp.get(i));
         
        for(int i=0; i<tmp.size(); i++)
        {   
            if(tmp.get(i).matches("<p>&nbsp;.*</p>|<p> <strong>&nbsp;.*</strong> </p>|<p align=\"center\">.* </p>|<p>[0-9][0-9][0-9] </p>"))
        //    System.out.println(tmp.get(i));  
            tmp2.add(tmp.get(i));
        }       
           for(int i=0; i<tmp2.size(); i++)
        {             
            String proba =  tmp2.get(i);
            proba = proba.replaceAll("<p> <strong>", "");   
            proba = proba.replaceAll(" </strong> </p>", "");
            proba = proba.replaceAll("<p>", "");            
            proba = proba.replaceAll("</p>", "");
            proba = proba.replaceAll("&nbsp;", "");
            proba = proba.replaceAll("</strong>", "");
            proba = proba.replaceAll("<p align=\"center\">", "");
            proba = proba.replaceAll(" </p>", "");
            proba = proba.replaceAll(" ", "");
         
            
            if(proba.length()<=8)
            {   
               // System.out.println(proba);
                last.add(proba);
            }                     
        }       
        
            last.remove(last.size()-1);
          //  System.out.println(last);
           return last;
    }
    
    private Document Connect(String cim) throws IOException
    {
       System.setProperty("http.proxyHost", "proxy.vekoll.uni-pannon.hu");
       System.setProperty("http.proxyPort", "3128"); 
       final Document document;
       document = Jsoup.connect(cim).get();
       return document;
           
    }
    
    private Map getBenzinFogyasztas() throws IOException
    {   
         Map norma= new HashMap();
         norma.put("1000",7.6);
         norma.put("1001-1500",8.6 );
         norma.put("1501-2000",9.5 );
         norma.put("2001-3000",11.4 );
         norma.put("3001",13.3 );
         
        return norma;
    }
    
    private Map getGazolajFogyasztas() throws IOException {
        Map norma= new HashMap();
         norma.put("1500",5.7);
         norma.put("1001-2000",6.7 );
         norma.put("2001-3000",7.6 );
         norma.put("3001",9.5 );
         return norma;
    }

    private Map getMotorFogyasztas() throws IOException {
         Map norma= new HashMap();
         norma.put("segedmotor",3.0);
         norma.put("80",3.5 );
         norma.put("81-125",3.8 );
         norma.put("126-250",4 );
         norma.put("251-500",4.5 );
         norma.put("500",5.0 );
         return norma;
    }
    
    private double getMNB(String valuta, String date1, String date2) throws IOException
    {    
        double arfolyam=-1;
         final Document document;
         String seged = 
      "http://www.mnb.hu/arfolyam-tablazat?deviza=rbCurrencySelect&devizaSelected="+valuta+"&datefrom="+date1+"&datetill="+date2+"&order=1";
         
        document = Connect(seged);
      // System.out.println(document.outerHtml());
        Elements paragraphs = document.select("span");
        List<String> tmp=new ArrayList();
        List<String> tmp2=new ArrayList();
        List<String> last=new ArrayList();
        for (Element p : paragraphs)
            tmp.add(p.toString());
             
        for(int i=0; i<tmp.size();i++)
        {   
            if(tmp.get(i).matches("<span>[0-9][0-9][0-9].*</span>"))
            tmp2.add(tmp.get(i));
        }
        
        String [] datum = 
         {
             "január","február","március","április","május","június","július","Augusztus","szeptember","október",
             "november","december"
         };
        
        
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        //System.out.println(month);
        
        String honap = datum[month-1];
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String minta = "<span>"+year+". "+honap+" ";
        
        String nap = null;
        String value=null;
        
        for(int i=0; i<tmp2.size();i++)
        {  
             if(i%2==0)
             {
                 nap=tmp2.get(i).replaceAll(minta, "");
                 nap=nap.replaceAll(".</span>", "");
                 last.add(nap);
                 //System.out.println(nap);
             }
              if(i%2==1)
             {
                 value=tmp2.get(i).replaceAll("<span>", "");
                 value=value.replaceAll("</span>", "");
                 value=value.replace(",", ".");
                 //System.out.println(value);
                 last.add(value);
             }
             
             
             
        } 
        
        
        String optimal ="15";        
        int distance=0;
        int max=30;
        int index=0;
        
        
        for(int i=0; i< last.size(); i++)
        {   
            if(i%2==0)
            {   
                distance = abs(Integer.valueOf(optimal),Integer.valueOf(last.get(i)));             
                if(distance<=max)
                {
                    max=distance;
                    index=i;
                }
                
            }
            
            
            
          
        }
        
       // System.out.println();
       // System.out.println(distance);
       // System.out.println(index);
       // System.out.println(last);
       // System.out.println();
         
        arfolyam=Double.valueOf(last.get(index+1));
  
        return arfolyam;
    }
    
    private Map getBenzinNorma() {
        return benzinnorma;
    } 

    private Map getGazolajnorma() {
        return gazolajnorma;
    }

    private Map getMotornorma() {
        return motornorma;
    }
    
    private List<String> getUzemanyag() {
        return uzemanyag;
    } 
     

    //fontos adatok Lekérése
    
    public double selectArfolyam(String valuta) throws IOException
    {    
            DateFormat year = new SimpleDateFormat("yyyy");
            DateFormat month = new SimpleDateFormat("MM");
            DateFormat day = new SimpleDateFormat("dd");
            Date time = new Date();
            String datum = year.format(time)+".";
            int honap = Integer.valueOf(month.format(time))-1;
            if(honap<10)    datum=datum+"0"+honap;
            else datum=datum+honap;
            datum=datum+".10.";
            
            if(month.format(time).equals("01"))
            {   
                int tmp;
                tmp = Integer.valueOf(year.format(time))-1;
                datum= tmp+"."+"12."+"10.";
            }
            
            String datum2 = year.format(time)+".";
            honap = Integer.valueOf(month.format(time))-1;
            if(honap<10)    datum2=datum2+"0"+honap;
            else datum2=datum2+honap;
            datum2=datum2+".20.";
            
            if(month.format(time).equals("01"))
            {   
                int tmp;
                tmp = Integer.valueOf(year.format(time))-1;
                datum2= tmp+"."+"12."+"20.";
            }
            
           
            
          //  System.out.println(datum);
          //  System.out.println(datum2);
            
           
        
         double value=getMNB(valuta,datum,datum2);              
         return value;
    } //egy bizonyos napon a valuta erteke
      
    public double selectNorma(String tipus,int henger)
    {   double norma=0;
        
        switch(tipus)
        {
            case "benzin":
                if(henger<=1000) return (double) benzinnorma.get("1000");
                if(henger>=1001  && henger<=2000) return (double) benzinnorma.get("1001-1500");
                if(henger>=2001  && henger<=3000) return (double) benzinnorma.get("1501-2000");
                if(henger>=2001  && henger<=3000) return (double) benzinnorma.get("2001-3000");
                if(henger>=3001) return (double) benzinnorma.get("3001");
            
            case "gazolaj":
                if(henger<=1500) return (double) gazolajnorma.get("1000");
                if(henger>=1501  && henger<=2000) return (double) gazolajnorma.get("1001-2000");
                if(henger>=2001  && henger<=3000) return (double) gazolajnorma.get("2001-3000");
                if(henger>=3001) return (double) gazolajnorma.get("3001");   
            
           case "motor":
                if(henger<=80) return (double) motornorma.get("80");
                if(henger>=81  && henger<=125) return (double) motornorma.get("81-125");
                if(henger>=126  && henger<=250) return (double) motornorma.get("126-250");
                if(henger>=251 && henger<=500) return (double) motornorma.get("251-500");    
                if(henger>=501) return (double) motornorma.get("500");   
                
        }
    
        return norma;
    } // adott tipusú és meretu henger fogyasztása /100km
    
   
    public int selectUzemanyag( String tipus )
    {   int tmp = 0;
        int index=0;
        List<String> seged=getUzemanyag();             
        String honap=null;
        String [] datum = 
         {
             "január","február","március","április","május","június","július","Augusztus","szeptember","október",
             "november","december"
         };
       
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        //System.out.println(month);
        
        honap=datum[month];
        
       for(int i=0;i<seged.size();i++)
      {     
          if(seged.get(i).equals(honap))
          {
              index=i;
          }
      } 
       
     //  System.out.println(seged);
    //   System.out.println(index);
      
      
      int[] anyag = new int[4];
       
      int j=0;
      for(int i=index+1;i<index+5;i++)
      {     
          anyag[j]=Integer.valueOf(seged.get(i));         
          j++;
      }
      
     
      
    
      switch(tipus)
      {
          case "ESZ-95": return anyag[0];
          case "Gázolaj": return anyag[1];
          case "Keverék": return anyag[2];
          case "LPG": return anyag[3];               
      }
      
      
       
        return tmp;
    }
   
    
    private int abs(int a, int b)
    {
        if(a-b>0) return a-b;
        else return (a-b)*-1;
    }
    
    // Példa a fügvények használatára
    
    //System.out.println(tmp.selectNorma("benzin", 1223)); 
    //System.out.println(tmp.selectArfolyam("2017. március 9.", "EUR","2017.03.07.","2017.04.07."));
    //System.out.println(tmp.selectUzemanyag("ESZ-95","január"));
}