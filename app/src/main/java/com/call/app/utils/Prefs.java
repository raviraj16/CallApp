package com.call.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by RAVI on 1/25/2017.
 */

public class Prefs {
    private static Prefs mPrefs = null;
    SharedPreferences sharedPreferences;
    private Context mContext;
    private static String KEY_PHONE_NUMBER = "key_phone_number";
    private static String KEY_SIM_ONE_SELECTION = "key_sim_one_selection";

    Prefs(Context context) {
        mContext = context;
    }

    public static void init(Context context) {

        mPrefs = new Prefs(context);

    }

    public static Prefs getInstance() {
        if (mPrefs != null)
            return mPrefs;
        else {
            throw new InstantiationError("Preference not initialized");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        setString(KEY_PHONE_NUMBER, phoneNumber);
    }

    public String getPhoneNumber() {
        return getString(KEY_PHONE_NUMBER, "");
    }

    public void setSimOneSelection(boolean isSelected) {
        setBoolean(KEY_SIM_ONE_SELECTION, isSelected);
    }

    public boolean getSimOneSelection() {
        return getBoolean(KEY_SIM_ONE_SELECTION, true);
    }

    private void setString(String key, String val) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences.edit().putString(key, val).apply();
    }

    private String getString(String key, String def) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getString(key, def);
    }

    private void setBoolean(String key, boolean val) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        sp.edit().putBoolean(key, val).apply();

    }

    private boolean getBoolean(String key, boolean def) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, def);
    }
}
