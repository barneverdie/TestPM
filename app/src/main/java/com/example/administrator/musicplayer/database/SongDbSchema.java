package com.example.administrator.musicplayer.database;

/**
 * Created by Administrator on 2017/2/8.
 */

public class SongDbSchema {
    public static final class SongTable {
        public static final String TABLENAME = "songs";

        public static final class Cols {


        public static final String UUID = "uuid";
        public static final String SONGNAME = "songname";
        public static final String FILEPATH = "filepath";
        public static final String SONGORDER = "songorder";
        public static final String DURATION = "duration";
    }
}
}
