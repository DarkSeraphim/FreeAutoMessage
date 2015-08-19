package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.ChatBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 2:29 19/08/2015 using IntelliJ IDEA.
 */
public class Chat {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private boolean enabled;
    public static boolean json;
    private List<ChatBroadcaster> broadcasters;
    public Chat(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("chat.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        json = config.getBoolean("Json");
        if(enabled && !plugin.verBiggerThan(1, 7)){
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 1) General.sendColoredMessage(plugin, "It seems that Json Chat formatting is enabled in the config, however the server is running 1.6 or lower! Fixing that for you :)", ChatColor.RED);
            json = false;
        }
        broadcasters = getBroadcasters();
        if(enabled) {
            for(ChatBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Started broadcasting Chat messages!", ChatColor.GREEN);
        }
        if(com.j0ach1mmall3.freeautomessage.config.Config.loggingLevel >= 2) General.sendColoredMessage(plugin, "Chat config successfully loaded!", ChatColor.GREEN);
    }

    private List<ChatBroadcaster> getBroadcasters() {
        List<ChatBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("ChatBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private ChatBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "ChatBroadcasters." + identifier + ".";
        return new ChatBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "EnabledWorlds"),
                config.getInt(path + "Interval"),
                config.getString(path + "Permission"),
                config.getStringList(path + "Messages")
        );
    }
}
