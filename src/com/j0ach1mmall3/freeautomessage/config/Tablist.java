package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.TablistBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 3:47 19/08/2015 using IntelliJ IDEA.
 */
public class Tablist {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private boolean enabled;
    private List<TablistBroadcaster> broadcasters;
    public Tablist(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("tablist.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        if(enabled && !plugin.verBiggerThan(1, 8)){
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 1) General.sendColoredMessage(plugin, "It seems that Tablist Broadcasting is enabled in the config, however the server is running 1.7 or lower! Fixing that for you :)", ChatColor.RED);
            enabled = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(TablistBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Tablist messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Tablist config successfully loaded!", ChatColor.GREEN);
    }

    private List<TablistBroadcaster> getBroadcasters() {
        List<TablistBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("TablistBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private TablistBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "TablistBroadcasters." + identifier + ".";
        return new TablistBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "EnabledWorlds"),
                config.getInt(path + "Interval"),
                config.getString(path + "Permission"),
                config.getStringList(path + "Messages")
        );
    }
}
