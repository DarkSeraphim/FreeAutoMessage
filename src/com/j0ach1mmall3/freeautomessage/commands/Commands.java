package com.j0ach1mmall3.freeautomessage.commands;

import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.config.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by j0ach1mmall3 on 3:11 19/08/2015 using IntelliJ IDEA.
 */
public class Commands implements CommandExecutor {
    private Main plugin;
    private Config config;
    public Commands(Main plugin) {
        this.plugin = plugin;
        this.config = new Config(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("FreeAutoMessage")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /fam reload, /fam addsign, /fam removesign");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("fam.reload")) {
                    if(sender instanceof Player) sender.sendMessage(PlaceholderAPI.setPlaceholders(((Player) sender), config.getNoPermissionMessage()));
                    return true;
                }
                Bukkit.getScheduler().cancelTasks(plugin);
                new Config(plugin);
                new Actionbar(plugin);
                new Bossbar(plugin);
                new Chat(plugin);
                new Signs(plugin);
                new Subtitle(plugin);
                new Tablist(plugin);
                new Title(plugin);
                sender.sendMessage(ChatColor.GREEN + "Reloaded Configs!");
                return true;
            }
            if (args[0].equalsIgnoreCase("addsign")) {
                if (!sender.hasPermission("fam.addsign")) {
                    if(sender instanceof Player) sender.sendMessage(PlaceholderAPI.setPlaceholders(((Player) sender), config.getNoPermissionMessage()));
                    return true;
                }
                if(!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "You need to be a player to execute this command!");
                    return true;
                }
                Player p = (Player) sender;
                Block b = p.getTargetBlock((Set<Material>) null, 5);
                if(!(b.getState() instanceof Sign)) {
                    p.sendMessage(ChatColor.RED + "You need to be looking at a placed Sign!");
                    return true;
                }
                if (args.length < 2) {
                    p.sendMessage(ChatColor.RED + "You need to specify a Broadcaster you want to add this Sign to!");
                    return true;
                }
                com.j0ach1mmall3.freeautomessage.api.Sign sign = new com.j0ach1mmall3.freeautomessage.api.Sign(plugin, args[1], b.getLocation());
                if(!sign.isSignsBroadcaster()) {
                    p.sendMessage(ChatColor.RED + args[1] + " isn't a valid Signs Broadcaster!");
                    return true;
                }
                if(sign.exists()) {
                    p.sendMessage(ChatColor.RED + args[1] + " already has this Sign!");
                    return true;
                }
                sign.add();
                p.sendMessage(ChatColor.GREEN + "Successfully added Sign to " + args[1]);
                p.sendMessage(ChatColor.GOLD + "Use /fam reload to apply the changes");
                return true;
            }
            if (args[0].equalsIgnoreCase("removesign")) {
                if (!sender.hasPermission("fam.removesign")) {
                    if(sender instanceof Player) sender.sendMessage(PlaceholderAPI.setPlaceholders(((Player) sender), config.getNoPermissionMessage()));
                    return true;
                }
                if(!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "You need to be a player to execute this command!");
                    return true;
                }
                Player p = (Player) sender;
                Block b = p.getTargetBlock((Set<Material>) null, 5);
                if(!(b.getState() instanceof Sign)) {
                    p.sendMessage(ChatColor.RED + "You need to be looking at a placed Sign!");
                    return true;
                }
                if (args.length < 2) {
                    p.sendMessage(ChatColor.RED + "You need to specify a Broadcaster you want to remove this Sign from!");
                    return true;
                }
                com.j0ach1mmall3.freeautomessage.api.Sign sign = new com.j0ach1mmall3.freeautomessage.api.Sign(plugin, args[1], b.getLocation());
                if(!sign.isSignsBroadcaster()) {
                    p.sendMessage(ChatColor.RED + args[1] + " isn't a valid Signs Broadcaster!");
                    return true;
                }
                if(!sign.exists()) {
                    p.sendMessage(ChatColor.RED + args[1] + " doesn't have this Sign!");
                    return true;
                }
                sign.remove();
                p.sendMessage(ChatColor.GREEN + "Successfully removed Sign from " + args[1]);
                p.sendMessage(ChatColor.GOLD + "Use /fam reload to apply the changes");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Usage: /fam reload, /fam addsign, /fam removesign");
            return true;
        }
        return false;
    }
}
