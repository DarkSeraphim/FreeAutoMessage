package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.SignsBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 2:58 19/08/2015 using IntelliJ IDEA.
 */
public class Signs {
    private static Main plugin;
    private static Config customConfig;
    private static FileConfiguration config;
    private static Boolean enabled;
    private static List<SignsBroadcaster> broadcasters;
    public Signs(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("signs.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        broadcasters = getBroadcasters();
        if(enabled) {
            for(SignsBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Signs messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Signs config successfully loaded!", ChatColor.GREEN);
    }

    private static List<SignsBroadcaster> getBroadcasters() {
        List<SignsBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("SignsBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private static SignsBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "SignsBroadcasters." + identifier + ".";
        return new SignsBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "Signs"),
                config.getInt(path + "Interval"),
                config.getStringList(path + "Messages")
        );
    }

    public static Boolean isSignsBroadcaster(String identifier) {
        return customConfig.getKeys("SignsBroadcasters").contains(identifier);
    }

    public static void addSign(String identifier, Location l) {
        String path = "SignsBroadcasters." + identifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        signs.add(serializeLocation(l));
        config.set("SignsBroadcasters." + identifier + ".Signs", signs);
        customConfig.saveConfig(config);
    }

    public static void removeSign(String identifier, Location l) {
        String path = "SignsBroadcasters." + identifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        signs.remove(serializeLocation(l));
        config.set("SignsBroadcasters." + identifier + ".Signs", signs);
        customConfig.saveConfig(config);
    }

    public static Boolean hasSign(String identifier, Location l) {
        String path = "SignsBroadcasters." + identifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        return signs.contains(serializeLocation(l));
    }

    private static String serializeLocation(Location l) {
        return l.getWorld().getName() + "/" + String.valueOf(l.getBlockX()) + "/" + String.valueOf(l.getBlockY()) + "/" + String.valueOf(l.getBlockZ());
    }
}
