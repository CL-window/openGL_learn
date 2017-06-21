package com.cl.slack.camerafilter.camerafilter2.base;

import android.content.Context;

import com.cl.slack.camerafilter.camerafilter2.filter.IFilter;


public interface onExtFilterListener {
    IFilter onCreateExtFilter(Context context, int index);
}
