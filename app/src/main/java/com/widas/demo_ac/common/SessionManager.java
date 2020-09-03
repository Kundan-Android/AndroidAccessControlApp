package com.widas.demo_ac.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.google.gson.Gson;

public class SessionManager {

    private static final String PREFS_NAME = "accessControlPrefsFile";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    // Shared Preferences
    SharedPreferences preferences;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    //Application context
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public String getAccessToken() {
        return preferences.getString(KEY_ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {

        editor.putString(KEY_ACCESS_TOKEN, accessToken);

        editor.apply();
    }
    public void setUserDetails(UserinfoEntity userinfoEntity){
        Gson gson = new Gson();
        String str = gson.toJson(userinfoEntity);
        editor.putString("UserDetails",str);
        editor.apply();
    }

    public UserinfoEntity getUserDetails(){
        Gson gson = new Gson();
        String json = preferences.getString("UserDetails", "");
        UserinfoEntity obj = gson.fromJson(json, UserinfoEntity.class);
        return obj;
    }
}
