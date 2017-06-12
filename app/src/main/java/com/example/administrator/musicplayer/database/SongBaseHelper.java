package com.example.administrator.musicplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.administrator.musicplayer.Song;
import com.example.administrator.musicplayer.database.SongDbSchema;

import static com.example.administrator.musicplayer.database.SongDbSchema.SongTable.*;

/**
 * Created by Administrator on 2017/2/8.
 */

public class SongBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SongBaseHelper";
    private static final int VERSION=1;
    private static final String DATABASE_NAME="songBase.db";

    public SongBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);

    }

    @Override public void onCreate(SQLiteDatabase db){
        String strTemp;

        strTemp=String.format("create table %s(_id integer primary key autoincrement,%s ,%s ,%s ,%s ,%s)",
                TABLENAME,Cols.UUID, Cols.SONGNAME, Cols.FILEPATH, Cols.SONGORDER,Cols.DURATION);
        db.execSQL(strTemp);




        
    }



    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strTemp;
        strTemp=String.format("create table %s(_id integer primary key autoincrement,%s ,%s ,%s ,%s ,%s)",
                TABLENAME,Cols.UUID, Cols.SONGNAME, Cols.FILEPATH, Cols.SONGORDER,Cols.DURATION);
        db.execSQL("drop table songs");
        db.execSQL(strTemp);

    }


}
