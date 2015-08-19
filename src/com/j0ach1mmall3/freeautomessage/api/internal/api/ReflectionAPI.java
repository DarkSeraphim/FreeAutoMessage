package com.j0ach1mmall3.freeautomessage.api.internal.api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ReflectionAPI {
	public static String getVersion(){
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4) return array[3];
		return null;
	}
	
	public static boolean useSpigot(){
		String path = "org.spigotmc.Metrics";
		try{
			Class.forName(path);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		    return false;
		}
	}
	
	public static Class<?> getNmsClass(String name){
		String className = "net.minecraft.server." + getVersion() + "." + name;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e){
            e.printStackTrace();
        }
		return clazz;
	}

	public static Class<?> getObcClass(String name){
		String className = "org.bukkit.craftbukkit." + getVersion() + "." + name;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return clazz;
	}
	
	private static Object getHandle(Entity entity){
		HashMap<Class<? extends Entity>, Method> handles = new HashMap<>();
		try {
			if (handles.get(entity.getClass()) != null)
				return handles.get(entity.getClass()).invoke(entity);
			else {
				Method getHandle = entity.getClass().getMethod("getHandle");
				handles.put(entity.getClass(), getHandle);
				return getHandle.invoke(entity);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Object getHandle(World world){
		Class<?> craftWorldClass = getObcClass("CraftWorld");
		try {
			return craftWorldClass.getMethod("getHandle", null).invoke(world, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendPacket(Player p, Object packet) throws IllegalArgumentException {
		Method sendPacket = null;
		try {
            Object handle = getHandle(p);
            if(handle != null) {
                Class c = handle.getClass();
                if (c != null) {
                    Field playerConnection = c.getField("playerConnection");
                    for (Method m : playerConnection.get(ReflectionAPI.getHandle(p)).getClass().getMethods()) {
                        if (m.getName().equalsIgnoreCase("sendPacket")) {
                            sendPacket = m;
                        }
                    }
                    sendPacket.invoke(playerConnection.get(handle), packet);
                }
            }
		} catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e){
			e.printStackTrace();
		}
	}
}
