package com.example.administrator.musicplayer;

import android.widget.Toast;

import java.util.Random;

/**
 * Created by Administrator on 2017/2/11.
 */

public class SettingParameter {
    public final static int  EXITLOOP=0;
    public final static int  STARTSONG=1;
    public final static int  DEFAULTVOLUME=2;
    public final static int  ISRANDOM=3;
    public final static int  ISSLEEPMODE=4;
    public final static int  RANDOMCOUNT=5;
    public final static int  RANDOMSTART=6;
    public final static int  VOLUMEDOWNTIME=7;
    public final static int  VOLUMEDOWNTIME1=8;
    public final static int  VOLUMEDOWNTIME2=9;
    public final static int  ISBYSONG=10;
    public final static int  MIDNIGHTSTART=11;
    public final static int  MIDNIGHTEND=12;
    public final static int  EXITTIME=13;

    int nExitLoop;
    int nStartSong;
    int nDefaultVolume;
    boolean bRandom;
    boolean bSleepMode;
    String strRandomPath;
    int nRandomStart;
    int nVolumeDonwTime;
    int nVolumeDonwTime1;
    int nVolumeDonwTime2;
    boolean bBySong;
    int nMidnightStart;
    int nMidnightEnd;
    int nExitTime;

    public int CreateRandom(int nMin,int nMax)
    {
          //return random.nextInt(nMax)%(nMax-nMin+1) + nMin;
        return new Random().nextInt(nMax - nMin) + nMin;


    }
}
