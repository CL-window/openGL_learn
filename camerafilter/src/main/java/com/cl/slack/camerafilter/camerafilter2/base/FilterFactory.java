package com.cl.slack.camerafilter.camerafilter2.base;

import android.content.Context;

import com.cl.slack.camerafilter.camerafilter2.filter.CameraFilter;
import com.cl.slack.camerafilter.camerafilter2.filter.CameraFilterBeauty;
import com.cl.slack.camerafilter.camerafilter2.filter.IFilter;


public class FilterFactory {
    private static int[] mCurveArrays = new int[]{

    };

    private FilterFactory() {
    }

    /**
     *
     * @param context
     * @param index
     * @return
     */
    public static IFilter getCameraFilter(Context context, int index) {

        switch (index) {
            case 0:
                return new CameraFilter(context);
            case 1:
                return new CameraFilterBeauty(context);

        }

        return new CameraFilter(context);
    }
}

