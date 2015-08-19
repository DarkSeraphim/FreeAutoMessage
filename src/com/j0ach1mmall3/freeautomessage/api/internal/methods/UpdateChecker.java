package com.j0ach1mmall3.freeautomessage.api.internal.methods;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
	public static boolean checkUpdate(int resourceID, String currentVersion){
		try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resourceID).getBytes("UTF-8"));
            String newversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            return !newversion.equalsIgnoreCase(currentVersion);
        }catch (Exception e){
        	e.printStackTrace();
        	return false;
        }
	}
	
	public static String getVersion(int resourceID){
		try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resourceID).getBytes("UTF-8"));
            return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        }catch (Exception e){
        	e.printStackTrace();
        	return "";
        }
	}
}
