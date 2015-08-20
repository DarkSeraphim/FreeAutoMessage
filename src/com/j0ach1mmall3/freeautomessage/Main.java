package com.j0ach1mmall3.freeautomessage;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.ReflectionAPI;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.General;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.Parsing;
import com.j0ach1mmall3.freeautomessage.api.internal.objects.UpdateChecker;
import com.j0ach1mmall3.freeautomessage.commands.Commands;
import com.j0ach1mmall3.freeautomessage.config.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by j0ach1mmall3 on 17:08 18/08/2015 using IntelliJ IDEA.
 */
public class Main extends JavaPlugin {
    private final String BUKKIT_VERSION = Bukkit.getBukkitVersion().split("\\-")[0];
    private final String MINECRAFT_VERSION = ReflectionAPI.getVersion();
    private boolean bossBarAPI;
    private Bossbar bossBar;
    private Config config;
    public void onEnable() {
        this.config = new Config(this);
        config.load();
        if(config.getLoggingLevel() >= 2) General.sendColoredMessage(this, "You are running Bukkit version " + BUKKIT_VERSION + " (MC " + MINECRAFT_VERSION + ")", ChatColor.GOLD);
        if(config.getUpdateChecker()) {
            UpdateChecker checker = new UpdateChecker(11191, getDescription().getVersion());
            if(checker.checkUpdate()) {
                if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "A new update is available!", ChatColor.GOLD);
                if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Version " + checker.getVersion() + " (Current: " + getDescription().getVersion() + ")", ChatColor.GOLD);
            } else {
                if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "You are up to date!", ChatColor.GREEN);
            }
        }
        PluginManager pm = getServer().getPluginManager();
        if(pm.isPluginEnabled("BossBarAPI")) {
            if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Successfully hooked into BossBarAPI for extended functionality", ChatColor.GREEN);
            bossBarAPI = true;
        } else {
            if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "BossBarAPI was not found! Bossbar Broadcasters will not work!", ChatColor.RED);
            bossBarAPI = false;
        }
        if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Loading configs...", ChatColor.GREEN);
        new Actionbar(this);
        this.bossBar = new Bossbar(this);
        new Chat(this);
        new Signs(this);
        new Subtitle(this);
        new Tablist(this);
        new Title(this);
        if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Loaded all configs!", ChatColor.GREEN);
        if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Registering command...", ChatColor.GREEN);
        getCommand("FreeAutoMessage").setExecutor(new Commands(this));
        if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Registered command!", ChatColor.GREEN);
        if(config.getLoggingLevel() >= 1) General.sendColoredMessage(this, "Done!", ChatColor.GREEN);
    }

    public void onDisable() {
        if(bossBar.isEnabled()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                org.inventivetalent.bossbar.BossBarAPI.removeBar(p);
            }
        }
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
