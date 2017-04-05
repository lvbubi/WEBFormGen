
package webformgen;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class DWNLDATA {
    
    List<String> lista= new ArrayList();
    
    public DWNLDATA() throws IOException
    {
         lista=getFuel();
    }

    public List<String> getPrices() throws IOException
    {
        final Document document;
       document = Jsoup.connect("http://www.nav.gov.hu/nav/szolgaltatasok/uzemanyag/uzemanyagarak/").get();
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
       
        // for(int i=0; i<last.size(); i++)
        //     System.out.println(last.get(i)); 
         
         
         return last;
    }
      
    public List<String> getMonths() throws IOException 
    {
          final Document document;
       document = Jsoup.connect("http://www.nav.gov.hu/nav/szolgaltatasok/uzemanyag/uzemanyagarak/").get();
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
           // if(tmp2.get(i).matches("<p>&nbsp;[^0123456789]*</p>|<p align=\"center\"> <strong>[^0123456789].* </strong> </p>"))
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
        
        //   for(int i=0; i<last.size(); i++)         
        //     System.out.println(last.get(i));
      
           
           return last;
    }
    
    
    public List<String> getFuel() throws IOException
    {
        List<String> lista = new ArrayList();
        List<String> price = getPrices();
        List<String> month = getMonths();
        
        
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

    public List<String> getLista() {
        return lista;
    }


}
