package common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class BasicSharedPreference {
    protected SharedPreferences sp;
    
    public BasicSharedPreference(Context ctx) {
        sp = ctx.getSharedPreferences("spMissions", Context.MODE_PRIVATE);
    }

    public boolean getBooleanKey(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public String getStringKey(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public int getIntKey(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public boolean putsStringValue(String key, String value) {
        return sp.edit().putString(key, value).commit();
    }

    public boolean putsBooleanValue(String key, boolean value) {
        return sp.edit().putBoolean(key, value).commit();
    }

    public boolean putsIntValue(String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }

    public boolean putObject(String key, Object o) {
        String json = new Gson().toJson(o);
        return sp.edit().putString(key, json).commit();
    }

    public <T> Object getObject(String key, Class<T> o) {
        Gson gson = new Gson();
        return (gson.fromJson(sp.getString(key, ""), o));
    }
}