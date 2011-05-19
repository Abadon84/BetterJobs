/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.bukkit.betterjobs.backend;

/**
 *
 * @author Abadon
 */

import java.sql.*;
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
}
