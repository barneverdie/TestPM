package com.example.administrator.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class SettingActivity extends SingleFragmentActivity {

    //public static final String EXTRA_SLEEPMODE="com.example.administrator.musicplayer.sleepmode";
    public static final String EXTRA_EXITLOOP="com.example.administrator.musicplayer.exit_loop";
    public static final String EXTRA_STARTSONG="com.example.administrator.musicplayer.start_song";
    public static final String EXTRA_DEFAULTVOLUME="com.example.administrator.musicplayer.default_volume";
    public static final String EXTRA_LOVEDIR="com.example.administrator.musicplayer.love_dir";
    public static final String EXTRA_SETTING="com.example.administrator.musicplayer.setting";
    static ArrayList arySettingPar;
    @Override
    protected Fragment createFragment(){
        /*int nExitLoop=getIntent().getIntExtra(EXTRA_EXITLOOP,8);
        int nStartSong=getIntent().getIntExtra(EXTRA_STARTSONG,3);
        int nDefaultVolume=getIntent().getIntExtra(EXTRA_DEFAULTVOLUME,8);*/
        String strLoveDir=getIntent().getStringExtra(EXTRA_LOVEDIR);
        Logger.writeLog("ready to createFragment");
        //arySettingPar=getIntent().getIntegerArrayListExtra(EXTRA_SETTING);
        return  SettingFragment.newInstance(strLoveDir);
    }


    public static Intent newIntent(Context packageContext, String strLoveDir){
        Intent intent=new Intent(packageContext,SettingActivity.class);
        intent.putExtra(EXTRA_LOVEDIR,strLoveDir);
        /*arySettingPar =new ArrayList();
        arySettingPar.add(0,nExitLoop);
        arySettingPar.add(1,nStartSong);
        arySettingPar.add(2,nDefaultVolume);
        Logger.writeLog("ready to enter newIntent");
        if(bRandom)
            arySettingPar.add(3,1);
        else
            arySettingPar.add(3,0);
        if(bSleepMode)
            arySettingPar.add(4,1);
        else
            arySettingPar.add(4,0);
        arySettingPar.add(5,nRandomCount);
        arySettingPar.add(6,nRandomStart);
        arySettingPar.add(7,nVolumeDownTime);
        arySettingPar.add(8,nVolumeDownTime1);
        arySettingPar.add(9,nVolumeDownTime2);
        if(bBySong)
            arySettingPar.add(10,1);
        else
            arySettingPar.add(10,0);

        intent.putExtra(EXTRA_EXITLOOP,nExitLoop);
        intent.putExtra(EXTRA_STARTSONG,nStartSong);
        intent.putExtra(EXTRA_DEFAULTVOLUME,nDefaultVolume);*/

        //Logger.writeLog("ready to put Arraylist Extra");
        //intent.putIntegerArrayListExtra(EXTRA_SETTING,arySettingPar);
        //intent.putExtra(EXTRA_SLEEPMODE,bSleepMode);
        return intent;
    }
}
