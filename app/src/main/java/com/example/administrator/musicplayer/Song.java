package com.example.administrator.musicplayer;

import java.util.UUID;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Song {
    private UUID mId;

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public int getDuration() {
        return mLong;
    }

    public void setDuration(int aLong) {
        mLong = aLong;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int sort) {
        mOrder = sort;
    }

    public void setPlaying(boolean bPlaying) {mPlaying=bPlaying;}
    public boolean isPlaying() {return mPlaying;}
    public void setSelected(boolean bSelected) {mSelected=bSelected;}
    public boolean isSelected() {return mSelected;}



    public Song()
    {
        mId=UUID.randomUUID();

    }


    public Song(Song s)
    {
        mId=s.getId();
        mFileName=s.getFileName();
        mFilePath=s.getFilePath();
        mLong=s.getDuration();
        mOrder=s.getOrder();
        mPlaying=false;
        mSelected=false;
    }

    public Song(UUID id){
        mId=id;
    }


    private String mFileName;
    private String mFilePath;
    private int mLong;
    private int mOrder;
    private boolean mPlaying;
    private boolean mSelected;
}
