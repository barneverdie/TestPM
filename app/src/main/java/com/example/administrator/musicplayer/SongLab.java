package com.example.administrator.musicplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.musicplayer.database.SongBaseHelper;
import com.example.administrator.musicplayer.database.SongCursorWrapper;
import com.example.administrator.musicplayer.database.SongDbSchema;
import com.example.administrator.musicplayer.database.SongDbSchema.SongTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/2/25.
 */
public class SongLab {
    private static SongLab sSongLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static SongLab get(Context context) {
        if (sSongLab == null) {
            sSongLab=new SongLab(context);
        }

        return sSongLab;

    }

    private SongLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new SongBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Song song)
    {
        ContentValues values=new ContentValues();

        values.put(SongTable.Cols.UUID,song.getId().toString());
        values.put(SongTable.Cols.SONGNAME,song.getFileName());
        values.put(SongTable.Cols.FILEPATH,song.getFilePath());
        values.put(SongTable.Cols.SONGORDER,song.getOrder());
        values.put(SongTable.Cols.DURATION,song.getDuration());

        return values;
    }

    public void addSong(Song s) {
        ContentValues values=getContentValues(s);
        mDatabase.insert(SongTable.TABLENAME,null,values);

    }

    public void updateSong(Song song){
        String strSongId=song.getId().toString();
        ContentValues value=getContentValues(song);
        mDatabase.update(SongTable.TABLENAME,value,SongTable.Cols.UUID+"=?",new String[] {strSongId});

    }

    public List<Song> getSongs() {
        List<Song> mSongs=new ArrayList<>();

        SongCursorWrapper cursor=querySongs(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mSongs.add(cursor.getSong());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();}
        return mSongs;
    }

    public void deleteSong(UUID id){
        mDatabase.delete(SongTable.TABLENAME,SongTable.Cols.UUID+"=?",new String[]{id.toString()});

    }
    public void deleteAllSongs(){
        mDatabase.execSQL("delete from "+SongTable.TABLENAME);
    }

    public Song getSong(UUID id) {

        SongCursorWrapper cursor=querySongs(SongTable.Cols.UUID+"=?",new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getSong();
        }finally {
            cursor.close();}

    }

    private SongCursorWrapper querySongs(String strWhere, String[] strWhereArgs){
        Cursor cursor=mDatabase.query(SongTable.TABLENAME,null,strWhere,strWhereArgs,null,null,null);
        return new SongCursorWrapper(cursor);
    }

    public int getCount(){

        int nTemp;
        Cursor cursor = mDatabase.rawQuery("SELECT COUNT(*) AS COUNT FROM " +SongTable.TABLENAME,null);
        if (cursor != null) {
            cursor.moveToFirst();
            int j=cursor.getColumnIndex("COUNT");
             nTemp=cursor.getInt(cursor.getColumnIndex("COUNT"));
            return nTemp;
        }
        else {
            return -1;
        }
    }


}
