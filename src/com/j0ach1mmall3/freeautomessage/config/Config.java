package com.j0ach1mmall3.freeautomessage.config;

import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by j0ach1mmall3 on 1:40 19/08/2015 using IntelliJ IDEA.
 */
public class Config {
    private Main plugin;
    private com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config customConfig;
    private FileConfiguration config;
    public static boolean updateChecker;
    public static int loggingLevel;
    public static String noPermissionMessage;
    public Config(Main plugin) {
        this.plugin = plugin;
        this.customConfig = new com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config("config.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        loggingLevel = config.getInt("LoggingLevel");
        updateChecker = config.getBoolean("UpdateChecker");
        if(loggingLevel >= 2 && !updateChecker) General.sendColoredMessage(plugin, "Update Checking is not enabled! You will not receive console notifications!", ChatColor.GOLD);
        noPermissionMessage = config.getString("NoPermissionMessage");
        if(loggingLevel >= 2) General.sendColoredMessage(plugin, "Main config successfully loaded!", ChatColor.GREEN);
    }
}
