package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import com.j0ach1mmall3.freeautomessage.api.internal.api.ReflectionAPI;
import com.j0ach1mmall3.freeautomessage.api.internal.methods.Parsing;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Created by j0ach1mmall3 on 4:46 19/08/2015 using IntelliJ IDEA.
 */
public class JsonText {
    private Player player;
    private String json;

    public JsonText(Player player, String json) {
        this.player = player;
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void send() {
        this.json = PlaceholderAPI.setPlaceholders(player, json);
        if(json.startsWith("[text]")) {
            json = json.replace("[text]", "");
            player.sendMessage(json);
            return;
        }
        try {
            Constructor packetConstructor = ReflectionAPI.getNmsClass("PacketPlayOutChat").getConstructor(ReflectionAPI.getNmsClass("IChatBaseComponent"), byte.class);
            Object baseComponent = getSerializerClass().getMethod("a", String.class).invoke(null, json);
            Object packet = packetConstructor.newInstance(baseComponent, (byte) 0);
            ReflectionAPI.sendPacket(player, packet);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private Class getSerializerClass() {
        if(verBiggerThan(1, 8) && verBiggerThan(2, 3)) {
            return ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
        } else {
            return ReflectionAPI.getNmsClass("ChatSerializer");
        }
    }

    public boolean verBiggerThan(int depth, int version) {
        return Parsing.parseString(Bukkit.getBukkitVersion().split("\\-")[0].split("\\.")[depth]) >= version;
    }
}
