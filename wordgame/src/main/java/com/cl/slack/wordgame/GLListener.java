/*
 * �������� 2013-5-3
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.cl.slack.wordgame;

import smallgameengine.help.CONFIG;
import smallgameengine.help.FIXVALUE;
import smallgameengine.image.Black;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GLListener extends SimpleOnGestureListener implements FIXVALUE,
		CONFIG {
	private Context context;
	private OpenGlRenderer renderer;

	public GLListener(OpenGlRenderer renderer, Context context) {
		this.renderer = renderer;
		this.context = context;
	}

	@Override
	// 双击触发：双击的第二下Touch down时触
	public boolean onDoubleTap(MotionEvent e) {
		// TODO 自动生成方法存根
		// System.out.println("onDoubleTap Test");
		return super.onDoubleTap(e);
	}



	@Override
	// 双击的第二下Touch down和up都会触发
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO 自动生成方法存根
		// System.out.println("onDoubleTapEvent Test");
		return super.onDoubleTapEvent(e);
	}

	@Override
	// 按下去
	public boolean onDown(MotionEvent event) {
		// TODO 自动生成方法存根
		//System.out.println("onDown Test");

		float x = event.getX();
		float y = renderer.screenH - event.getY();
		float gameX = ((x / renderer.screenW) * renderer.gameScreenW);
		float gameY = ((y / renderer.screenH) * renderer.gameScreenH);
		// Log.v("www",""+gameX+":"+gameY);
		switch (renderer.scene) {
			/********************* 启动界面 *********************/
			case START_TO_STARTPAGE:

				break;
			case STARTPAGE:

				break;
			case STARTPAGE_TO_HOMEPAGE:

				break;

			/********************* 首页界面 *********************/
			case TO_HOMEPAGE:

				break;
			case HOMEPAGE:
				if(renderer.single.isTouch(gameX, gameY)){
					renderer.black.isToBlack = true;
					renderer.scene = HOMEPAGE_TO_GAME;
				}
				if(renderer.setting.isTouch(gameX, gameY))
				{
					Intent intent=new Intent();
					intent.setClass(context, SetGame.class);
					context.startActivity(intent);
				}
				break;

			/********************* 帮助界面 *********************/
			case HOMEPAGE_TO_HELP:
				break;
			case HELP:
				break;
			case HELP_BACK_HOMEPAGE:
				break;

			/********************* 关于界面 *********************/
			case HOMEPAGE_TO_ABOUT:
				break;
			case ABOUT:
				break;
			case ABOUT_BACK_HOMEPAGE:
				break;

			/********************* 设置界面 *********************/
			case HOMEPAGE_TO_SETTING:
				break;
			case SETTING:
				break;
			case SETTING_BACK_HOMEPAGE:
				break;

			/********************* 单人界面 *********************/
			case HOMEPAGE_TO_SINGLEPLAY:
				break;
			case SINGLEPLAY:
				break;

			/********************* 分类界面 *********************/
			case SINGLEPLAY_TO_CLASS:
				break;
			case CLASS:
				break;
			case CLASS_TO_MISSION:
				break;

			/********************* 关卡界面 *********************/
			case MISSION:
				break;
			case MISSION_TO_GAME:
				break;

			/********************* 游戏界面 *********************/
			case GAME:
				renderer.game.touchDown(gameX, gameY);
				break;
			case GAME_BACK_MISSION:
				break;
		}
		return super.onDown(event);
	}

	@Override
	// Touch了滑动一点距离后，up时触
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						   float velocityY) {

		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public void onLongPress(MotionEvent event) {
		// TODO 自动生成方法存根
		float x = event.getX();
		float y = renderer.screenH - event.getY();
		float gameX = ((x / renderer.screenW) * renderer.gameScreenW);
		float gameY = ((y / renderer.screenH) * renderer.gameScreenH);
		// Log.v("www",""+gameX+":"+gameY);
		switch (renderer.scene) {
			/********************* 启动界面 *********************/
			case START_TO_STARTPAGE:
				break;
			case STARTPAGE:
				break;
			case STARTPAGE_TO_HOMEPAGE:
				break;
			/********************* 首页界面 *********************/
			case TO_HOMEPAGE:
				break;
			case HOMEPAGE:
				break;
			/********************* 帮助界面 *********************/
			case HOMEPAGE_TO_HELP:
				break;
			case HELP:
				break;
			case HELP_BACK_HOMEPAGE:
				break;
			/********************* 关于界面 *********************/
			case HOMEPAGE_TO_ABOUT:
				break;
			case ABOUT:
				break;
			case ABOUT_BACK_HOMEPAGE:
				break;
			/********************* 设置界面 *********************/
			case HOMEPAGE_TO_SETTING:
				break;
			case SETTING:
				break;
			case SETTING_BACK_HOMEPAGE:
				break;
			/********************* 单人界面 *********************/
			case HOMEPAGE_TO_SINGLEPLAY:
				break;
			case SINGLEPLAY:
				break;
			/********************* 分类界面 *********************/
			case SINGLEPLAY_TO_CLASS:
				break;
			case CLASS:
				break;
			case CLASS_TO_MISSION:
				break;
			/********************* 关卡界面 *********************/
			case MISSION:
				break;
			case MISSION_TO_GAME:
				break;
			/********************* 游戏界面 *********************/
			case GAME:
				break;
			case GAME_BACK_MISSION:
				break;
		}
		super.onLongPress(event);
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY) {
		// TODO 自动生成方法存根

		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	// 没有滚动之前的
	public void onShowPress(MotionEvent e) {
		// TODO 自动生成方法存根
		//System.out.println("onShowPress");
		super.onShowPress(e);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO 自动生成方法存根
		//System.out.println("onSingleTapConfirmed");
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		// TODO 自动生成方法存根
		float x = event.getX();
		float y = renderer.screenH - event.getY();
		float gameX = ((x / renderer.screenW) * renderer.gameScreenW);
		float gameY = ((y / renderer.screenH) * renderer.gameScreenH);
		// Log.v("www",""+gameX+":"+gameY);
		switch (renderer.scene) {
			/********************* 启动界面 *********************/
			case START_TO_STARTPAGE:
				break;
			case STARTPAGE:
				break;
			case STARTPAGE_TO_HOMEPAGE:
				break;
			/********************* 首页界面 *********************/
			case TO_HOMEPAGE:
				break;
			case HOMEPAGE:
				break;
			/********************* 帮助界面 *********************/
			case HOMEPAGE_TO_HELP:
				break;
			case HELP:
				break;
			case HELP_BACK_HOMEPAGE:
				break;
			/********************* 关于界面 *********************/
			case HOMEPAGE_TO_ABOUT:
				break;
			case ABOUT:
				break;
			case ABOUT_BACK_HOMEPAGE:
				break;
			/********************* 设置界面 *********************/
			case HOMEPAGE_TO_SETTING:
				break;
			case SETTING:
				break;
			case SETTING_BACK_HOMEPAGE:
				break;
			/********************* 单人界面 *********************/
			case HOMEPAGE_TO_SINGLEPLAY:
				break;
			case SINGLEPLAY:
				break;
			/********************* 分类界面 *********************/
			case SINGLEPLAY_TO_CLASS:
				break;
			case CLASS:
				break;
			case CLASS_TO_MISSION:
				break;
			/********************* 关卡界面 *********************/
			case MISSION:
				break;
			case MISSION_TO_GAME:
				break;
			/********************* 游戏界面 *********************/
			case GAME:
				break;
			case GAME_BACK_MISSION:
				break;
		}
		return super.onSingleTapUp(event);
	}
}
