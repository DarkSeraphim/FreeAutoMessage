package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.ActionbarBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 1:51 19/08/2015 using IntelliJ IDEA.
 */
public class Actionbar {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private boolean enabled;
    private List<ActionbarBroadcaster> broadcasters;
    public Actionbar(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("actionbar.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        com.j0ach1mmall3.freeautomessage.config.Config pluginConfig = new com.j0ach1mmall3.freeautomessage.config.Config(plugin);
        if(enabled && !plugin.verBiggerThan(1, 8)){
            if(pluginConfig.getLoggingLevel() >= 1) General.sendColoredMessage(plugin, "It seems that Actionbar Broadcasting is enabled in the config, however the server is running 1.7 or lower! Fixing that for you :)", ChatColor.RED);
            enabled = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(ActionbarBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(pluginConfig.getLoggingLevel() >= 2) General.sendColoredMessage(plugin, "Started broadcasting Actionbar messages!", ChatColor.GREEN);
        }
        if(pluginConfig.getLoggingLevel() >= 2) General.sendColoredMessage(plugin, "Actionbar config successfully loaded!", ChatColor.GREEN);
    }

    private List<ActionbarBroadcaster> getBroadcasters() {
        List<ActionbarBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("ActionbarBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private ActionbarBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "ActionbarBroadcasters." + identifier + ".";
        return new ActionbarBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "EnabledWorlds"),
                config.getInt(path + "Interval"),
                config.getString(path + "Permission"),
                config.getStringList(path + "Messages")
        );
    }
}
