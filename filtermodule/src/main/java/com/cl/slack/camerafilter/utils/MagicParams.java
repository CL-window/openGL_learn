package com.cl.slack.camerafilter.utils;

import android.content.Context;
import android.os.Environment;

import com.cl.slack.camerafilter.widget.base.MagicBaseView;


/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicParams {
    public static Context context;
    public static MagicBaseView magicBaseView;

    public static String videoPath = Environment.getExternalStorageDirectory().getPath();
    public static String videoName = "MagicCamera_test.mp4";

    public static int beautyLevel = 5;

    public MagicParams() {

    }
}
