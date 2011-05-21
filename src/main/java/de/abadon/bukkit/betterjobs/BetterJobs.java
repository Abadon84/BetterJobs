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

package de.abadon.bukkit.betterjobs ;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import de.abadon.bukkit.betterjobs.config.Properties;
import de.abadon.bukkit.betterjobs.backend.BackendManager;

/**
 *
 * @author code
 */
public class BetterJobs extends JavaPlugin {

    protected static final String configFile = "config.yml";
    protected static Properties conf;
    protected static BackendManager backend; 
    public static final Logger log = Logger.getLogger("Minecraft.BetterJobs");

    public BetterJobs() {
        log.info("[BetterJobs] BetterJobs plugin was Initialized.");
    }

    @Override
    public void onLoad() {
        conf = new Properties(getDataFolder());
        conf.loadConfig();
        backend = new BackendManager(conf.getSection("BACKEND"));
    }

    public void onEnable() {
        log.info("[BetterJobs] plugin enabled");
    }

    public void onDisable() {
    	log.info("[BetterJobs] plugin disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(commandLabel.equalsIgnoreCase("bjobs"))
        {
           Player player = (Player)sender;
            String help = "/bjobs - Display help\n/bjobs info - Display jobs\n/bjobs info <job> - Display job info\n/bjobs join <job> - Join a job\n/bjobs stats - Display job stats\n/bjobs stats <player> - Display job stats of player\n/bjobs del <player> - Delete the players job\n/bjobs set <job> <player> - Set the players job\n/bjobs reload - Reload Better Jobs";
                if (args.length != 0){
                    if(args[0].equalsIgnoreCase("info") && args.length == 1){
                        player.sendMessage(ChatColor.GREEN + "Following Jobs are available:");
                        for(Job job : backend.getJobs()){
                            if (job != null){
                                player.sendMessage(job.name + ": " + job.description);
                            }
                        }
                    }
                    else if(args[0].equalsIgnoreCase("info") && args.length == 2){
                        player.sendMessage("command for job info " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("join") && args.length == 2){
                         player.sendMessage("command for join job " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("stats") && args.length == 1){
                        player.sendMessage("command for your stats ");
                    }
                    else if(args[0].equalsIgnoreCase("stats") && args.length == 2){
                        player.sendMessage("command for stats of " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("del") && args.length == 2){
                        player.sendMessage("command for deleting " + args[1]);
                    }
                    else if(args[0].equalsIgnoreCase("set") && args.length == 3){
                        player.sendMessage("command for setting job " + args[1] + " for " + args[2]);
                    }
                    else if(args[0].equalsIgnoreCase("config") && args.length == 1){
                        HashMap Config = conf.getSection("PLUGIN");
                        Set<String> Nodes = Config.keySet();
                        player.sendMessage(ChatColor.GREEN + "Plugin Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        player.sendMessage(ChatColor.RED + conf.setProperty("PLUGIN", args[2], args[3]));
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args.length == 1){
                        HashMap Config = conf.getSection("BACKEND");
                        Set<String> Nodes = Config.keySet();
                        player.sendMessage(ChatColor.GREEN + "Backend Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        player.sendMessage(ChatColor.RED + conf.setProperty("BACKEND", args[2], args[3]));
                    }
                    else if(args[0].equalsIgnoreCase("reload") && args.length == 1){
                        conf.loadConfig();
                        player.sendMessage(ChatColor.GREEN + "BetterJobs reloaded");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "Usage of BetterJobs:");
                        messageSender(help,player);
                     }
                }
                else{
                    player.sendMessage(ChatColor.GREEN + "Usage of BetterJobs:");
                    messageSender(help,player);
                }
            }
            return true;
    }
    
    private void messageSender(String message, Player player){
        for (String msg: message.split("\\n")) {
            player.sendMessage(msg);
        }
    }
}

   