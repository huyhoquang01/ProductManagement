package com.example.aidnetworking.models;

import android.content.SharedPreferences;


public class AccessTokenManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public static AccessTokenManager instance= null;

    private AccessTokenManager(SharedPreferences _prefs) {
        this.prefs = _prefs;
        this.editor = prefs.edit();
    }
    public static synchronized AccessTokenManager getInstance(SharedPreferences _prefs){
        if(instance==null){
            instance = new AccessTokenManager(_prefs);
        }
        return instance;
    }

    public void saveToken(AccessToken token){
        editor.putString("ACCESS_TOKEN", token.getAccess_token()).commit();
    }
    public void deleteToken(){
        editor.remove("ACCESS_TOKEN").commit();
    }
    public AccessToken getToken(){
        return new AccessToken(prefs.getString("ACCESS_TOKEN", null));
    }
}
