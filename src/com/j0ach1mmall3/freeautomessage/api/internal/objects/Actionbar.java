package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import com.j0ach1mmall3.freeautomessage.api.internal.api.ReflectionAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Created by j0ach1mmall3 on 1:15 19/08/2015 using IntelliJ IDEA.
 */
public class Actionbar {
    private Player player;
    private String message;

    public Actionbar(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void send() {
        this.message = PlaceholderAPI.setPlaceholders(player, message);
        try {
            Constructor packetConstructor = ReflectionAPI.getNmsClass("PacketPlayOutChat").getConstructor(ReflectionAPI.getNmsClass("IChatBaseComponent"), byte.class);
            Object baseComponent = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\": \"" + message + "\"}");
            Object packet = packetConstructor.newInstance(baseComponent, (byte) 2);
            ReflectionAPI.sendPacket(player, packet);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
