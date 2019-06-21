/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metartest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    
    public MetarData(){
        String t_airport = "ESNZ.TXT";
        
        getHttpMetar(t_airport);
    }
    
    public MetarData(String t_airport ){
    
    
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
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString() + "\n\n-----------------");
            setVariables(response.toString());

        
        }
        catch(Exception e){
            System.out.println("RequestError=" + e);
        }
    
    
    }
    
    
    private void setVariables(String t_metar){
        String[] t_split = t_metar.split(" ");
        //[0-9]*KT
        Pattern p = Pattern.compile("[0-9][0-9]/[0-9][0-9]");
        Pattern p2 = Pattern.compile("[0-9]*KT");

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
    }
}
