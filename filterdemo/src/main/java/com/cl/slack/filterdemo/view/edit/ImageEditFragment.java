package com.cl.slack.filterdemo.view.edit;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

public abstract class ImageEditFragment extends Fragment{
	protected onHideListener mOnHideListener;
	
	public void onHide(){
		if(isChanged()){

		}else{
			mOnHideListener.onHide();
		}
	}
	
	public void setOnHideListener(onHideListener l){
		this.mOnHideListener = l;
	}
	
	protected abstract boolean isChanged();
	
	protected void onDialogButtonClick(DialogInterface dialog){
		if(mOnHideListener != null)
			mOnHideListener.onHide();
		dialog.dismiss();
	}
	
	public interface onHideListener{
		void onHide();
	}
}
