package com.j0ach1mmall3.freeautomessage.api.internal.objects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private int resourceID;
    private String currentVersion;
    public UpdateChecker(int resourceID, String currentVersion) {
        this.resourceID = resourceID;
        this.currentVersion = currentVersion;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public boolean checkUpdate(){
		return !getVersion().equalsIgnoreCase(currentVersion);
	}
	
	public String getVersion(){
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
