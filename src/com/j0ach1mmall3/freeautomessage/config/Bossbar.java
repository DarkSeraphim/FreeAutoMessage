package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.BossbarBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 2:17 19/08/2015 using IntelliJ IDEA.
 */
public class Bossbar {
    private Main plugin;
    private static Config customConfig;
    private static FileConfiguration config;
    public static Boolean enabled;
    private static List<BossbarBroadcaster> broadcasters;
    public Bossbar(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("bossbar.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        if(enabled && !Main.BossBarAPI){
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 1) General.sendColoredMessage(plugin, "It seems that Actionbar Broadcasting is enabled in the config, however the server is running 1.7 or lower! Fixing that for you :)", ChatColor.RED);
            enabled = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                BossbarBroadcaster.removeBar(p);
            }
            for(BossbarBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Bossbar messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Bossbar config successfully loaded!", ChatColor.GREEN);
    }

    private static List<BossbarBroadcaster> getBroadcasters() {
        List<BossbarBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("BossbarBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private static BossbarBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "BossbarBroadcasters." + identifier + ".";
        return new BossbarBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "EnabledWorlds"),
                config.getInt(path + "Interval"),
                config.getString(path + "Permission"),
                config.getStringList(path + "Messages")
        );
    }
}
