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
    public String connect(){
        try {
            con = DriverManager.getConnection( "jdbc:mysql://" + server + "/" + database, user, pass );
            return "[BetterJobs] Connected to MySql";
        } catch (SQLException ex) {
            return "[BetterJobs] Failed to setup MySql Connection: " + ex;
        }
    }
    
    @Override       
    public String disconnect(){
        try {
            con.close();
            con = null;
            return "[BetterJobs] Disconnected from MySql";
        } catch (SQLException ex) {
            return "[BetterJobs] Failed to disconnect from: " + ex;
        }
    }
     
    public String reload(){
        return null;
    }
    
    public String setUp(){
        return null;
    }
    
    public HashMap getJobs(){
        return null;
    }
    
    public HashMap getPlayers(){
        return null;
    }
    
    public boolean savePlayer(){
        return false;
    }
    
    public boolean saveJob(){
        return false;
    }
}
