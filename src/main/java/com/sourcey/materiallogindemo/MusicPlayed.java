package com.sourcey.materiallogindemo;import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2/4/2018.
 */

public class MusicPlayed extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener {
    private ImageButton btn_play_pause;
    private SeekBar seekbar ;
    private TextView textView;

    // private  VusikView musicview;

    private MediaPlayer mediaPlayer;
    private int mediaFileLength;
    private int realTimeLength;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_played);
        BottomNavigationView bottomNavigationView= (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.browse:
                        Intent intent = getIntent();
                        startActivity(new Intent(MusicPlayed.this, MusicActivity.class));
                        return true;
                    case R.id.playlist:
                        startActivity(new Intent(MusicPlayed.this, MusicPlayed.class));
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(MusicPlayed.this,Settings.class));
                        return true;
                }
                return true;
            }
        });

        // musicview = (VusikView)findViewById(R.id.music_view);
        seekbar = (SeekBar)findViewById(R.id.seekbar);
        seekbar.setMax(99);
        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(mediaPlayer.isPlaying()){
                    SeekBar seekBar = (SeekBar)v;
                    int playPosition = (mediaFileLength/100)*seekBar.getProgress();
                    mediaPlayer.seekTo(playPosition);
                }
                return false;
            }
        });
        textView = (TextView)findViewById(R.id.text_timer) ;
        btn_play_pause = (ImageButton) findViewById(R.id.btn_play_pause);
        btn_play_pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(MusicPlayed.this);

                AsyncTask<String,String,String> mp3play= new AsyncTask<String,String,String>(){

                    @Override
                    protected void onPreExecute(){
                        mDialog.setMessage("Please Wait!");
                        mDialog.show();
                    }
                    @Override
                    protected String doInBackground(String... params) {
                        try{
                            mediaPlayer.setDataSource(params[0]);
                            mediaPlayer.prepare();
                        }
                        catch (Exception e){

                        }
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String s){
                        mediaFileLength= mediaPlayer.getDuration();
                        realTimeLength = mediaFileLength;
                        if(!mediaPlayer.isPlaying()){
                            mediaPlayer.start();
                            btn_play_pause.setImageResource(R.drawable.ic_pause);
                        }
                        else {
                            mediaPlayer.pause();
                            btn_play_pause.setImageResource(R.drawable.ic_play);
                        }
                        updateSeekBar ();
                        mDialog.dismiss();

                    }
                };

                mp3play.execute("https://papulose-schematics.000webhostapp.com/Mighty%20Sam%20McClain%20-%20Im%20Tired%20of%20These%20Blues.mp3");
                //musicview.start();

            }}
        );
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);




    }



    private void updateSeekBar() {
        seekbar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLength*100)));
        if(mediaPlayer.isPlaying()){
            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                    realTimeLength-=1000;
                    textView.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(realTimeLength),
                            TimeUnit.MILLISECONDS.toSeconds(realTimeLength) -
                                    TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(realTimeLength))));
                }
            };

            handler.postDelayed(updater,1000);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekbar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btn_play_pause.setImageResource(R.drawable.ic_play);
        //musicview.StopNotesFall();

    }



}






