package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.TitleBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 3:03 19/08/2015 using IntelliJ IDEA.
 */
public class Title {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private boolean enabled;
    private List<TitleBroadcaster> broadcasters;
    public Title(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("title.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        if(enabled && !plugin.verBiggerThan(1, 8)){
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 1) General.sendColoredMessage(plugin, "It seems that Title Broadcasting is enabled in the config, however the server is running 1.7 or lower! Fixing that for you :)", ChatColor.RED);
            enabled = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(TitleBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Title messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Title config successfully loaded!", ChatColor.GREEN);
    }

    private List<TitleBroadcaster> getBroadcasters() {
        List<TitleBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("TitleBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private TitleBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "TitleBroadcasters." + identifier + ".";
        return new TitleBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "EnabledWorlds"),
                config.getInt(path + "Interval"),
                config.getString(path + "Permission"),
                config.getInt(path + "FadeIn"),
                config.getInt(path + "Stay"),
                config.getInt(path + "FadeOut"),
                config.getStringList(path + "Messages")
        );
    }
}
