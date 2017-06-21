package com.cl.slack.filterdemo.view.edit.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cl.slack.filterdemo.R;
import com.cl.slack.filterdemo.view.edit.ImageEditFragment;


public class ImageEditFilterView extends ImageEditFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_filter, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {

	}

	@Override
	protected boolean isChanged() {

		return false;
	}
}
