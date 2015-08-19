package com.j0ach1mmall3.freeautomessage;

import com.j0ach1mmall3.freeautomessage.api.internal.api.ReflectionAPI;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.Parsing;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.UpdateChecker;
import com.j0ach1mmall3.freeautomessage.commands.Commands;
import com.j0ach1mmall3.freeautomessage.config.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by j0ach1mmall3 on 17:08 18/08/2015 using IntelliJ IDEA.
 */
public class Main extends JavaPlugin {
    private final String BUKKIT_VERSION = Bukkit.getBukkitVersion().split("\\-")[0];
    private final String MINECRAFT_VERSION = ReflectionAPI.getVersion();
    private boolean bossBarAPI;
    public void onEnable() {
        new Config(this);
        if(Config.loggingLevel >= 2) General.sendColoredMessage(this, "You are running Bukkit version " + BUKKIT_VERSION + " (MC " + MINECRAFT_VERSION + ")", ChatColor.GOLD);
        if(Config.updateChecker) {
            if(UpdateChecker.checkUpdate(11191, getDescription().getVersion())) {
                if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "A new update is available!", ChatColor.GOLD);
                if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Version " + UpdateChecker.getVersion(11191) + " (Current: " + getDescription().getVersion() + ")", ChatColor.GOLD);
            } else {
                if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "You are up to date!", ChatColor.GREEN);
            }
        }
        PluginManager pm = getServer().getPluginManager();
        if(pm.isPluginEnabled("BossBarAPI")) {
            if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Successfully hooked into BossBarAPI for extended functionality", ChatColor.GREEN);
            bossBarAPI = true;
        } else {
            if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "BossBarAPI was not found! Bossbar Broadcasters will not work!", ChatColor.RED);
            bossBarAPI = false;
        }
        if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Loading configs...", ChatColor.GREEN);
        new Actionbar(this);
        new Bossbar(this);
        new Chat(this);
        new Signs(this);
        new Subtitle(this);
        new Tablist(this);
        new Title(this);
        if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Loaded all configs!", ChatColor.GREEN);
        if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Registering command...", ChatColor.GREEN);
        getCommand("FreeAutoMessage").setExecutor(new Commands(this));
        if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Registered command!", ChatColor.GREEN);
        if(Config.loggingLevel >= 1) General.sendColoredMessage(this, "Done!", ChatColor.GREEN);
    }

    public void onDisable() {

        Bukkit.getScheduler().cancelTasks(this);
    }

    public boolean verBiggerThan(int depth, int version) {
        return Parsing.parseString(BUKKIT_VERSION.split("\\.")[depth]) >= version;
    }

    public boolean getBossBarAPI() {
        return bossBarAPI;
    }

    public void reloadConfigs() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
