package com.example.administrator.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    private static final String ARG_EXITLOOP="exit_loop";
    private static final String ARG_STARTSONG="start_song";
    private static final String ARG_DEFAULTVOLUME="default_volume";
    private static final String ARG_LOVEDIR="love_dir";
    private static final String ARG_SETTING="arg_setting";

    public static final String EXTRA_SETTING="com.example.administrator.musicplayer.setting";

    boolean bSleepMode;
    boolean bRandom;
    boolean bBySong;
    int nExitLoop;
    int nStartSong;
    int nDefaultVolume;
    int nRandomCount;
    int nRandomStart;
    int nVolumeDownTime;
    int nVolumeDownTime1;
    int nVolumeDownTime2;
    int nMidnightStart;
    int nMidnightEnd;
    int nExitTime;
    ArrayList arySetting;

    EditText mSongDir;
    EditText mRandomDir;
    EditText mRandomCount;
    EditText mExitLoop;
    EditText mStartSong;
    EditText mDefaultVolume;
    EditText mRandomStart;
    EditText mVolumeDownTime;
    EditText mVolumeDownTime1;
    EditText mVolumeDownTime2;
    EditText mMidnightStart;
    EditText mMidnightEnd;
    EditText mExitTime;
    CheckBox mSleepMode;
    CheckBox mRandomAddSong;
    CheckBox mBySong;
    Button mSaveButton;
    String strLoveDir;
    String strRandomDir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        String strTemp;
        View view = inflater.inflate(R.layout.fragment_setting3, container, false);

        mExitLoop=(EditText)view.findViewById(R.id.exitloop);
        mStartSong=(EditText)view.findViewById(R.id.startsong);
        mDefaultVolume=(EditText)view.findViewById(R.id.defaultvolume);
        mSongDir=(EditText)view.findViewById(R.id.inputpath);
        mRandomDir=(EditText)view.findViewById(R.id.randomdir);
        mRandomCount=(EditText)view.findViewById(R.id.randomcount);
        mRandomStart=(EditText)view.findViewById(R.id.randomstart);
        mVolumeDownTime=(EditText)view.findViewById(R.id.volume_down_time);
        mVolumeDownTime1=(EditText)view.findViewById(R.id.volume_down_time1);
        mVolumeDownTime2=(EditText)view.findViewById(R.id.volume_down_time2);
        mMidnightStart=(EditText)view.findViewById(R.id.midnight_start);
        mMidnightEnd=(EditText)view.findViewById(R.id.midnight_end);
        mExitTime=(EditText)view.findViewById(R.id.exit_time);

        mSleepMode=(CheckBox)view.findViewById(R.id.sleepmode);
        mRandomAddSong=(CheckBox)view.findViewById(R.id.randomaddsong);
        mBySong=(CheckBox)view.findViewById(R.id.bysong);
        mSaveButton=(Button)view.findViewById(R.id.saveall);

        mSongDir.setText(strLoveDir);
        mRandomDir.setText(strRandomDir);

        strTemp=String.format("%d",nExitLoop);
        mExitLoop.setText(strTemp);
        strTemp=String.format("%d",nStartSong);
        mStartSong.setText(strTemp);
        strTemp=String.format("%d",nDefaultVolume);
        mDefaultVolume.setText(strTemp);
        strTemp=String.format("%d",nRandomCount);
        mRandomCount.setText(strTemp);
        strTemp=String.format("%d",nRandomStart);
        mRandomStart.setText(strTemp);
        strTemp=String.format("%d",nVolumeDownTime);
        mVolumeDownTime.setText(strTemp);
        strTemp=String.format("%d",nVolumeDownTime1);
        mVolumeDownTime1.setText(strTemp);
        strTemp=String.format("%d",nVolumeDownTime2);
        mVolumeDownTime2.setText(strTemp);
        strTemp=String.format("%d",nMidnightStart);
        mMidnightStart.setText(strTemp);
        strTemp=String.format("%d",nMidnightEnd);
        mMidnightEnd.setText(strTemp);
        strTemp=String.format("%d",nExitTime);
        mExitTime.setText(strTemp);

        mRandomAddSong.setChecked(bRandom);
        mSleepMode.setChecked(bSleepMode);
        mBySong.setChecked(bBySong);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                String strTemp;
                int nTemp;

                strTemp=mExitLoop.getText().toString();
                try
                {
                    nExitLoop=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }

                strTemp=mStartSong.getText().toString();
                try
                {
                    nStartSong=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }

                strTemp=mDefaultVolume.getText().toString();
                try
                {
                    nDefaultVolume=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mRandomCount.getText().toString();
                try
                {
                    nRandomCount=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mRandomStart.getText().toString();
                try
                {
                    nRandomStart=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mVolumeDownTime.getText().toString();
                try
                {
                    nVolumeDownTime=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mVolumeDownTime1.getText().toString();
                try
                {
                    nVolumeDownTime1=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mVolumeDownTime2.getText().toString();
                try
                {
                    nVolumeDownTime2=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mMidnightStart.getText().toString();
                try
                {
                    nMidnightStart=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mMidnightEnd.getText().toString();
                try
                {
                    nMidnightEnd=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }
                strTemp=mExitTime.getText().toString();
                try
                {
                    nExitTime=Integer.parseInt(strTemp);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getActivity(),"intput err:"+e.toString(),Toast.LENGTH_SHORT).show();;
                }

                if(nExitLoop<=0||nStartSong<=0||nDefaultVolume<=0||nRandomCount<=0||nRandomStart<=0||nVolumeDownTime<=0||
                        nVolumeDownTime1<=0||nVolumeDownTime2<=0||nExitTime<=0) {
                    Toast.makeText(getActivity(), "intput err,should be bigger than 0 err:", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nMidnightStart<0||nMidnightEnd<0||nMidnightStart>24||nMidnightEnd>24||nMidnightStart>nMidnightEnd)
                {
                    Toast.makeText(getActivity(), "intput midnight time err", Toast.LENGTH_SHORT).show();
                    return;
                }
                QueryPreferences.setStoredSongDir(getActivity(),mSongDir.getText().toString());
                QueryPreferences.setStoredStartSong(getActivity(),nStartSong);
                QueryPreferences.setStoredExitLoop(getActivity(),nExitLoop);
                QueryPreferences.setStoredDefaultVolume(getActivity(),nDefaultVolume);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_RANDOMCOUNT,nRandomCount);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_RANDOMSTART,nRandomStart);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME,nVolumeDownTime);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME1,nVolumeDownTime1);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME2,nVolumeDownTime2);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTSTART,nMidnightStart);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTEND,nMidnightEnd);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_EXITTIME,nExitTime);

                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_ISSLEEPMODE,mSleepMode.isChecked()?1:0);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_ISRANDOM,mRandomAddSong.isChecked()?1:0);
                QueryPreferences.setIntPreference(getActivity(),QueryPreferences.PREF_ISBYSONG,mBySong.isChecked()?1:0);
                QueryPreferences.setStringPreference(getActivity(),QueryPreferences.PREF_RANDOMDIR,mRandomDir.getText().toString());


                Intent data=new Intent();
                ArrayList list=new ArrayList();
                list.add(SettingParameter.EXITLOOP,mExitLoop.getText());
                list.add(SettingParameter.STARTSONG,mStartSong.getText());
                list.add(SettingParameter.DEFAULTVOLUME,mDefaultVolume.getText());
                list.add(SettingParameter.ISRANDOM,mRandomAddSong.isChecked()?"true":"false");
                list.add(SettingParameter.ISSLEEPMODE,mSleepMode.isChecked()?"true":"false");
                list.add(SettingParameter.RANDOMCOUNT,mRandomCount.getText());
                list.add(SettingParameter.RANDOMSTART,mRandomStart.getText());
                list.add(SettingParameter.VOLUMEDOWNTIME,mVolumeDownTime.getText());
                list.add(SettingParameter.VOLUMEDOWNTIME1,mVolumeDownTime1.getText());
                list.add(SettingParameter.VOLUMEDOWNTIME2,mVolumeDownTime2.getText());
                list.add(SettingParameter.ISBYSONG,mBySong.isChecked()?"true":"false");
                list.add(SettingParameter.MIDNIGHTSTART,mMidnightStart.getText());
                list.add(SettingParameter.MIDNIGHTEND,mMidnightEnd.getText());
                list.add(SettingParameter.EXITTIME,mExitTime.getText());
                data.putStringArrayListExtra(EXTRA_SETTING,list);
                getActivity().setResult(Activity.RESULT_OK,data);


            }
        });


        return view;
    }

    public static SettingFragment newInstance(String strLoveDir){
        Bundle args=new Bundle();
        /*args.putSerializable(ARG_EXITLOOP,nExitLoop);
        args.putSerializable(ARG_STARTSONG,nStartSong);
        args.putSerializable(ARG_DEFAULTVOLUME,nDefaultVolume);
        */
        //Logger.writeLog("settingFragment:ready to put Serial setting");
        //args.putSerializable(ARG_SETTING,arySettingPar);
        args.putSerializable(ARG_LOVEDIR,strLoveDir);
        SettingFragment fragment=new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*nExitLoop=(int)getArguments().getSerializable(ARG_EXITLOOP);
        nStartSong=(int)getArguments().getSerializable(ARG_STARTSONG);
        nDefaultVolume=(int)getArguments().getSerializable(ARG_DEFAULTVOLUME);*/

        //Logger.writeLog("SettingFragment:ready to restore arylist");
        /*arySetting=(ArrayList)getArguments().getSerializable(ARG_SETTING);
        nExitLoop=(int)arySetting.get(SettingParameter.EXITLOOP);
        nStartSong=(int)arySetting.get(SettingParameter.STARTSONG);
        nDefaultVolume=(int)arySetting.get(SettingParameter.DEFAULTVOLUME);

        if((int)arySetting.get(SettingParameter.ISRANDOM)==1)
            bRandom=true;
        else
            bRandom=false;
        if((int)arySetting.get(SettingParameter.ISSLEEPMODE)==1)
            bSleepMode=true;
        else
            bSleepMode=false;
        if((int)arySetting.get(SettingParameter.BYSONG)==1)
            bBySong=true;
        else
            bBySong=false;*/

        strLoveDir=(String)getArguments().getSerializable(ARG_LOVEDIR);
        nExitLoop=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_EXITLOOP);
        nStartSong=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_STARTSONG);
        nDefaultVolume=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_DEFAULTVOLUME);
        bRandom=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISRANDOM)==1?true:false;
        bSleepMode=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISSLEEPMODE)==1?true:false;
        bBySong=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISBYSONG)==1?true:false;

        nRandomCount=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_RANDOMCOUNT);
        nRandomStart=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_RANDOMSTART);
        strRandomDir=QueryPreferences.getStringPreference(getActivity(),QueryPreferences.PREF_RANDOMDIR);
        nVolumeDownTime=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME);
        nVolumeDownTime1=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME1);
        nVolumeDownTime2=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME2);
        nMidnightStart=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTSTART);
        nMidnightEnd=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTEND);
        nExitTime=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_EXITTIME);
    }

}
