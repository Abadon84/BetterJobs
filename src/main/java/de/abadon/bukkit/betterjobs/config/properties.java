/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.bukkit.betterjobs.config;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.List;
import de.abadon.utils.IniProperties;
/**
 *
 * @author Abadon
 */
public class properties {
    private static final String pluginName = "BetterJobs";
    private static final Logger logger = Logger.getLogger("Minecraft." + pluginName);
    private File dataFolder;
    private File confFile;
    private IniProperties props;
    public properties(File path){
        dataFolder = path;
        String confPath = dataFolder.getPath() + "/config.ini";
        confFile = new File(confPath);
    }   
    
    public void loadConfig(){
    try {
      if (!dataFolder.isDirectory()) {
        logger.log(Level.INFO, "[" + pluginName + "] Directory does not exists. Creating dirs.");

        //create all necessary directorys
        if (!dataFolder.mkdirs()) {
          logger.log(Level.WARNING, "[" + pluginName + "] Error during directory create");
          return;
        }
        logger.log(Level.INFO, "[" + pluginName + "] Directory structure planted");
      }

      if (!confFile.exists()){
        logger.log(Level.INFO, "[" + pluginName + "] Planting config file");
        props = new IniProperties();
        setDefault();
        logger.log(Level.INFO, "[" + pluginName + "] Default config created");
      }
      props = new IniProperties(confFile.toString());
      logger.log(Level.INFO, "[" + pluginName + "] loaded ini-file configuration");
    } catch (SecurityException ex) {
      logger.log(Level.WARNING, "[" + pluginName + "] Error during file backend creation");
    }
    }

    public void saveConfig(){
        try {
            props.save();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "[" + pluginName + "] Error during file writing.");
        }
    }

    
    public void setDefault(){
        props.setProperty("BACKEND", "type", "mysql");
        props.setProperty("BACKEND", "server", "localhost");
        props.setProperty("BACKEND", "database", "10_minecraft");
        props.setProperty("BACKEND", "user", "user");
        props.setProperty("BACKEND", "pass", "pass");
        props.setProperty("PLUGIN", "chatDisplay", "none");
        props.setProperty("PLUGIN", "broadcastSkillUp", "true");
        props.setProperty("PLUGIN", "baseXp", "100");
        props.setProperty("PLUGIN", "xpMultiplyer", "1.0");
        props.setProperty("PLUGIN", "flatRatePayout", "0.0");
        props.setProperty("PLUGIN", "switchFee", "500.0");
        props.setProperty("PLUGIN", "payday", "1");
        props.saveAs(confFile.toString());
    }
    
    public String getProperty(String section, String property){
        return props.getProperty(section, property);
    }
    
    public String setProperty(String section, String property, String value){
        try {
            if(!props.getProperty(section, property).equalsIgnoreCase("null")){
            props.setProperty(section, property, value);
            props.save();
            return "Configuration node set";
            }
            logger.log(Level.WARNING, "[" + pluginName + "] Try to set invalid config node.");
            return "Try to set invalid config node.";
        } catch (IOException ex) {
            logger.log(Level.WARNING, "[" + pluginName + "] Error during file writing.");
            return "Error during file writing.";
        }
    }
    
    public HashMap getSection(String section) {
        List<String> Properties = props.getProperties(section);
        HashMap<String,String> Section = new HashMap<String,String> ();
	for (String Property: Properties) {
            Section.put(Property,props.getProperty(section, Property));
        }
        return Section;
    }
}