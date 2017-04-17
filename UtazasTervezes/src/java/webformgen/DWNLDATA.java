package webformgen;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class DWNLDATA {
    
    List<String> uzemanyag= new ArrayList();
    Map benzinnorma= new HashMap();
    Map gazolajnorma= new HashMap();
    Map motornorma= new HashMap();
    
    public DWNLDATA()
    {
        try {
        //uzemanyag=getFusion();
        benzinnorma=getBenzinFogyasztas();
        gazolajnorma=getGazolajFogyasztas(); 
        motornorma=getMotorFogyasztas();
        } 
        catch (IOException ex) {
            Logger.getLogger(DWNLDATA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<String> getPrices(String cim) throws IOException
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
        
       for(int i=0; i<tmp.size(); i++)
       {   
           if(tmp.get(i).matches("<p>.*[0-9][0-9][0-9].*</p>"))
           //System.out.println(tmp.get(i));
           tmp2.add(tmp.get(i));      
       }
       
       String price;
       price = null;
       for(int i=0; i<tmp2.size(); i++)
       {   
          //System.out.println(tmp2.get(i));
           price = tmp2.get(i).replaceAll("[^0-9]", "");  
           last.add(price);
       }
       
      //   for(int i=0; i<last.size(); i++)
       //      System.out.println(last.get(i)); 
         
         
         return last;
    }
      
    private List<String> getMonths(String cim) throws IOException 
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
         
          for(int i=0; i<tmp.size(); i++)
        {   
            if(tmp.get(i).matches("<p>&nbsp;[^0123456789]*</p>|<p align=\"center\"> <strong>[^0123456789].* </strong> </p>"))
            //System.out.println(tmp.get(i));  
            tmp2.add(tmp.get(i));
        }       
           for(int i=0; i<tmp2.size(); i++)
        {             
            String proba =  tmp2.get(i);
            proba = proba.replaceAll("<p>", "");
            proba = proba.replaceAll(" </strong> </p>", "");
            proba = proba.replaceAll("</p>", "");
            proba = proba.replaceAll("&nbsp;", "");
            proba = proba.replaceAll("<p align=\"center\"> <strong>", "");     
          ///  System.out.println(proba);
            last.add(proba);
          //  System.out.println(tmp2.get(i));            
        }       
          // for(int i=0; i<last.size(); i++)         
         //   System.out.println(last.get(i));  
           return last;
    }
  
    private List<String> getFusion() throws IOException
    {
        List<String> lista = new ArrayList();
        List<String> price = getPrices("http://www.nav.gov.hu/nav/szolgaltatasok/uzemanyag/uzemanyagarak/");
        List<String> month = getMonths("http://www.nav.gov.hu/nav/szolgaltatasok/uzemanyag/uzemanyagarak/");
           
        lista.add(month.get(0));
        int index=1;
        for(int i=0; i<price.size()-1; i++)
        {   
          
            lista.add(price.get(i));
            if(index<4)
            {
                if((i+1)%4==0) 
                {   
                lista.add(month.get(index));
                index++;
                 }
            }
        }
    
        return lista;
    }
    
    private Document Connect(String cim) throws IOException
    {
       //System.setProperty("http.proxyHost", "proxy.vekoll.uni-pannon.hu");
       //System.setProperty("http.proxyPort", "3128"); 
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
    
    private Map getMNB(String valuta, String begin,String end) throws IOException
    {   Map arfolyam = new HashMap();
        
         final Document document;
         String seged = 
      "http://www.mnb.hu/arfolyam-tablazat?deviza=rbCurrencySelect&devizaSelected="+valuta+"&datefrom="+begin+"&datetill="+end+"&order=1";
         
        document = Connect(seged);
      // System.out.println(document.outerHtml());
        Elements paragraphs = document.select("span");
        List<String> tmp=new ArrayList();
        List<String> tmp2=new ArrayList();
        for (Element p : paragraphs)
            tmp.add(p.toString());
             
        for(int i=0; i<tmp.size();i++)
        {   
            if(tmp.get(i).matches("<span>20.*</span>|<span>[0-9][0-9][0-9].*</span>"))
            tmp2.add(tmp.get(i));
        }
        
        String date=null;
        String value=null;
        double ertek=0;
         for(int i=0; i<tmp2.size();i=i+2)
        {   
            //if(tmp.get(i).matches("<span>20.*</span>|<span>[0-9][0-9][0-9].*</span>"))
            
            date=tmp2.get(i).replace("<span>", "");
            date=date.replace("</span>", "");
            value=tmp2.get(i+1).replace("<span>", "");
            value=value.replace("</span>", "");
            value=value.replace(",", ".");
            ertek=Double.valueOf(value);
           // System.out.println(date + " " + value);
            arfolyam.put(date,ertek);
        }
        
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
    
    public double selectArfolyam(String date,String valuta, String begin,String end) throws IOException
    {   // date pl.: 2017. március 9.
         Map tmp= getMNB(valuta,begin,end);
         double value=(double) tmp.get(date);     
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
    
    public int selectUzemanyag( String tipus,String honap )
    {   int tmp = 0;
        int index=0;
        List<String> seged=getUzemanyag();             
       for(int i=0;i<seged.size();i++)
      {     
          if(seged.get(i).equals(honap))
          {
              index=i;
          }
      } 
      int[] anyag = new int[4];
       
      int j=0;
      for(int i=index;i<index+4;i++)
      {
          anyag[j]=Integer.valueOf(seged.get(i+1));
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
   
   


    
}