package com.example.administrator.musicplayer.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.administrator.musicplayer.Song;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/2.
 */

public class SongCursorWrapper extends CursorWrapper {
    public SongCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Song getSong(){
        String strUUID=getString(getColumnIndex(SongDbSchema.SongTable.Cols.UUID));
        String mFileName=getString(getColumnIndex(SongDbSchema.SongTable.Cols.SONGNAME));
        String mFilePath=getString(getColumnIndex(SongDbSchema.SongTable.Cols.FILEPATH));
        int nDuration=getInt(getColumnIndex(SongDbSchema.SongTable.Cols.DURATION));
        int nOrder=getInt(getColumnIndex(SongDbSchema.SongTable.Cols.SONGORDER));

        Song song=new Song(UUID.fromString(strUUID));
        song.setFileName(mFileName);
        song.setFilePath(mFilePath);
        song.setDuration(nDuration);
        song.setOrder(nOrder);

        return song;
    }

}
