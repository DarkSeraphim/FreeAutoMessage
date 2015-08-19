package com.j0ach1mmall3.freeautomessage.api;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.Random;
import com.j0ach1mmall3.freeautomessage.api.internal.objects.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by j0ach1mmall3 on 17:57 18/08/2015 using IntelliJ IDEA.
 */
public class TitleBroadcaster extends Broadcaster {
    private String identifier;
    private boolean random;
    private List<String> enabledWorlds;
    private int interval;
    private String permission;
    private int fadeIn;
    private int stay;
    private int fadeOut;
    private List<String> messages;
    private int count = 0;

    public TitleBroadcaster(String identifier, boolean random, List<String> enabledWorlds, int interval, String permission, int fadeIn, int stay, int fadeOut, List<String> messages) {
        this.identifier = identifier;
        this.random = random;
        this.enabledWorlds = enabledWorlds;
        this.interval = interval;
        this.permission = permission;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
        this.messages = messages;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean getRandom() {
        return random;
    }

    public void setRandom(boolean random) {
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

    public int getFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
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
            if(p.hasPermission(permission) && enabledWorlds.contains(p.getWorld().getName())) new Title(p, messages.get(id), fadeIn, stay, fadeOut).send();
        }
    }
}