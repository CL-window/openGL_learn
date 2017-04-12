package com.example.mix;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

import java.util.Locale;

/**
 * Chronometer
 * 有一个问题，停止即使，界面不动了，但如果再次点击计时，会发现停止的这段时间也是计时的
 * stop()方法只是停止刷新计时器的时间显示，而并没有真正停止计时
 * 解决： 记录当前总时间
 * 显示 时分秒  TODO
 * created by slack
 * on 17/4/12 下午5:34
 */
public class Chronometr extends AppCompatActivity {

    private Chronometer chronometer = null;
    private long current; // 记录总时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometr);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    public void onStart(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime() - current);
        chronometer.start();
    }

    public void onStop(View view) {
        current = SystemClock.elapsedRealtime()- chronometer.getBase();// 保存这次记录了的时间
        chronometer.stop();
    }

    public void onReset(View view) {
        current = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * int --> HH:mm:ss
     * @param cnt
     * @return
     */
    private String getStringTime(int cnt) {
        int hour = cnt/3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
    }
}
