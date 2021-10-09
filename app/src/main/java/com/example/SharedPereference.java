package com.example;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPereference {

    static String prefName = "consession";
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    public static void setPrefs(Context context,String prefKey,String value){
        preferences =context.getSharedPreferences(prefName,Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey,value);
        editor.apply();
    }


    public static String getPrefs(Context context, String prefKey){
        preferences = context.getSharedPreferences(prefKey,Context.MODE_PRIVATE);
        return preferences.getString(prefKey,null);
    }

}
