package com.connorlinfoot.playerchatcolor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class Main extends JavaPlugin implements Listener {
    private static Plugin instance;
    private static boolean Enabled;
    private static boolean Permissions;
    private static String Prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "ChatColor" + ChatColor.GRAY + "] " + ChatColor.RESET;

    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        Enabled = getConfig().getBoolean("Enabled");
        Permissions = getConfig().getBoolean("Use Permissions");

        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        console.sendMessage(ChatColor.GREEN + "========= PlayerChatColor! =========");
        console.sendMessage(ChatColor.GREEN + "=========== VERSION: 1.1 ===========");
        console.sendMessage(ChatColor.GREEN + "======== BY CONNOR LINFOOT! ========");
    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( !Enabled ){
            sender.sendMessage(Prefix + "PlayerChatColor is currently disabled");
            return false;
        }
        Player p = null;

        if( args.length == 2 ) {
            p = Bukkit.getPlayer(args[0]);
            if( Permissions ){
                if( sender instanceof Player ) {
                    Player pp = (Player) sender;
                    if (!pp.hasPermission("chatcolor.other")) {
                        sender.sendMessage(Prefix + "You dont have the permission \"chatcolor.other\"");
                        return false;
                    }
                }
            } else if( !sender.isOp() ){
                sender.sendMessage(Prefix + "Your not op");
                return false;
            }
        } else {
            if( !( sender instanceof Player) ) {
                sender.sendMessage(Prefix + "PlayerChatColor must be ran as a player");
                return false;
            }

            p = (Player) sender;

            if( Permissions ){
                if( !p.hasPermission("chatcolor.use") ){
                    sender.sendMessage(Prefix + "You dont have the permission \"chatcolor.use\"");
                    return false;
                }
            }
        }

        if( p == null ){
            sender.sendMessage(Prefix + "Player not found");
            return false;
        }

        if( args.length >= 1 ){

            if( args[0].equalsIgnoreCase("list") ){
                // List of all colors
                sender.sendMessage(Prefix + "===== ChatColor List =====");
                sender.sendMessage(Prefix + ChatColor.WHITE + "- white");
                sender.sendMessage(Prefix + ChatColor.GREEN + "- green");
                sender.sendMessage(Prefix + ChatColor.AQUA + "- aqua");
                sender.sendMessage(Prefix + ChatColor.RED + "- red");
                sender.sendMessage(Prefix + ChatColor.BLUE + "- blue");
                sender.sendMessage(Prefix + ChatColor.GOLD + "- gold");
                sender.sendMessage(Prefix + ChatColor.YELLOW + "- yellow");
                sender.sendMessage(Prefix + ChatColor.LIGHT_PURPLE + "- purple");
                sender.sendMessage(Prefix + "More coming soon");
                return true;
            }

            if( args[0].equalsIgnoreCase("white") ){
                setPlayerChatColor(p, ChatColor.WHITE);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("green") ){
                setPlayerChatColor(p, ChatColor.GREEN);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("aqua") ){
                setPlayerChatColor(p, ChatColor.AQUA);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("red") ){
                setPlayerChatColor(p, ChatColor.RED);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("blue") ){
                setPlayerChatColor(p, ChatColor.BLUE);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("gold") ){
                setPlayerChatColor(p, ChatColor.GOLD);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("yellow") ){
                setPlayerChatColor(p, ChatColor.YELLOW);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("purple") ){
                setPlayerChatColor(p, ChatColor.LIGHT_PURPLE);
                sender.sendMessage(Prefix + "ChatColor has been updated");
                return true;
            }

        }

        sender.sendMessage(Prefix + ChatColor.RED + "Usage: /chatcolor [player] <color/list>");
        return true;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setPlayerChatColor(Player player, ChatColor chatColor){
        String uuid = String.valueOf(player.getUniqueId());
        getInstance().getConfig().set("Data." + uuid + ".Color",chatColor);
        getInstance().saveConfig();
    }

    public static ChatColor getPlayerChatColor(Player player){
        String uuid = String.valueOf(player.getUniqueId());
        if( getInstance().getConfig().isSet("Data." + uuid + ".Color") ) {
            return (ChatColor) getInstance().getConfig().get("Data." + uuid + ".Color");
        } else {
            return ChatColor.WHITE;
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setMessage(getPlayerChatColor(e.getPlayer()) + e.getMessage());
    }

}