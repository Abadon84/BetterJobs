/*
 * BetterJobs - Jobs plugin for Bukkit  
 * Copyright (C) 2011 Abadon84 http://www.procrafter.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
public class Properties {
    private static final String pluginName = "BetterJobs";
    private static final Logger logger = Logger.getLogger("Minecraft." + pluginName);
    private File dataFolder;
    private File confFile;
    private IniProperties props;
    public Properties(File path){
        dataFolder = path;
        String confPath = dataFolder.getPath() + "/config.ini";
        confFile = new File(confPath);
    }   
    
    /**
     * Load config file, if it not exists create it
     */
    public void loadConfig(){
        try {
        if (!dataFolder.isDirectory()) {
            logger.log(Level.INFO, "[" + pluginName + "] Directory does not exists. Creating dirs.");
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

    /**
     * Save config file
    */
    public void saveConfig(){
        try {
            props.save();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "[" + pluginName + "] Error during file writing.");
        }
    }

    /**
     * Setup default config and save it.
    */
    public void setDefault(){
        props.setProperty("BACKEND", "type", "mysql");
        props.setProperty("BACKEND", "server", "localhost");
        props.setProperty("BACKEND", "database", "10_minecraft");
        props.setProperty("BACKEND", "user", "user");
        props.setProperty("BACKEND", "pass", "pass");
        props.setProperty("PERMISSIONS", "plugin", "permissionsex");
        props.setProperty("PLUGIN", "chatDisplay", "none");
        props.setProperty("PLUGIN", "broadcastSkillUp", "true");
        props.setProperty("PLUGIN", "baseXp", "100");
        props.setProperty("PLUGIN", "xpMultiplyer", "1.0");
        props.setProperty("PLUGIN", "flatRatePayout", "0.0");
        props.setProperty("PLUGIN", "switchFee", "500.0");
        props.setProperty("PLUGIN", "payday", "1");
        props.saveAs(confFile.toString());
    }

    
    /**
     * Get a property value
     * @param section the name of the section
     * @param key the name of the key
     * @return the value of the key or <code>null</code> if not present
    */   
    public String getProperty(String section, String key){
        return props.getProperty(section, key);
    }
 
    /**
     * Set a property value
     * @param section the name of the section
     * @param key the name of the key
     * @param key the name of the key
     * @return The message of the action
    */
    public String setProperty(String section, String key, String value){
        try {
            if(props.getProperty(section, key) != null){
            props.setProperty(section, key, value);
            props.save();
            return "Configuration node set";
            }
            else{
                logger.log(Level.WARNING, "[" + pluginName + "] Try to set invalid config node.");
                return "Try to set invalid config node.";
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "[" + pluginName + "] Error during file writing.");
            return "Error during file writing.";
        }
    }
    
    /**
     * Set a property value
     * @param section the name of the section
     * @return Hashmap of the propertys
    */
    public HashMap getSection(String section) {
        List<String> Properties = props.getProperties(section);
        HashMap<String,String> Section = new HashMap<String,String> ();
	for (String Property: Properties) {
            Section.put(Property,props.getProperty(section, Property));
        }
        return Section;
    }
}