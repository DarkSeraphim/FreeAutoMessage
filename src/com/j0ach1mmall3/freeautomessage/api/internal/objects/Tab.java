package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import com.j0ach1mmall3.freeautomessage.api.internal.methods.ReflectionAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by j0ach1mmall3 on 3:45 19/08/2015 using IntelliJ IDEA.
 */
public class Tab {
    private Player player;
    private String header;
    private String footer;

    public Tab(Player player, String header, String footer) {
        this.player = player;
        this.header = header;
        this.footer = footer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void send() {
        this.header = PlaceholderAPI.setPlaceholders(player, header);
        this.footer = PlaceholderAPI.setPlaceholders(player, footer);

        try {
            Constructor packetTabConstructor = ReflectionAPI.getNmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(ReflectionAPI.getNmsClass("IChatBaseComponent"));
            Class serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
            Object headerPacket = packetTabConstructor.newInstance(serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + header + "\"}"));
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + footer + "\"}"));
            ReflectionAPI.sendPacket(player, headerPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
