package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.SubtitleBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 3:01 19/08/2015 using IntelliJ IDEA.
 */
public class Subtitle {
    private Main plugin;
    private static Config customConfig;
    private static FileConfiguration config;
    private static Boolean enabled;
    private static List<SubtitleBroadcaster> broadcasters;
    public Subtitle(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("subtitle.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        if(enabled && !Main.verBiggerThan(1, 8)){
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 1) General.sendColoredMessage(plugin, "It seems that Subtitle Broadcasting is enabled in the config, however the server is running 1.7 or lower! Fixing that for you :)", ChatColor.RED);
            enabled = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(SubtitleBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Subtitle messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Subtitle config successfully loaded!", ChatColor.GREEN);
    }

    private static List<SubtitleBroadcaster> getBroadcasters() {
        List<SubtitleBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("SubtitleBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private static SubtitleBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "SubtitleBroadcasters." + identifier + ".";
        return new SubtitleBroadcaster(
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
