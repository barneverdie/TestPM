package com.example.administrator.musicplayer;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.musicplayer.database.SongBaseHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/19.
 */

public class FileListFragment extends Fragment {
    private static Handler sleepHandler=new Handler();
    private RecyclerView mFileRecyclerView;
    private RecyclerView mSongRecyclerView;
    private Button mEnterButton;
    private Button mPlayButton;
    private Button mAddButton;
    private Button mStopButton;
    private Button mPrevButton;
    private Button mNextButton;
    private Button mVolumeUpButton;
    private Button mVolumeDownButton;
    private Button mClearAllButton;
    private Button mSaveDBButton;
    private Button mMoveUpButton;
    private Button mMoveDownButton;
    private Button mAddAllButton;
    private Button mMyLoveSongButton;
    private SeekBar mSeekBar;
    private EditText mInputDir;
    private SongAdapter mSongAdapter;
    private FileAdapter mFileAdapter;
    private TextView mCurDir;
    private TextView mSongDisplay;
    private boolean bIsDir;
    String strCurDir;
    List<Song> ltSongs;
    List<Song> ltRandomSongs;
    List<FileLabel> ltFiles;
    ArrayList mSetting;
    MediaPlayer mPlayer;
    int nSelectIndex;
    int nCurSongIndex;
    int nCurSelectedFile;
    int nPlayCount;
    int nCurVolume;
    int nVolumeDownTime;
    int nVolumeDownTime1;
    int nVolumeDownTime2;
    int nMidnightStart;
    int nMidnightEnd;
    int nExitTime;
    Date dPlayTime;
    static int nState;

    boolean bSleepMode;
    boolean bRandom;
    boolean bBySong;
    boolean bIsPlaying;

    private AudioManager mAudioManager;
    Logger mLogger;
    private static final String TAG="MediaPlayer";
    private static final int REQUEST_SETTING=1;
    private static final String EXTRA_SETTING="com.example.administrator.musicplayer.setting";
    String strLoveDir;
    String strRandomDir;
    int nExitLoop;
    int nDefaultVolume;
    int nStartSong;
    int nRandomCount;
    int nRandomIndex;
    int nMaxVolume;
    int nRandomStart;
    int nSongSelected;
    int nFileSelected;


    @Override public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        bIsPlaying=false;
        nCurSelectedFile=-1;
    }

    int RestoreSetting(int nSetting)
    {
        String strTemp;
        int nReturn;

        if(mSetting==null)
            return -1;
        strTemp=mSetting.get(nSetting).toString();
        try{
            nReturn=Integer.parseInt(strTemp);
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(getActivity(),"exitloop parse err:"+e.toString(),Toast.LENGTH_SHORT).show();
            return -1;
        }
        return nReturn;
    }

    @Override public void onActivityResult(int nRequestCode,int nResultCode,Intent data) {
        String strTemp;
        Log.d("MUSICMPLAYERTAG","enter onactivity");
        if(nResultCode!= Activity.RESULT_OK)
        {
            mLogger.writeLog("result not ok");
            return;
        }
        if(nRequestCode==REQUEST_SETTING)
        {
            if(data==null)
                return;
            else{
                //Logger.writeLog("ready to parse intent");
                //mSetting=data.getIntegerArrayListExtra("EXTRA_SETTING");
                mSetting=data.getStringArrayListExtra(SettingFragment.EXTRA_SETTING);
                //SpannableString string1=(SpannablstrTemp=mSetting.get(SettingParameter.RANDOMCOUNT).toString()sttting.get(SettingParamet
                nExitLoop=RestoreSetting(SettingParameter.EXITLOOP);
                nStartSong=RestoreSetting(SettingParameter.STARTSONG);
                nDefaultVolume=RestoreSetting(SettingParameter.DEFAULTVOLUME);
                nRandomCount=RestoreSetting(SettingParameter.RANDOMCOUNT);
                nRandomStart=RestoreSetting(SettingParameter.RANDOMSTART);
                nVolumeDownTime=RestoreSetting(SettingParameter.VOLUMEDOWNTIME);
                nVolumeDownTime1=RestoreSetting(SettingParameter.VOLUMEDOWNTIME1);
                nVolumeDownTime2=RestoreSetting(SettingParameter.VOLUMEDOWNTIME2);
                nMidnightStart=RestoreSetting(SettingParameter.MIDNIGHTSTART);
                nMidnightEnd=RestoreSetting(SettingParameter.MIDNIGHTEND);
                nExitTime=RestoreSetting(SettingParameter.EXITTIME);

                strTemp=mSetting.get(SettingParameter.ISRANDOM).toString();
                if(strTemp.equals("true"))
                    bRandom=true;
                else
                    bRandom=false;
                strTemp=mSetting.get(SettingParameter.ISSLEEPMODE).toString();
                if(strTemp.equals("true"))
                    bSleepMode=true;
                else
                    bSleepMode=false;
                strTemp=mSetting.get(SettingParameter.ISBYSONG).toString();
                if(strTemp.equals("true"))
                    bBySong=true;
                else
                    bBySong=false;


                strTemp=String.format("song:%s,rand:%s,nStartSong=%d,nRandomCount=%d,nRandomStart=%d",strLoveDir,strRandomDir,nStartSong,nRandomCount,nRandomStart);
                Toast.makeText(getActivity(),strTemp,Toast.LENGTH_LONG).show();



            }
       }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_saveall:
                Intent intent=SettingActivity.newIntent(getActivity(),strLoveDir);
                //Intent intent=new Intent(getActivity(),SettingActivity.class);
                //intent.putExtra("dfdfsdf",nExitLoop);
                startActivityForResult(intent,REQUEST_SETTING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean LoadDB(){
        int nCount;
        SongLab songLab=SongLab.get(getActivity());

        nCount=songLab.getCount();
        if(nCount<=0) return false;

        ltSongs=songLab.getSongs();
        return true;

    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState){
        String strTemp;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            getActivity().requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        };
        ltSongs=new ArrayList<>();
        ltFiles=new ArrayList<>();
        ltRandomSongs=new ArrayList<>();
        nCurSongIndex=0;
        nPlayCount=0;
        nExitLoop=8;
        nRandomCount=3;
        nRandomStart=4;
        nFileSelected=0;
        nSongSelected=0;
        bIsDir=true;
        mPlayer=new MediaPlayer();
        bSleepMode=false;
        bRandom=false;
        View view=inflater.inflate(R.layout.fragment_filelist2,container,false);

        WindowManager mWindowManager=getActivity().getWindowManager();
        Display display= mWindowManager.getDefaultDisplay();
        int nHeight=display.getHeight();
        mAudioManager=(AudioManager)(getActivity().getSystemService(Context.AUDIO_SERVICE));

        nCurSongIndex=0;
        mFileRecyclerView=(RecyclerView)view.findViewById(R.id.filelist_recycler_view2);
        mFileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSongRecyclerView=(RecyclerView)view.findViewById(R.id.song_list_recycler_view);
        mSongRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCurDir=(TextView)view.findViewById(R.id.cur_dir);
        mInputDir=(EditText)view.findViewById(R.id.input_dir);
        mSongDisplay=(TextView)view.findViewById(R.id.songdata);

        mSeekBar=(SeekBar)view.findViewById(R.id.seekbar);

        mEnterButton=(Button)view.findViewById(R.id.enter);
        mPlayButton=(Button)view.findViewById(R.id.playstop);
        mAddButton=(Button)view.findViewById(R.id.addsong);
        mStopButton=(Button)view.findViewById(R.id.stop);
        mClearAllButton=(Button)view.findViewById(R.id.clearall);
        mVolumeUpButton=(Button)view.findViewById(R.id.vup);
        mVolumeDownButton=(Button)view.findViewById(R.id.vdown);
        mMoveUpButton=(Button)view.findViewById(R.id.moveup);
        mMoveDownButton=(Button)view.findViewById(R.id.movedown);
        mAddAllButton=(Button)view.findViewById(R.id.addall);
        mSaveDBButton =(Button)view.findViewById(R.id.savedb);
        mPrevButton=(Button)view.findViewById(R.id.prev);
        mNextButton=(Button)view.findViewById(R.id.next);
        mMyLoveSongButton=(Button)view.findViewById(R.id.mylovesong);
        mLogger.writeLog("create view");

        strLoveDir=QueryPreferences.getStoredSongDir(getActivity());
        strRandomDir=QueryPreferences.getStringPreference(getActivity(),QueryPreferences.PREF_RANDOMDIR);
        nExitLoop=QueryPreferences.getStoredExitLoop(getActivity());
        nDefaultVolume=QueryPreferences.getStoredDefaultVolume(getActivity());
        nStartSong=QueryPreferences.getStoredStartSong(getActivity());
        nRandomCount=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_RANDOMCOUNT);
        nRandomStart=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_RANDOMSTART);
        nVolumeDownTime=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME);
        nVolumeDownTime1=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME1);
        nVolumeDownTime2=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_VOLUMEDOWNTIME2);
        nMidnightStart=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTSTART);
        nMidnightEnd=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_MIDNIGHTEND);
        nExitTime=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_EXITTIME);

        bRandom=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISRANDOM)==1?true:false;
        bSleepMode=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISSLEEPMODE)==1?true:false;
        bBySong=QueryPreferences.getIntPreference(getActivity(),QueryPreferences.PREF_ISBYSONG)==1?true:false;
        //strTemp=String.format("nStartSong=%d,nRandomCount=%d,nRandomStart=%d",nStartSong,nRandomCount,nRandomStart);
        //Toast.makeText(getActivity(),strTemp,Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),"Wish you health and happy",Toast.LENGTH_LONG).show();I
        if(LoadDB())
        {
            strTemp=String.format("%d songs loaded",ltSongs.size());
            mLogger.writeLog(strTemp);
            UpdateSongsUI();
        }
        else
            mLogger.writeLog("songsdb empty or not exist");
        if(strLoveDir==null) {
            mInputDir.setText("/storage");
            strCurDir="/storage";
        }
        else
        {
            mInputDir.setText(strLoveDir);
            strCurDir=strLoveDir;
            LoadFiles(strCurDir);

        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override  public void onCompletion(MediaPlayer mp) {

                if(ltSongs.size()<=0) return;
                if(!bIsPlaying)
                {
                    return;
                }

                ltSongs.get(nCurSongIndex).setPlaying(false);
                mSongRecyclerView.getAdapter().notifyDataSetChanged();

                String strTemp=String.format("complete listener:cursongindex=%d,randomStart=%d,randomsize=%d",nCurSongIndex,nRandomStart,ltRandomSongs.size());
                Logger.writeLog(strTemp);

                if(!bRandom)
                {
                    nCurSongIndex++;
                    if(nCurSongIndex>=ltSongs.size())
                        nCurSongIndex=0;
                    PlaySong(mp, nCurSongIndex, false);
                }
                else
                {
                    if(nPlayCount==nRandomStart){
                        PlaySong(mp,nCurSongIndex,true);}
                    else {
                        if (nPlayCount % nRandomCount == 0)
                            PlaySong(mp, nCurSongIndex, true);
                        else{
                            nCurSongIndex++;
                            if(nCurSongIndex>=ltSongs.size())
                                nCurSongIndex=0;
                            PlaySong(mp, nCurSongIndex, false);}
                    }
                }
            }
        });

        mEnterButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                LoadFiles(mInputDir.getText().toString());
            }
        });

        mMoveUpButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {

                if(nSongSelected==0) return;
                if(ltSongs.size()==0)
                    return;

                Song songPrev=new Song(ltSongs.get(nSongSelected-1));
                Song songSelected=new Song(ltSongs.get(nSongSelected));

                /*song.setId(ltSongs.get(nSongSelected).getId());
                song.setFileName(ltSongs.get(nSongSelected).getFileName());
                song.setFilePath(ltSongs.get(nSongSelected).getFilePath());
                song.setPlaying(ltSongs.get(nSongSelected).isPlaying());*/

                ltSongs.set(nSongSelected-1,songSelected);
                ltSongs.set(nSongSelected,songPrev);
                ltSongs.get(nSongSelected).setSelected(false);
                nSongSelected--;
                ltSongs.get(nSongSelected).setSelected(true);
                mSongRecyclerView.getAdapter().notifyItemChanged(nSongSelected);
                mSongRecyclerView.getAdapter().notifyItemChanged(nSongSelected+1);

                //song.set
            }
        });

        mMoveDownButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {

                if(nSongSelected==ltSongs.size()) return;
                if(ltSongs.size()==0)
                    return;

                Song songNext=new Song(ltSongs.get(nSongSelected+1));
                Song songSelected=new Song(ltSongs.get(nSongSelected));

                /*song.setId(ltSongs.get(nSongSelected).getId());
                song.setFileName(ltSongs.get(nSongSelected).getFileName());
                song.setFilePath(ltSongs.get(nSongSelected).getFilePath());
                song.setPlaying(ltSongs.get(nSongSelected).isPlaying());*/

                ltSongs.set(nSongSelected+1,songSelected);
                ltSongs.set(nSongSelected,songNext);
                ltSongs.get(nSongSelected).setSelected(false);
                nSongSelected++;
                ltSongs.get(nSongSelected).setSelected(true);
                mSongRecyclerView.getAdapter().notifyDataSetChanged();

            }
        });

        mSaveDBButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                //QueryPreferences.setStringPreference(getActivity(), QueryPreferences.PREF_SONGDIR,mInputDir.getText().toString());
                //(getActivity(),mInputDir.getText().toString());
                SongLab songLab=SongLab.get(getActivity());

                songLab.deleteAllSongs();
                for(Song song:ltSongs) {
                    songLab.addSong(song);
                }



            }


        });

        mMyLoveSongButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                String strSongPath;
                String strSongName;
                String[] arySplit;


                bSleepMode=true;
                bRandom=true;
                if(strLoveDir==null)
                    mInputDir.setText("/storage/sdcard0/bluetooth");
                else
                    mInputDir.setText(strLoveDir);
                LoadFiles(mInputDir.getText().toString());
                if(ltFiles.size()==0)
                {
                    Toast.makeText(getActivity(),"find no files to add",Toast.LENGTH_SHORT).show();
                    return;
                }
                for (FileLabel label : ltFiles) {
                    Song objSong = new Song();
                    objSong.setFileName(label.getFileName());
                    objSong.setFilePath(label.getFilePath());
                    ltSongs.add(objSong);
                }
                if(strRandomDir!=null) {
                    LoadRandomFiles(strRandomDir,ltRandomSongs);

                }
                nDefaultVolume=QueryPreferences.getStoredDefaultVolume(getActivity());
                strRandomDir=QueryPreferences.getStringPreference(getActivity(),QueryPreferences.PREF_RANDOMDIR);
                nMaxVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,nDefaultVolume>nMaxVolume?nMaxVolume:nDefaultVolume,0);
                nCurVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                //strTemp=String.format("%d_V%d-%d:%d-%s",nPlayCount,nCurVolume,mPlayer.getCurrentPosition(),mPlayer.getDuration(),mSongs.get(nCurSongIndex).getFileName());
                nCurSongIndex=QueryPreferences.getStoredStartSong(getActivity());
                if(nCurSongIndex>=ltSongs.size())
                    nCurSongIndex=0;
                //mSongAdapter = new SongAdapter(ltSongs);
                //mSongRecyclerView.setAdapter(mSongAdapter);
                UpdateSongsUI();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                if(mPlayer!=null) {
                    //if (mPlayer.isPlaying())
                        sleepHandler.removeCallbacks(timerThread);
                        mPlayer.stop();
                        bIsPlaying=false;
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                nPlayCount=0;
                if(mPlayer!=null) {
                    if (mPlayer.isPlaying())
                        mPlayer.stop();
                    ltSongs.get(nCurSongIndex).setPlaying(false);
                    mSongRecyclerView.getAdapter().notifyItemChanged(nCurSongIndex);
                    nCurSongIndex++;
                    if (nCurSongIndex >= ltSongs.size())
                        nCurSongIndex = 0;
                    PlaySong(mPlayer, nCurSongIndex,false);
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                nPlayCount=0;
                if(mPlayer!=null)
                {
                    if (mPlayer.isPlaying())
                        mPlayer.stop();
                    ltSongs.get(nCurSongIndex).setPlaying(false);
                    mSongRecyclerView.getAdapter().notifyItemChanged(nCurSongIndex);
                    nCurSongIndex--;
                    if (nCurSongIndex<0)
                        nCurSongIndex = ltSongs.size()-1;
                    PlaySong(mPlayer, nCurSongIndex,false);
                 }
            }
        });

        mVolumeUpButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                String strTemp;
                mAudioManager = (AudioManager) (getActivity().getSystemService(Context.AUDIO_SERVICE));
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                nCurVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                strTemp=String.format("%d-%d:%d-%s",nPlayCount,mPlayer.getCurrentPosition(),mPlayer.getDuration(),ltSongs.get(nCurSongIndex).getFileName());
                mSongDisplay.setText(strTemp);
            }
        });

        mVolumeDownButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v)
            {
                String strTemp;
                mAudioManager=(AudioManager)(getActivity().getSystemService(Context.AUDIO_SERVICE));
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
                nCurVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                strTemp=String.format("%d-%d:%d-%s",nPlayCount,mPlayer.getCurrentPosition(),mPlayer.getDuration(),ltSongs.get(nCurSongIndex).getFileName());
                mSongDisplay.setText(strTemp);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                String strSongPath;
                String strSongName;
                String[] arySplit;
                String strTemp;
                JudgeMultiMediaType objMediaJudge=new JudgeMultiMediaType();
                int nType;
                FileLabel label;

                if (nCurSelectedFile<0) {
                    Toast.makeText(getActivity(), "not selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                label=ltFiles.get(nCurSelectedFile);

                if (label.isDir()) {
                    Toast.makeText(getActivity(), "not song", Toast.LENGTH_SHORT).show();
                    return;
                }
                objMediaJudge.initReflect();
                nType=objMediaJudge.getMediaFileType(label.getFilePath());
                if(!objMediaJudge.isAudioFile(nType))
                {
                    strTemp = String.format("isnot audio,filetype=%d", nType);
                    Toast.makeText(getActivity(), strTemp, Toast.LENGTH_SHORT).show();
                    return;
                }

                //arySplit = strSongPath.split("/");
                //strSongName = arySplit[arySplit.length - 1];
                Song objSong = new Song();
                objSong.setFileName(label.getFileName());
                objSong.setFilePath(label.getFilePath());
                ltSongs.add(objSong);

                //mSongAdapter = new SongAdapter(ltSongs);
                //mSongAdapter.setSongs(ltSongs);
                //mSongRecyclerView.setAdapter(mSongAdapter);
                UpdateSongsUI();
            }
        });

        mAddAllButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                String strSongPath;
                String strSongName;
                String[] arySplit;
                JudgeMultiMediaType objMediaJudge=new JudgeMultiMediaType();
                int nType;

                if(ltFiles.size()==0)
                {
                    Toast.makeText(getActivity(),"find no files to add",Toast.LENGTH_SHORT).show();
                    return;
                }
                for (FileLabel label : ltFiles) {
                    objMediaJudge.initReflect();
                    nType=objMediaJudge.getMediaFileType(label.getFilePath());
                    if(objMediaJudge.isAudioFile(nType)) {
                        Song objSong = new Song();
                        objSong.setFileName(label.getFileName());
                        objSong.setFilePath(label.getFilePath());
                        ltSongs.add(objSong);
                    }

                }
                //mSongAdapter = new SongAdapter(ltSongs);
                //mSongRecyclerView.setAdapter(mSongAdapter);
                UpdateSongsUI();
            }
        });

        mClearAllButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                if (ltSongs.size() > 0)
                    ltSongs.clear();
                if (ltRandomSongs.size() > 0)
                    ltRandomSongs.clear();
                //
                mSongAdapter.setSongs(ltSongs);
                mSongRecyclerView.setAdapter(mSongAdapter);
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v){
                String strSongPath;
                String strSongData;
                String strTemp;
                nPlayCount=0;

                if(ltSongs.size()==0){
                    Toast.makeText(getActivity(),"songlist not exist",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nCurSongIndex>=ltSongs.size())
                    nCurSongIndex=0;
                if(strRandomDir!=null) {
                    LoadRandomFiles(strRandomDir,ltRandomSongs);
                }
                dPlayTime = new Date(System.currentTimeMillis());
                nState=0;

                /*if(!bSleepMode)
                    mLogger.writeLog("ready to send message");
                    sleepHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LowerVolume();
                    }
                },1*60*1000);*/

                PlaySong(mPlayer,nCurSongIndex,false);

                //origin here

                    /*rrently_Music.setText(selectName);
	                    seekBar.setMax(mplayer.getDuration());// ��Ƶ�ļ�����ʱ��
                    seekBar.setProgress(1);
                    currently_Time.setText(getFileTime(mplayer.getCurrentPosition()));
                    //	lrcTime.setText(systemProvider.getArtist());

                    handler.removeCallbacks(thread_One);
                    end_Time.setText(getFileTime(mplayer.getDuration()));
                    handler.postDelayed(thread_One, 1000);*/


           }
        });


        //LoadFiles(mInputDir.getText().toString());

        return view;
    }

    private Runnable timerThread = new Runnable() {
        public void run() {

            String strTemp;
            int curProgress = mSeekBar.getProgress() + 1000;
            mSeekBar.setProgress(curProgress);
            mSongDisplay.setText(getTime(mPlayer.getCurrentPosition()));
            //showLrcTwo(lrc_time, lrc_word);
            if(bRandom)
                strTemp = String.format("%s-%d:%d",  ltRandomSongs.get(nRandomIndex).getFileName(),mPlayer.getCurrentPosition(), mPlayer.getDuration());
            else
                strTemp = String.format("%s-%d:%d",  ltSongs.get(nCurSongIndex).getFileName(),mPlayer.getCurrentPosition(), mPlayer.getDuration());
            mSongDisplay.setText(strTemp);
            sleepHandler.postDelayed(timerThread, 1000);
            Log.i("time", strTemp);

        }
    };

    private String getTime(int timeMs) {
        int totalSeconds = timeMs / 1000;// ��ȡ�ļ��ж�����
        //StringBuilder mFormatBuilder = new StringBuilder();
        //Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        //mFormatBuilder.setLength(0);

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return String.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_filelist,menu);
        mLogger.writeLog("create options menu");
    }

    public void  LowerVolume() {
        mAudioManager = (AudioManager) (getActivity().getSystemService(Context.AUDIO_SERVICE));
        Toast.makeText(getActivity(), "lower volume", Toast.LENGTH_SHORT).show();
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        mLogger.writeLog("lower volume");

    }

    public void PlaySong(MediaPlayer mp,int nCurSongIndex,boolean bRandom)
    {

        String strTemp;
        nPlayCount++;
        int nHour;

        if(ltSongs.size()==0)
        {
            Toast.makeText(getActivity(),"not file list",Toast.LENGTH_SHORT).show();
            return;
        }

        nCurVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(bSleepMode) {
            Calendar now = Calendar.getInstance();
            nHour=now.get(Calendar.HOUR_OF_DAY);
            if(bBySong) {
                if(nHour>=nMidnightStart&&nHour<=nMidnightEnd) {
                    if(nPlayCount>=2) {
                        LowerVolume();
                    }
                    if(nPlayCount>nExitLoop/2)
                    {
                        mPlayer.release();
                        Toast.makeText(getActivity(), "exiting", Toast.LENGTH_LONG).show();
                        mLogger.writeLog("night exit");
                        getActivity().finish();
                        System.exit(0);
                        return;
                    }
                }
                else {

                    if (nPlayCount <= 5) {
                        if (nPlayCount == 3 || nPlayCount == 5) {
                            LowerVolume();
                        }
                    }else if (nPlayCount > 5 && nPlayCount <= nExitLoop) {
                        LowerVolume();
                    } else {
                        if (nPlayCount > nExitLoop) {
                            mPlayer.release();
                            Toast.makeText(getActivity(), "exiting", Toast.LENGTH_LONG).show();
                            mLogger.writeLog("exit");
                            getActivity().finish();
                            System.exit(0);
                            return;
                        }
                    }
                }
            }
            //by time
            else{
                Date dCurTime=new Date(System.currentTimeMillis());
                long lEclapseSecond=(dCurTime.getTime()-dPlayTime.getTime())/1000;

                if(nHour>=nMidnightStart&&nHour<=nMidnightEnd) {
                    switch(nState) {
                        case 0:
                            if (lEclapseSecond > nVolumeDownTime1 * 60/2) {
                                LowerVolume();
                                nState = 1;
                            }
                            break;
                        case 1:
                            if (lEclapseSecond  > nVolumeDownTime1 * 60/2+nVolumeDownTime2* 60/2) {
                                LowerVolume();
                                nState = 2;
                            }
                        break;
                        default:
                            if (lEclapseSecond >nVolumeDownTime1 * 60/2+nVolumeDownTime2* 60/2+ (nState-1)*nVolumeDownTime*60/2) {
                                LowerVolume();
                                nState++;

                            }
                            break;

                    }

                    if(lEclapseSecond>=nExitTime*60)
                    {
                        mPlayer.release();
                        Toast.makeText(getActivity(), "exiting", Toast.LENGTH_LONG).show();
                        mLogger.writeLog("night exit");
                        getActivity().finish();
                        System.exit(0);
                        return;
                    }
                }
                else {
                    switch(nState) {
                        case 0:
                            if (lEclapseSecond > nVolumeDownTime1 * 60) {
                                LowerVolume();
                                nState = 1;
                            }
                            break;
                        case 1:
                            if (lEclapseSecond  >nVolumeDownTime1 * 60+ nVolumeDownTime2* 60) {
                                LowerVolume();
                                nState = 2;
                            }
                            break;
                        default:
                            if (lEclapseSecond >nVolumeDownTime1 * 60+ nVolumeDownTime2* 60+ (nState-1)*nVolumeDownTime*60) {
                                LowerVolume();
                                nState++;

                            }
                            break;
                    }
                    if(lEclapseSecond>=nExitTime*60)
                    {
                        mPlayer.release();
                        Toast.makeText(getActivity(), "exiting", Toast.LENGTH_LONG).show();
                        mLogger.writeLog("night exit");
                        getActivity().finish();
                        System.exit(0);
                        return;
                    }
                }


            }

        }

        try {

            mp.reset();
            if(bRandom)
            {
                if(ltRandomSongs.size()==0){
                    Toast.makeText(getActivity(),"not randomsong file list",Toast.LENGTH_SHORT).show();
                    Logger.writeLog("not randomsong file found");
                    return;
                }

                //Toast.makeText(getActivity(),"random song add",Toast.LENGTH_SHORT).show();

                nRandomIndex=new Random().nextInt(ltRandomSongs.size());
                String test;
                test=ltRandomSongs.get(nRandomIndex).getFilePath();
                Logger.writeLog("ready to play randomsong:"+ltRandomSongs.get(nRandomIndex).getFilePath());
                mp.setDataSource(ltRandomSongs.get(nRandomIndex).getFilePath());
                mp.prepare();
                strTemp = String.format("%s-%d:%d",  ltRandomSongs.get(nRandomIndex).getFileName(),mp.getCurrentPosition(), mp.getDuration());
            }
            else {
                //mSongRecyclerView.findViewHolderForItemId(nCurSongIndex);
                //mSongRecyclerView.getAdapter().notifyItemChanged(nCurSongIndex);
                //mSongRecyclerView.getAdapter().notifyItemChanged((nCurSongIndex-1)<0?ltSongs.size():nCurSongIndex-1);

                Logger.writeLog("ready to play normal song:"+ltSongs.get(nCurSongIndex).getFilePath());
                mp.setDataSource(ltSongs.get(nCurSongIndex).getFilePath());
                mp.prepare();
                strTemp = String.format("%s-%d:%d",  ltSongs.get(nCurSongIndex).getFileName(),mPlayer.getCurrentPosition(), mPlayer.getDuration());
                ltSongs.get(nCurSongIndex).setPlaying(true);
                mSongRecyclerView.getAdapter().notifyDataSetChanged();
            }
            mSongDisplay.setText(strTemp);
            bIsPlaying=true;
            mSeekBar.setMax(mp.getDuration());// ��Ƶ�ļ�����ʱ��
            mSeekBar.setProgress(1);

            //	lrcTime.setText(systemProvider.getArtist());

            mp.start();
            sleepHandler.removeCallbacks(timerThread);
            //mSongDisplay.setText(getTime(mPlayer.getDuration()));
            sleepHandler.postDelayed(timerThread, 1000);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void UpdateSongsUI(){
        if(mSongAdapter==null) {
            mSongAdapter = new SongAdapter(ltSongs);
            mSongRecyclerView.setAdapter(mSongAdapter);
        }
        else{
            mSongAdapter.setSongs(ltSongs);

        }
        mSongAdapter.notifyDataSetChanged();
    }

    private void LoadFiles(String strDir){

        String strTemp;
        File[] files;
        FileLabel label;
        File fFile;
        int i=0;

        fFile=new File(strDir);
        if(fFile.isDirectory()) {
            files = fFile.listFiles();
            if (files == null) {
                Toast.makeText(getActivity(), "not avail", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ltFiles.size() > 0)
                ltFiles.clear();

            for (File file : files) {
                label=new FileLabel(file);
                ltFiles.add(label);

            }
            mFileAdapter = new FileAdapter(ltFiles);
            mFileRecyclerView.setAdapter(mFileAdapter);
            mCurDir.setText(strDir);
            bIsDir = true;
            strCurDir = strDir;
        }
        else{
            bIsDir=false;
            mCurDir.setText(strDir);
        }

    }

    private void LoadRandomFiles(String strDir, List ltRandomSongs ){

        String strTemp;
        File[] files;
        File fFile;
        int i=0;


        fFile=new File(strDir);

        if(fFile.isDirectory()) {
            files = fFile.listFiles();
            if (files == null) {
                Toast.makeText(getActivity(), "not avail", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ltRandomSongs.size() > 0)
                ltRandomSongs.clear();

            for (File file : files) {
                Song objSong = new Song();
                objSong.setFileName(file.getName());
                objSong.setFilePath(file.getPath());
                ltRandomSongs.add(objSong);

            }

        }
        else
            Toast.makeText(getActivity(),"error load random dir",Toast.LENGTH_SHORT).show();


    }

    private class FileAdapter extends RecyclerView.Adapter<FileHolder> {
        private List<FileLabel> mFiles;

        public FileAdapter(List<FileLabel> listFiles) {
            mFiles = listFiles;
        }

        @Override
        public FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_file, parent, false);
            return new FileHolder(view);
        }

        @Override
        public void onBindViewHolder(FileHolder holder, int position) {
            FileLabel labelFile = mFiles.get(position);
            holder.mFileNameTextView.setText(labelFile.getFileName());
            //Resources res=getResources();
            //Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.sample_0);


            //holder.mFileImageView.setImageBitmap((BitmapFactory.decodeResource(getResources(),labelFile.getIconId())));
            holder.bindResource(labelFile.getIconId());
            if(labelFile.getSelected())
            {holder.mFileNameTextView.setTextColor(Color.RED);}
            else{
                holder.mFileNameTextView.setTextColor(Color.BLACK);
            }


            
        }

        @Override
        public int getItemCount() {
            return mFiles.size();
        }
    }



    private class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mFileNameTextView;
        private ImageView mFileImageView;
        private File mFile;

        public FileHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mFileNameTextView = (TextView) itemView.findViewById(R.id.list_item_file_name_text_view);
            mFileImageView = (ImageView) itemView.findViewById(R.id.list_item_file_image_view);

        }

        public void bindDrawable(Drawable drawable)
        {
            mFileImageView.setImageDrawable(drawable);
        }

        public void bindResource(int nResource)
        {
            mFileImageView.setImageDrawable(getResources().getDrawable(nResource));
        }


        @Override public void onClick(View v) {
            //Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getId());
            //startActivity(intent);
            String strSelect;
            FileLabel labelFile;

            labelFile=ltFiles.get(this.getAdapterPosition());
            if(labelFile.isDir()){
                LoadFiles(labelFile.getFilePath());
            }
            else{
                if(nCurSelectedFile>=0)
                    ltFiles.get(nCurSelectedFile).setSelected(false);
                mFileRecyclerView.getAdapter().notifyItemChanged(nCurSelectedFile);
                nCurSelectedFile=this.getAdapterPosition();
                labelFile.setSelected(true);
                mFileRecyclerView.getAdapter().notifyItemChanged(nCurSelectedFile);

            }
            /*if (bIsDir) {
                strSelect = mCurDir.getText().toString() + "/" + mFileNameTextView.getText().toString();
                LoadFiles(strSelect);
            } else {
                strSelect = strCurDir + "/" + mFileNameTextView.getText().toString();
                mCurDir.setText(strSelect);
                LoadFiles(strSelect);
            }*/
        }

            /*
            fFile=new File(strSelect);
            if(fFile.isDirectory()) {

                files=fFile.listFiles();
                if(files==null) {
                    Toast.makeText(getActivity(), "not avail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mFiles.size()>0)
                    mFiles.clear();

                for(File file:files)
                {
                    mFiles.add(file);

                }
                mFileAdapter=new FileAdapter(mFiles);
                mFileRecyclerView.setAdapter(mFileAdapter);
                mCurDir.setText(strSelect);
                mIsDir=true;
            }
            else{
                mCurDir.setText(strSelect);
                mIsDir=false;
            }

        }*/


    }


    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {
        private List<Song> mSongs;
        int nFocus;

        public SongAdapter(List<Song> songs) {
            mSongs = songs;
        }

        @Override
        public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_song, parent, false);
            return new SongHolder(view);
        }

        public void setTextWithColor(TextView textView,int  color){

            SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());

            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
         /*ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
            ForegroundColorSpan greenSpan = new ForegroundColorSpan(Color.GREEN);
            ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);*/
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);

            builder.setSpan(colorSpan,0,textView.getText().length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            /*builder.setSpan(whiteSpan, 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setSpan(blueSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(greenSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(yellowSpan, 4,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            textView.setText(builder);
        }

        @Override
        public void onBindViewHolder(SongHolder holder, int position) {
            Song song = mSongs.get(position);
            //TextPaint tp = holder.mSongNameTextView.getPaint();
            //tp.setFakeBoldText(true);
            if(song.isPlaying())
                holder.mSongNameTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            else
                holder.mSongNameTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            if(song.isSelected())
                holder.mSongNameTextView.setTextColor(Color.RED);
           else
                holder.mSongNameTextView.setTextColor(Color.BLACK);
                //setTextWithColor(holder.mSongNameTextView,Color.RED);
            //else
              //  setTextWithColor(holder.mSongNameTextView,Color.BLACK);
            holder.mSongNameTextView.setText(song.getFileName());

        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }

        public void setSongs(List<Song> songs){
            mSongs=songs;
        }
    }



    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSongNameTextView;
        private Song mSong;

        public SongHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mSongNameTextView = (TextView) itemView.findViewById(R.id.list_item_song_name_text_view);


        }


        @Override
        public void onClick(View v) {
            //Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getId());
            //startActivity(intent);
            //LoadFiles(mCurDir.getText().toString()+"/"+mSongNameTextView.getText().toString());
            String strTemp;


            ltSongs.get(nSongSelected).setSelected(false);
            mSongRecyclerView.getAdapter().notifyItemChanged(nSongSelected);
            nSongSelected=this.getAdapterPosition();
            ltSongs.get(nSongSelected).setSelected(true);
            mSongRecyclerView.getAdapter().notifyItemChanged(nSongSelected);


            //strTemp=String.format("click song %d",nSongSelected);
            //Toast.makeText(getActivity(), strTemp, Toast.LENGTH_SHORT).show();



        }

        /*public void bindCrime(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText((mCrime.getDate().toString()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }*/
    }

}
