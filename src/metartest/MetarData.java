/*
 * License FREE
 */
package metartest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.*;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author jens
 */
public class MetarData {
    
    private final String USER_AGENT = "Mozilla/5.0";
    private String datum;
    private String tid; 
    private String temperatur;
    private String vindspeed;
    private String string_url = "https://tgftp.nws.noaa.gov/data/observations/metar/stations/";
    private String gmt_time;
    private String gmt_date;
    
    /**
     * MetarData
     * Set data for Östersundsflygplats ICAO_kod ESNZ
     */
    public MetarData(){
        
        String t_airport = "ESNZ.TXT";
        getHttpMetar(t_airport);
    }
    /**
     * MetarData
     * Set data for Airport with specifik ICAO_kod
     * In: ICAO_kod
     */
    public MetarData(String t_airport ){
        t_airport = t_airport + ".TXT";
        getHttpMetar(t_airport);
    }
    
    
    private void getHttpMetar(String t_airport){
        
        string_url = string_url + t_airport;
        
        System.out.println("Sending 'GET' request to URL: " + string_url);
        try {
            //URL url = new URL("https://tgftp.nws.noaa.gov/data/observations/metar/stations/ESNZ.TXT");
            URL url = new URL(string_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
           
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
            }
            in.close();

            //System.out.println(response.toString() + "\n\n-----------------");
            setVariables(response.toString());//sätter först Globala variabler från metar tag med UTC tidszon
            setGmtTime();//Skapar globala variabler för GMT tidszon
        
        }
        catch(Exception e){
            System.out.println("RequestError=" + e);
        }
    }
    
    
    private void setVariables(String t_metar){
        String[] t_split = t_metar.split(" ");
        
        datum = t_split[0];//datum
        tid = t_split[1];//tid
                       
        Pattern p = Pattern.compile("[0-9][0-9]/[0-9][0-9]");//get the temperatur
        Pattern p2 = Pattern.compile("[0-9]*KT");//get the winddir and speed //[0-9]*KT

        for (String t_value: t_split){
            //System.out.println(t_value);

            Matcher m = p.matcher(t_value); //temperaturen
            if (m.find()){//hittar och sätter temperaturen
                //System.out.println("Matcher=" + m.find());
                temperatur = t_value;
            }
        
            m = p2.matcher(t_value);// vindriktning och vindstyrka
            if (m.find()){
                //System.out.println("Matcher2=" + m.find());
                vindspeed = t_value;
            }
        }
    
        //System.out.println("\n"+ datum +"\n"+ tid +"\n"+ temperatur +"\n"+ vindspeed);
    }
     private String setGmtTime(){
        String t_tid = tid.substring(0, 5);
        String[] t_tim_min = t_tid.split(":");
        
       
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
               
       cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t_tim_min[0]));
       cal.set(Calendar.MINUTE, Integer.parseInt(t_tim_min[1]));
       
       String[] t_year_month = datum.split("/");
       
       cal.set(Calendar.YEAR, Integer.parseInt(t_year_month[0]));
       cal.set(Calendar.MONTH, Integer.parseInt(t_year_month[1]));
       cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(t_year_month[2]));
       
       cal.setTimeZone(TimeZone.getTimeZone("GMT"));
       
       SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
       SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
       
       gmt_time = sdf1.format(cal.getTime());
       
       gmt_date = sdf2.format(cal.getTime());
       
       return tid.substring(0, 5);
   
   }
    /**
     * getTemperatur()
     * @return int temperatur
     */
    public int getTemperatur(){
    
        String t_temparray[]= temperatur.split("/");
        float t_temp = Float.parseFloat(t_temparray[0]);
        t_temp *= 0.514;
        int t_w = Math.round(t_temp);
        
        return t_w;
        
    }
    
    /**
     * getGmtDate()
     * @return String gmt_date
     */
    public String getGmtDate(){
        return gmt_date;
    
    }
    /**
     * getGmtTime()
     * @return String gmt_time
     */
    public String getGmtTime(){
        return gmt_time;
    
    }
   /**
     * getAirport()
     * @return String ICAO_kod
     */
   public String getAirport(){
       return tid.substring(5, 9);
   }
   
   /**
     * getWindspeed()
     * @return String windspeed 
     */
   public  String getWindspeed(){
        
       String t_vindspeed = vindspeed; 
       t_vindspeed = t_vindspeed.substring(3,5);
       
      
       Double t_speed = Double.parseDouble(t_vindspeed);
       NumberFormat formatter = new DecimalFormat("#0.00");     

       t_speed = t_speed*0.5144;
       String t_string = new String(formatter.format(t_speed));
       
       return t_string;
   
   }
   /**
     * getWindDirection()
     * @return String wind_direction
     */
   public String gettWindDirection(){
       
       int t_grader = Integer.parseInt(vindspeed.substring(0, 3));
       
       String t_winddir ="";

       if (t_grader>=0 && t_grader<=12)
          t_winddir="N";

       else if (t_grader>=13 && t_grader<=34)
          t_winddir="N NO";

       else if (t_grader>=35 && t_grader<=56)
          t_winddir="NO";

       else if (t_grader>=57 && t_grader<=78)
          t_winddir="O NO";

       else if (t_grader>=79 && t_grader<=102)
          t_winddir="O";

       else if (t_grader>=103 && t_grader<=124)
          t_winddir="O SO";

       else if (t_grader>=125 && t_grader<=146)
          t_winddir="SO";

       else if (t_grader>=147 && t_grader<=168)
          t_winddir="S SO";

       else if (t_grader>=169 && t_grader<=192)
          t_winddir="S";

       else if (t_grader>=193 && t_grader<=214)
          t_winddir="S SV";

       else if (t_grader>=215 && t_grader<=236)
          t_winddir="SV";

       else if (t_grader>=237 && t_grader<=258)
          t_winddir="V SV";

       else if (t_grader>=259 && t_grader<=282)
          t_winddir="V";

       else if (t_grader>=283 && t_grader<=304)
          t_winddir="V NV";

       else if (t_grader>=305 && t_grader<=326)
          t_winddir="NV";

       else if (t_grader>=327 && t_grader<=347)
          t_winddir="N NV";

       else if (t_grader>=348 && t_grader<=360)
          t_winddir="N";

       return t_winddir;
   }
   /**
     * getGmtTime()
     * printout the wheather for airport
     */
   public void printWeather(){
        System.out.println("\n\n.:Flygplats: " +  getAirport()+ ":.");
        System.out.println("------------------------");
        System.out.println("Datum: " +  getGmtDate());
        System.out.println("Tid: " +  getGmtTime());
        
        System.out.println("Temperatur: " +  getTemperatur() + " c\u00B0");
        System.out.println("Vindstyrka: " +  getWindspeed()+ " m/s");
        System.err.println("Vindriktning: " + gettWindDirection() +"\n------------------------");
   
   }
          
   
}
