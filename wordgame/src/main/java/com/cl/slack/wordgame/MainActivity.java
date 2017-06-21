package com.cl.slack.wordgame;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.util.Map;

import smallgameengine.help.FIXVALUE;

public class MainActivity extends AppCompatActivity implements FIXVALUE {
    private OpenGlRenderer myrenderer = null;
    public SoundPool sp;
    public Map<Integer, Integer> map;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myrenderer = new OpenGlRenderer(this);
        mGestureDetector = new GestureDetector(this,
                new GLListener(myrenderer, this));

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(myrenderer);
        setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_UP){
            if(myrenderer.game!=null)
                myrenderer.game.touchUp(event);
        }

        if (mGestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {

        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // finish();
            switch (myrenderer.scene) {
                case HOMEPAGE:
                    new AlertDialog.Builder(this)
                            .setTitle("确定退出游戏？")
                        // 设置标题
                        // .setCustomTitle(View) // 以一个 View 作为标题
                        .setIcon(R.drawable.ic_launcher)
                        // 设置标题图片
                        // .setMessage("信息") // 需要显示的弹出内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置弹框的确认按钮所显示的文本，以及单击按钮后的响应行为
                            @Override
                            public void onClick(DialogInterface a0,
                                                int a1) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null).show();

                    break;
                case GAME:
                    myrenderer.scene=HOMEPAGE;
                    break;
            }
        }
        return true;
    }

    public void initSoundPool() {
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public void playSound(int id) {
        AudioManager am = (AudioManager) getSystemService(MainActivity.AUDIO_SERVICE);
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolume = am
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float audioRatio = audioCurrentVolume / audioMaxVolume;
        sp.play(map.get(id), audioRatio, audioRatio, 1, 0, 1);
    }

}
