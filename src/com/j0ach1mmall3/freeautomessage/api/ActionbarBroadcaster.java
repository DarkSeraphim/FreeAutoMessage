package com.j0ach1mmall3.freeautomessage.api;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.Random;
import com.j0ach1mmall3.freeautomessage.api.internal.objects.Actionbar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by j0ach1mmall3 on 17:47 18/08/2015 using IntelliJ IDEA.
 */
public class ActionbarBroadcaster extends Broadcaster {
    private String identifier;
    private Boolean random;
    private List<String> enabledWorlds;
    private int interval;
    private String permission;
    private List<String> messages;
    private int count = 0;

    public ActionbarBroadcaster(String identifier, Boolean random, List<String> enabledWorlds, int interval, String permission, List<String> messages) {
        this.identifier = identifier;
        this.random = random;
        this.enabledWorlds = enabledWorlds;
        this.interval = interval;
        this.permission = permission;
        this.messages = messages;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public List<String> getEnabledWorlds() {
        return enabledWorlds;
    }

    public void setEnabledWorlds(List<String> enabledWorlds) {
        this.enabledWorlds = enabledWorlds;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void broadcast() {
        int id;
        if(random) {
            id = Random.getInt(messages.size());
        } else {
            if(count >= messages.size()) {
                count = 0;
            }
            id = count;
            count++;
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission(permission) && enabledWorlds.contains(p.getWorld().getName())) new Actionbar(p, messages.get(id)).send();
        }
    }
}
