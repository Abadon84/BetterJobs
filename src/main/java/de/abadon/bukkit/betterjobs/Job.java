/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.bukkit.betterjobs;

/**
 *
 * @author Abadon
 */
import java.util.HashMap;

public class Job {
    public String name = null;
    public String description = null;
    public String prefix = null;
    public String suffix = null;
    public double moneyGain = 0.0;
    public double xpGain = 0.0;
    public double flatrate = 0.0;
    public double[] blockCreate = null;
    public double[] blockDestroyPlayer = null;
    public double[] blockDestroyNonPlayer = null;
    public HashMap<String,Double> kills = new HashMap<String, Double>();    
    
    public Job(String jName, String jDescription, String jPrefix, String jSuffix, double jMoneyGain, double jXpGain, double jFlatrate){
        name = jName;
        description = jDescription;
        prefix = jPrefix;
        suffix = jSuffix;
        moneyGain = jMoneyGain;
        xpGain = jXpGain;
        flatrate = jFlatrate;
    }
}
