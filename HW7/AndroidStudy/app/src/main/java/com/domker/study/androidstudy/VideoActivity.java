package com.domker.study.androidstudy;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private VideoView videoView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.yuminhong));
        init();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
            }
        });
        setGestureDetector();
    }
    private void  init(){
        MediaController mMediaController = new MediaController(this);
        videoView.setMediaController(mMediaController);
        videoView.start();
        videoView.requestFocus();
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            int sec = savedInstanceState.getInt("time");
            videoView.seekTo(sec);
            float lightness = savedInstanceState.getFloat("lightness");
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = lightness;
            getWindow().setAttributes(layoutParams);
            int volume = savedInstanceState.getInt("volume");
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void setGestureDetector() {
        gestureDetector = new GestureDetector(this,
                new GestureDetector.OnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return false;
                    }

                    @Override
                    public void onShowPress(MotionEvent motionEvent) {

                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        return false;
                    }

                    @Override
                    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent motionEvent) {

                    }


                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                        if (e1.getY() - e2.getY() > 0.5 && Math.abs(distanceY) > 0.5) {
                            if (e1.getX() > 500) {
                                addLightness(10);
                            } else {
                                addVolume(1);
                            }
                        }
                        if (e1.getY() - e2.getY() < 0.5 && Math.abs(distanceY) > 0.5) {
                            if (e1.getX() > 500) {
                                addLightness(-10);
                            } else {
                                addVolume(-1);
                            }
                        }
                        return true;
                    }

                }
        );
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int sec = videoView.getCurrentPosition();
        outState.putInt("time", sec);
        float lightness = getWindow().getAttributes().screenBrightness;
        outState.putFloat("lightness", lightness);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        outState.putInt("volume", volume);
    }

    public void addLightness(float lightness) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness += lightness / 255f;
        if (layoutParams.screenBrightness > 1) {
            layoutParams.screenBrightness = 1;
        } else if (layoutParams.screenBrightness < 0.2) {
            layoutParams.screenBrightness = 0.2f;
        }
        getWindow().setAttributes(layoutParams);
    }

    public void addVolume(int volume) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume += volume;
        if (currentVolume < 0) {
            currentVolume = 0;
        }
        if (currentVolume > max) {
            currentVolume = max;
        }

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_PLAY_SOUND);
    }


}
