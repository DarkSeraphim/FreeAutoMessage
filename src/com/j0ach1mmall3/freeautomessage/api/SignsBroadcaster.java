package com.j0ach1mmall3.freeautomessage.api;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.Parsing;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.Random;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.List;

/**
 * Created by j0ach1mmall3 on 2:44 19/08/2015 using IntelliJ IDEA.
 */
public class SignsBroadcaster extends Broadcaster {
    private String identifier;
    private boolean random;
    private List<String> signs;
    private int interval;
    private List<String> messages;
    private int count = 0;

    public SignsBroadcaster(String identifier, boolean random, List<String> signs, int interval, List<String> messages) {
        this.identifier = identifier;
        this.random = random;
        this.signs = signs;
        this.interval = interval;
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

    public List<String> getSigns() {
        return signs;
    }

    public void setSigns(List<String> signs) {
        this.signs = signs;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
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
        for(String s : signs) {
            Block b = getWorld(s).getBlockAt(deserializeLocation(s));
            if(b.getState() instanceof Sign) {
                Sign sign = (Sign) b.getState();
                for(int i=0;i<4;i++) {
                    sign.setLine(i, "");
                }
                setSign(sign, messages.get(id));
            }
        }
    }

    private Location deserializeLocation(String s) {
        String[] parts = s.split("/");
        return new Location(Bukkit.getWorld(parts[0]), Parsing.parseString(parts[1]), Parsing.parseString(parts[2]), Parsing.parseString(parts[3]));
    }

    private World getWorld(String s) {
        String[] parts = s.split("/");
        return Bukkit.getWorld(parts[0]);
    }

    private void setSign(Sign sign, String message) {
        String[] parts = message.split("\\|");
        int a = parts.length;
        if(parts.length > 4) a = 4;
        for(int i=0;i<a;i++) {
            sign.setLine(i, PlaceholderAPI.setPlaceholders(null, parts[i]));
            sign.update();
        }
    }
}
