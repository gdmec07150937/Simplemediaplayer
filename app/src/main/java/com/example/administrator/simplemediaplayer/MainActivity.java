package com.example.administrator.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="SimpleMediaPlayer";
    private MediaPlayer mMediaPlayer;
    private String mpath;
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri !=null){
            mpath = uri.getPath();
            setTitle(mpath);
            if(intent.getType().contains("audio")){
                setContentView(android.R.layout.simple_list_item_1);
                mMediaPlayer = new MediaPlayer();
                try{
                    mMediaPlayer.setDataSource(mpath);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(intent.getType().contains("video")){
                mVideoView = new VideoView(this);
                mVideoView.setVideoPath(mpath);
                mVideoView.setMediaController(new MediaController(this));
                setContentView(mVideoView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"暂停");
        menu.add(0,2,0,"开始");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case 1:
               if(mMediaPlayer ==null||!mMediaPlayer.isPlaying()){
                   Toast.makeText(this,"没有音乐文件，不需要",Toast.LENGTH_SHORT).show();
               }else{
                   mMediaPlayer.pause();
               }
               break;
           case 2:
               if(mMediaPlayer==null){
                   Toast.makeText(this,"没有选中音乐文件，请到文件夹中点击",Toast.LENGTH_SHORT).show();
               }else{
                   mMediaPlayer.start();
               }
               break;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
        }
        if(mVideoView!=null){
            mVideoView.stopPlayback();
        }

    }
}
