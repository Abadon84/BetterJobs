/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.abadon.bukkit.betterjobs ;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import de.abadon.bukkit.betterjobs.config.Properties;

/**
 *
 * @author code
 */
public class BetterJobs2 extends JavaPlugin {

    protected static final String configFile = "config.yml";
    protected static final Logger logger = Logger.getLogger("Minecraft");
    protected static  Properties conf;

    public BetterJobs2() {
        logger.log(Level.INFO, "[BetterJobs] BetterJobs plugin was Initialized.");
    }

    @Override
    public void onLoad() {
            conf = new Properties(getDataFolder());
            conf.loadConfig();
    }

    @Override
    public void onEnable() {
        logger.log(Level.INFO, "[BetterJobs] loaded");
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "[BetterJobs]disabled successfully.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(commandLabel.equalsIgnoreCase("bjobs"))
        {
           Player player = (Player)sender;
            String help = "Usage of BetterJobs:\n/bjobs - Display help\n/bjobs info - Display jobs\n/bjobs info <job> - Display job info\n/bjobs join <job> - Join a job\n/bjobs stats - Display job stats\n/bjobs stats <player> - Display job stats of player\n/bjobs del <player> - Delete the players job\n/bjobs set <job> <player> - Set the players job\n/bjobs reload - Reload Better Jobs";
                if (args.length != 0){
                    if(args[0].equalsIgnoreCase("info") && args.length == 1){
                        player.sendMessage("command for job list");
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
                        player.sendMessage("Plugin Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        player.sendMessage(conf.setProperty("PLUGIN", args[2], args[3]));
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args.length == 1){
                        HashMap Config = conf.getSection("DATABASE");
                        Set<String> Nodes = Config.keySet();
                        player.sendMessage("Plugin Configuration:");
                        for (String Node : Nodes) {
                            player.sendMessage(Node + ": " + Config.get(Node));
                        }
                    }
                    else if(args[0].equalsIgnoreCase("backend") && args[1].equalsIgnoreCase("set") && args.length == 4){
                        player.sendMessage(conf.setProperty("DATABASE", args[2], args[3]));
                    }
                    else if(args[0].equalsIgnoreCase("reload") && args.length == 1){
                        conf.loadConfig();
                        player.sendMessage("BetterJobs reloaded");
                    }
                    else{
                        messageSender(help,player);
                     }
                }
                else{
                        String testString = conf.getProperty("DATABASE", "database");
                        player.sendMessage(testString);
                        testString = conf.getProperty("PLUGIN", "switchFee");
                        player.sendMessage(testString);
                }
            }
            logger.log(Level.INFO, "[BetterJobs] version [{0}] get the command.", this.getDescription().getVersion());
            return true;
    }
    
    private void messageSender(String message, Player player){
        for (String msg: message.split("\\n")) {
            player.sendMessage(msg);
        } 
    }
}

   