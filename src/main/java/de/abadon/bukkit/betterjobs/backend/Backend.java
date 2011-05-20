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

import java.util.HashMap;

public class Backend {
    
    HashMap BlocksBreakPlayer = null;
    HashMap BlocksBreakEnv = null;
    HashMap BlocksCreate = null;
    HashMap Kills = null;
    String  Name = null;
    String  Description = null;
    String  Prefix = null;
    String  Suffix = null;
    String  XPGain = null;
    String  MoneyGain = null;
    HashMap Jobs = null;
    HashMap Players = null;
    
    
    public Backend(){
        
    }
    
    public String connect(){
        return null;
    }
        
    public String disconnect(){
        return null;
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
