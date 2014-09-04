package com.connorlinfoot.playerchatcolor;

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
        console.sendMessage(ChatColor.GREEN + "=========== VERSION: 1.0 ===========");
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

        if( !( sender instanceof Player) ) {
            sender.sendMessage(Prefix + "PlayerChatColor must be ran as a player");
            return false;
        }

        Player p = (Player) sender;

        if( Permissions ){
            if( !p.hasPermission("chatcolor.use") ){
                sender.sendMessage(Prefix + "You dont have the permission \"chatcolor.use\"");
                return false;
            }
        }

        if( args.length == 1 ){

            if( args[0].equalsIgnoreCase("list") ){
                // List of all colors
                sender.sendMessage(Prefix + "===== ChatColor List =====");
                sender.sendMessage(Prefix + ChatColor.WHITE + "- white");
                sender.sendMessage(Prefix + ChatColor.GREEN + "- green");
                sender.sendMessage(Prefix + ChatColor.AQUA + "- aqua");
                sender.sendMessage(Prefix + ChatColor.RED + "- red");
                sender.sendMessage(Prefix + ChatColor.BLUE + "- blue");
                sender.sendMessage(Prefix + "More coming soon");
                return true;
            }

            if( args[0].equalsIgnoreCase("white") ){
                setPlayerChatColor(p, ChatColor.WHITE);
                sender.sendMessage(Prefix + "Your ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("green") ){
                setPlayerChatColor(p, ChatColor.GREEN);
                sender.sendMessage(Prefix + "Your ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("aqua") ){
                setPlayerChatColor(p, ChatColor.AQUA);
                sender.sendMessage(Prefix + "Your ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("red") ){
                setPlayerChatColor(p, ChatColor.RED);
                sender.sendMessage(Prefix + "Your ChatColor has been updated");
                return true;
            } else if( args[0].equalsIgnoreCase("blue") ){
                setPlayerChatColor(p, ChatColor.BLUE);
                sender.sendMessage(Prefix + "Your ChatColor has been updated");
                return true;
            }

        }

        sender.sendMessage(Prefix + ChatColor.RED + "Usage: /chatcolor <color/list>");
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