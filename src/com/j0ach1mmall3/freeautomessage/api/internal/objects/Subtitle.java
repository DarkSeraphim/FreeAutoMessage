package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import com.j0ach1mmall3.freeautomessage.api.internal.api.ReflectionAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Created by j0ach1mmall3 on 1:49 19/08/2015 using IntelliJ IDEA.
 */
public class Subtitle {
    private Player player;
    private String message;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public Subtitle(Player player, String message, int fadeIn, int stay, int fadeOut) {
        this.player = player;
        this.message = message;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
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

    public void send() {
        message = PlaceholderAPI.setPlaceholders(player, message);
        try {
            Class<Enum> enumTitleAction = (Class<Enum>) ReflectionAPI.getNmsClass("PacketPlayOutTitle$EnumTitleAction");
            Constructor packetConstructor = ReflectionAPI.getNmsClass("PacketPlayOutTitle").getConstructor(enumTitleAction, ReflectionAPI.getNmsClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object titleSer = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\": \"" + message + "\"}");
            Object titlePacket = packetConstructor.newInstance(enumTitleAction.getEnumConstants()[1], titleSer, fadeIn, stay, fadeOut);
            ReflectionAPI.sendPacket(player, titlePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
