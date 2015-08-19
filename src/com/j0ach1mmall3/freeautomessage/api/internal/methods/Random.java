package com.j0ach1mmall3.freeautomessage.api.internal.methods;

public class Random {
	private static java.util.Random rand = new java.util.Random();
	public static int getInt(int min, int max){
		return rand.nextInt(max-min) + min;
	}
	
	public static int getInt(int max){
		return getInt(0, max);
	}
	
	public static int getInt(){
		return rand.nextInt();
	}
	
	public static boolean getBoolean(){
		return rand.nextBoolean();
	}
	
	public static Double getDouble(){
		return rand.nextDouble();
	}
}
