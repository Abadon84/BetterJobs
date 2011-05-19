/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.bukkit.betterjobs.backend;

/**
 *
 * @author Abadon
 */

import java.io.*;
import java.sql.*;

public class MySqlBackend extends Backend{
    protected Connection con = null;
    public MySqlBackend(String server, String database, String user, String pass) throws ClassNotFoundException, SQLException{
        Class.forName( "com.mysql.jdbc.Driver" );
        con = DriverManager.getConnection( "jdbc:mysql://" + server + "/" + database, user, pass );
    }
}
