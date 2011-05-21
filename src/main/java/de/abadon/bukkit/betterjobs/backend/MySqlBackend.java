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

package de.abadon.bukkit.betterjobs.backend;

/**
 *
 * @author Abadon
 */

import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlBackend extends Backend{
    protected Connection con = null;
    protected String server;
    protected String database;
    protected String user;
    protected String pass;
    public MySqlBackend(String dbserver, String db, String dbuser, String dbpass) throws ClassNotFoundException{
        Class.forName( "com.mysql.jdbc.Driver" );
        server = dbserver;
        database = db;
        user = dbuser;
        pass = dbpass;
    }
    
    @Override
    public boolean connect(){
        try {
            con = DriverManager.getConnection( "jdbc:mysql://" + server + "/" + database, user, pass );
            log.info("[BetterJobs] Connected to MySql");
            if(checkTable("bjobs_jobs") && checkTable("bjobs_entitys") && checkTable("bjobs_employees")){
                return true; 
            }
            else{
                if(setUp()){
                    return true; 
                }
                else{
                    return false; 
                }
            }
        } catch (SQLException ex) {
            log.warning("[BetterJobs] Failed to setup MySql Connection: " + ex);
            return false; 
        }
    }
    
    @Override       
    public boolean disconnect(){
        try {
            con.close();
            con = null;
            log.info("[BetterJobs] Disconnected from MySql");
            return true;
        } catch (SQLException ex) {
            log.warning("[BetterJobs] Failed to disconnect from: " + ex);
            return false;
        }
    }
    
    public boolean setUp(){
        try {
            Statement st = con.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS `bjobs_jobs` (`id` int(11) NOT NULL auto_increment,`name` varchar(32) NOT NULL,`description` varchar(320) NOT NULL,`prefix` varchar(16) NOT NULL,`suffix` varchar(16) NOT NULL,`moneygain` float NOT NULL default '0.1',`xpgain` float NOT NULL default '0.1',`flatrate` float NOT NULL default '0',PRIMARY KEY  (`id`)) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS `bjobs_entitys` (`id` int(11) NOT NULL auto_increment,`type` int(1) NOT NULL,`name` varchar(16) NOT NULL,`job` int(11) NOT NULL,`value` double NOT NULL,PRIMARY KEY  (`id`)) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS `bjobs_employees` (`id` int(11) NOT NULL auto_increment,`player` varchar(32) NOT NULL,`job` int(11) NOT NULL,`payout` double NOT NULL,PRIMARY KEY  (`id`)) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;");
            log.info("[BetterJobs] Created Default-Tables");
            return true;
        } catch (SQLException ex) {
            log.warning("[BetterJobs] Can't create tables. " + ex);
            return false;
        }
    }
    
    public boolean load(){
        try {
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT * from `bjob_jobs`;");
            int jobCount = res.getFetchSize();
            log.warning("[BetterJobs] Loaded " + jobCount + " jobs");
            if(res.next())
            {
                //job = new Job(res.getString(3), res.getInt(1), res.getInt(2), plugin, player);
            }
        return true;
        } catch (SQLException ex) {
            log.warning("[BetterJobs] Can't load jobs. " + ex);
            return false;
        }
    }
    
    public boolean getPlayer(){
        return false;
    }
    
    public boolean savePlayer(){
        return false;
    }
    
    public boolean saveJob(){
        return false;
    }
    
    public boolean checkTable(String table){
        try {
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SHOW TABLES LIKE '" + table + "';");
            if(res.first()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            log.warning("[BetterJobs] Failed to create tables" + ex);
            return false;        
        }
            
    }
}
