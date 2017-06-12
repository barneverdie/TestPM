package com.example.administrator.musicplayer;

import java.io.File;
import java.util.UUID;

/**
 * Created by Administrator on 2017/1/21.
 */

public class FileLabel{
    private UUID mId;
    /*private String mFileName;
    private String mFilePath;*/
    File mFile;
    private int  mIconId;
    private boolean mbSelected;

    public UUID getId() {
        return mId;
    }

    public boolean isDir() {return mFile.isDirectory();}

    public void setId(UUID id) {
        mId = id;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int nIcon) {
        mIconId=nIcon;
    }

    public String getFileName() {
        return this.mFile.getName();
    }

    //public void setFileName(String fileName) {
      //  this.mFileName = fileName;    }

    public String getFilePath() {
        return this.mFile.getPath();
    }

    /*public void setFilePath(String filePath) {
        mFilePath = filePath;
    }*/

    public boolean getSelected() {
        return mbSelected;
    }

    public void setSelected(boolean bSelected) {
        mbSelected =bSelected;
    }


     public FileLabel(File f)
    {
        mId=UUID.randomUUID();
        mFile=f;
        if(f.isFile())
            mIconId=R.drawable.normal_file;
        else
            mIconId=R.drawable.folder_file;
    }

    public FileLabel(UUID id) {
        mId = id;
    }



    public void addFile(File f){
        if(mId==null)
            mId=UUID.randomUUID();
        mFile=f;
        if(f.isFile())
            mIconId=R.drawable.normal_file;
        else
            mIconId=R.drawable.folder_file;

    }

   
}
