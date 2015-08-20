package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.BroadcastScheduler;
import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.SignsBroadcaster;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j0ach1mmall3 on 2:58 19/08/2015 using IntelliJ IDEA.
 */
public class Signs {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private boolean enabled;
    private List<SignsBroadcaster> broadcasters;
    public Signs(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new Config("signs.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        enabled = config.getBoolean("Enabled");
        broadcasters = getBroadcasters();
        com.j0ach1mmall3.freeautomessage.config.Config pluginConfig = new com.j0ach1mmall3.freeautomessage.config.Config(plugin);
        if(enabled) {
            for(SignsBroadcaster broadcaster : broadcasters) {
                new BroadcastScheduler(broadcaster).runTaskTimer(plugin, 0, broadcaster.getInterval());
            }
            if(pluginConfig.getLoggingLevel() >= 2) General.sendColoredMessage(plugin, "Started broadcasting Signs messages!", ChatColor.GREEN);
        }
        if(pluginConfig.getLoggingLevel() >= 2) General.sendColoredMessage(plugin, "Signs config successfully loaded!", ChatColor.GREEN);
    }

    private List<SignsBroadcaster> getBroadcasters() {
        List<SignsBroadcaster> broadcasters = new ArrayList<>();
        for(String s : customConfig.getKeys("SignsBroadcasters")) {
            broadcasters.add(getBroadcasterByIdentifier(s));
        }
        return broadcasters;
    }

    private SignsBroadcaster getBroadcasterByIdentifier(String identifier) {
        String path = "SignsBroadcasters." + identifier + ".";
        return new SignsBroadcaster(
                identifier,
                config.getBoolean(path + "Random"),
                config.getStringList(path + "Signs"),
                config.getInt(path + "Interval"),
                config.getStringList(path + "Messages")
        );
    }
}
