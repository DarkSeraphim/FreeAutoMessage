package com.j0ach1mmall3.freeautomessage.api.internal.methods;

import org.bukkit.Material;

public class Parsing {
	public static int parseString(String s){
		int i = 0;
		try {
		      i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
            e.printStackTrace();
        }
		return i;
	}
	
	public static String parseInt(int i){
		return String.valueOf(i);
	}
	
	@SuppressWarnings("deprecation")
	public static Material parseMaterial(String item){
		if(item.equals("")) return Material.AIR;
		return Material.getMaterial(parseString(item.split(":")[0]));
	}
	
	public static int parseData(String item){
		if(item.equals("")) return 0;
		if(!item.contains(":")) return 0;
		if(item.endsWith(":")) return 0;
		return Integer.valueOf(item.split(":")[1]);
	}
}
