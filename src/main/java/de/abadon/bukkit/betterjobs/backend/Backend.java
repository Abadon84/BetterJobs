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
import java.util.logging.Logger;
import de.abadon.bukkit.betterjobs.Job;

public class Backend {
    public static final Logger log = Logger.getLogger("Minecraft.BetterJobs");
    Job Jobs[] = null;   
    
    public Backend(){
        
    }
    
    public boolean connect(){
        return false;
    }
        
    public boolean disconnect(){
        return false;
    }
    
    public boolean reload(){
        Jobs = null;
        return load();
    }
    
    public boolean load(){
        return false;
    }
        
    public boolean setUp(){
        return false;
    }
    
    public boolean getJobs(){
        return false;
    }
    
    public boolean getPlayers(){
        return false;
    }
    
    public boolean savePlayer(){
        return false;
    }
    
    public boolean saveJob(){
        return false;
    }
}
