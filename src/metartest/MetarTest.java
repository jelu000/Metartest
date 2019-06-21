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
        
        MetarData mt = new MetarData();
        
        System.out.println("\n\n.:Flygplats: " +  mt.getAirport()+ ":.");
        System.out.println("------------------------");
        System.out.println("Datum: " +  mt.getDate());
        System.out.println("Tid: " +  mt.getTime());
        System.out.println("Vindstyrka: " +  mt.getTemperatur() + " m/s");
        System.err.println("Vindriktning: " + mt.gettWindDirection() +"\n------------------------");
    }
    
}
