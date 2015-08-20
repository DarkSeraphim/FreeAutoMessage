package com.j0ach1mmall3.freeautomessage.api;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.Random;
import com.j0ach1mmall3.freeautomessage.api.internal.objects.JsonText;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by j0ach1mmall3 on 17:09 18/08/2015 using IntelliJ IDEA.
 */
public class ChatBroadcaster extends Broadcaster {
    private String identifier;
    private boolean random;
    private List<String> enabledWorlds;
    private int interval;
    private String permission;
    private List<String> messages;
    private boolean json;
    private int count = 0;

    public ChatBroadcaster(String identifier, boolean random, List<String> enabledWorlds, int interval, String permission, List<String> messages, boolean json) {
        this.identifier = identifier;
        this.random = random;
        this.enabledWorlds = enabledWorlds;
        this.interval = interval;
        this.permission = permission;
        this.messages = messages;
        this.json = json;
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
            if(p.hasPermission(permission) && enabledWorlds.contains(p.getWorld().getName())) sendMessage(p, messages.get(id));
        }
    }

    private void sendMessage(Player p, String message) {
        String[] parts = message.split("\\|");
        for(String s : parts) {
            if(json) {
                new JsonText(p, s).send();
            } else {
                p.sendMessage(PlaceholderAPI.setPlaceholders(p, s));
            }
        }
    }
}
