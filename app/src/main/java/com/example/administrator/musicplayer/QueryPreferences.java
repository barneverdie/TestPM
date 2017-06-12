package com.example.administrator.musicplayer;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/2/3.
 */

public class QueryPreferences {
    public static final String PREF_SONGDIR="QuerySongDir";
    public static final String PREF_RANDOMDIR="QueryRandomDir";
    public static final String PREF_STARTSONG="QueryStartSong";
    public static final String PREF_DEFAULTVOLUME="QueryDefaultVolume";
    public static final String PREF_EXITLOOP="QueryExitLoop";
    public static final String PREF_RANDOMCOUNT="QueryRandomCount";
    public static final String PREF_RANDOMSTART="QueryRandomStart";
    public static final String PREF_VOLUMEDOWNTIME="QueryVolumeDownTime";
    public static final String PREF_VOLUMEDOWNTIME1="QueryVolumeDownTime1";
    public static final String PREF_VOLUMEDOWNTIME2="QueryVolumeDownTime2";
    public static final String PREF_MIDNIGHTSTART="QueryMidnightStart";
    public static final String PREF_MIDNIGHTEND="QueryMidnightEnd";
    public static final String PREF_EXITTIME="QueryExitTime";

    public static final String PREF_ISRANDOM="QueryIsRandom";
    public static final String PREF_ISSLEEPMODE="QueryIsSleepMode";
    public static final String PREF_ISBYSONG="QueryIsBySong";


    public static String getStringPreference(Context context,String strQuery){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(strQuery,null);
    }

    public static void setStringPreference(Context context,String strQuery,String strData){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(strQuery,strData).apply();
    }

    public static int getIntPreference(Context context,String strQuery){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(strQuery,0);
    }

    public static void setIntPreference(Context context,String strQuery,int nInput){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(strQuery,nInput).apply();
    }

    public static String getStoredSongDir(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SONGDIR,null);
    }

    public static void setStoredSongDir(Context context,String query){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_SONGDIR,query).apply();
    }

    public static int getStoredExitLoop(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_EXITLOOP,8);
    }

    public static void setStoredExitLoop(Context context,int nExitLoop){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_EXITLOOP,nExitLoop).apply();
    }

    public static int getStoredStartSong(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_STARTSONG,3);
    }

    public static void setStoredStartSong(Context context,int nStartSong){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_STARTSONG,nStartSong).apply();
    }

    public static int getStoredDefaultVolume(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_DEFAULTVOLUME,8);
    }

    public static void setStoredDefaultVolume(Context context,int nDefaultVolume){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_DEFAULTVOLUME,nDefaultVolume).apply();
    }


}
