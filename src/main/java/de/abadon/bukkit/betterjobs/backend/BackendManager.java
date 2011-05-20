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
import java.util.HashMap;

public class BackendManager {
    public static final Logger log = Logger.getLogger("Minecraft.BetterJobs");
    protected Backend backend;
    protected String backendName;
    public BackendManager(HashMap<String, String> backendConf){
        backendName = backendConf.get("type");
        if (backendName.equalsIgnoreCase("mysql")){
            try {
                log.info("[BetterJobs] Loading MySql backend...");
                backend = new MySqlBackend(backendConf.get("server"),backendConf.get("database"),backendConf.get("user"),backendConf.get("pass"));
                log.info(backend.connect());
            } catch (ClassNotFoundException ex) {
                log.warning("[BetterJobs] JBDC-Driver not found");
            }
        }
        else if (backendName.equalsIgnoreCase("sqlite")){
            log.info("[BetterJobs] Sqlite backend isnt implemented yet :P");
        }
        else{
            log.warning("[BetterJobs] Invalid backend type, please correct the config!");
        }
    }
}
