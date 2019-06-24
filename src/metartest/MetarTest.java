/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metartest;

/**
 *
 * @author jens
 */
public class MetarTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 0){ 
        MetarData mt = new MetarData();
        mt.printWeather();
       
        }
        
        else{
            System.out.println("Args 0 =" + args[0]);
            try{
                
                
                MetarData mt = new MetarData(args[0]);
                mt.printWeather();
               
            }
            catch(Exception e){
                System.out.println("Kunde inte hitta ICAO kod förflygplats!");
            
            }
        
        }
        
    }
    
}
