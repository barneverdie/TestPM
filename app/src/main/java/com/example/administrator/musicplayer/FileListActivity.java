package com.example.administrator.musicplayer;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/19.
 */

public class FileListActivity extends SingleFragmentActivity {
    @Override
 protected   Fragment createFragment(){
        return new FileListFragment();
    }
    @Override public void onActivityResult(int nRequestCode,int nResultCode,Intent data) {
        Log.d("MUSICMPLAYERTAG","enter onactivity");
        super.onActivityResult(nRequestCode,nResultCode,data);
    }


}
