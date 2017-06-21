package com.cl.slack.wordgame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import smallgameengine.game.Game;
import smallgameengine.help.CONFIG;
import smallgameengine.help.FIXVALUE;
import smallgameengine.help.Font;
import smallgameengine.help.Texture;
import smallgameengine.image.Black;
import smallgameengine.image.Button;
import smallgameengine.image.Image;
import smallgameengine.image.Sprite;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.widget.SlidingDrawer;

public class OpenGlRenderer implements Renderer ,FIXVALUE{

	
	//public Image TEST;
	public Thread threadTwoSecond = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(4000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			black.isToBlack=true;
			scene=STARTPAGE_TO_HOMEPAGE;
		}
	});
	
	int gameinit=0;

	// 用于放在类里面来渲染
	public GL10 mainGl;
	public Context context = null;

	/** 游 戏 **/
	public int scene=START_TO_STARTPAGE;
	// 手机大小
	public int screenW;
	public int screenH;
	// 游戏屏幕大小
	public int gameScreenW = 720;// 游戏屏幕坐标
	public int gameScreenH = 1280;// 游戏屏幕坐标

	Image imgback;
	Image imgHomePageBack;
	Black black;
	Button help,setting,single,multiple;
	Sprite eyes;


	Image gameBack;
	Game game;
	Button alp[] = new Button[26];
	Button bntGrid[]=new Button[3];
	//	Font Fonttest;
	public OpenGlRenderer(Context ct) {
		// TODO Auto-generated constructor stub
		context = ct;
	}

	public void initGame() {
		threadTwoSecond.start();
		imgback = new Image(mainGl, Texture.textures[Texture.startback], gameScreenW * 1.0f / 2,
				gameScreenH * 1.0f / 2, -0.9f, gameScreenW * 1.0f,
				gameScreenH * 1.0f);

		imgHomePageBack = new Image(mainGl, Texture.textures[Texture.homepageback], gameScreenW * 1.0f / 2,
				gameScreenH * 1.0f / 2, -0.9f, gameScreenW * 1.0f,
				gameScreenH * 1.0f);

		black = new Black(mainGl, Texture.textures[Texture.black], gameScreenW * 1.0f / 2,
				gameScreenH * 1.0f / 2, -0.1f, gameScreenW * 1.0f,
				gameScreenH * 1.0f);


		help = new Button(mainGl, Texture.textures[Texture.homepagebnthelp], 280 * 1.0f,
				1280-1060 * 1.0f, -0.1f, 128 * 1.0f,
				128 * 1.0f);
		setting =  new Button(mainGl, Texture.textures[Texture.homepagebntsetting], 460 * 1.0f,
				1280-1060 * 1.0f, -0.1f, 128 * 1.0f,
				128 * 1.0f);
		single =  new Button(mainGl, Texture.textures[Texture.homepagebntsingle], 180 * 1.0f,
				1280-880 * 1.0f , -0.1f, 128 * 1.0f,
				128 * 1.0f);
		multiple =  new Button(mainGl, Texture.textures[Texture.homepagebntmul], 540 * 1.0f ,
				1280-880 * 1.0f, -0.1f, 128 * 1.0f,
				128 * 1.0f);


		eyes = new Sprite(mainGl, new int[]{Texture.textures[Texture.homepagebntabout0],Texture.textures[Texture.homepagebntabout1],Texture.textures[Texture.homepagebntabout2],
				Texture.textures[Texture.homepagebntabout3]}, 360 * 1.0f ,
				1280-880 * 1.0f, -0.1f, 160 * 1.0f,
				80 * 1.0f,3);


		gameBack =  new Image(mainGl, Texture.textures[Texture.gamepageback], gameScreenW * 1.0f / 2,
				gameScreenH * 1.0f / 2, -0.9f, gameScreenW * 1.0f,
				gameScreenH * 1.0f);





		bntGrid[0] = new Button(mainGl, Texture.textures[Texture.GRID1], 0* 1.0f ,
				1280-960 * 1.0f, -0.1f, 72 * 1.0f,
				72 * 1.0f);

		bntGrid[1] = new Button(mainGl, Texture.textures[Texture.GRID2], 0 * 1.0f ,
				1280-960 * 1.0f, -0.1f, 72 * 1.0f,
				72 * 1.0f);

		bntGrid[2] = new Button(mainGl, Texture.textures[Texture.GRID3], 0 * 1.0f ,
				1280-960 * 1.0f, -0.1f, 72 * 1.0f,
				72 * 1.0f);


		for(int i=0;i<10;i++)
		{
			alp[i] = new Button(mainGl, Texture.textures[Texture.alpA+i], (36+i*72) * 1.0f ,
					1280-1014 * 1.0f, -0.1f, 90 * 1.0f,
					120 * 1.0f);
		}

		for(int i=10;i<20;i++)
		{
			alp[i] = new Button(mainGl, Texture.textures[Texture.alpA+i], (36+(i-10)*72) * 1.0f ,
					1280-108-1014 * 1.0f, -0.1f, 90 * 1.0f,
					120 * 1.0f);
		}

		for(int i=20;i<26;i++)
		{
			alp[i] = new Button(mainGl, Texture.textures[Texture.alpA+i], (144+36+(i-20)*72) * 1.0f ,
					1280-108-108-1014 * 1.0f, -0.1f, 90 * 1.0f,
					120 * 1.0f);
		}

//		game = new Game(bntGrid,alp,this);

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		switch (scene) {
			/********************* 启动界面 *********************/
			case START_TO_STARTPAGE:
				imgback.draw();
				break;
			case STARTPAGE:
				imgback.draw();
				break;
			case STARTPAGE_TO_HOMEPAGE:
				imgback.draw();
				black.draw();
				if(black.isChanging() == false ){
					( ( Black )black ).isToBlack = false;
					scene = TO_HOMEPAGE;
				}
				break;


			/********************* 首页界面 *********************/
			case TO_HOMEPAGE:
				black.draw();
				imgHomePageBack.draw();
				if(black.isChanging() == false ){
					scene = HOMEPAGE;
				}
				break;
			case HOMEPAGE:
				imgHomePageBack.draw();
				help.draw();
				setting.draw();
				single.draw();
				multiple.draw();
				eyes.draw();
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


			/*********************单人界面 *********************/
			case HOMEPAGE_TO_SINGLEPLAY:
				break;
			case SINGLEPLAY:
				break;


			/*********************分类界面 *********************/
			case SINGLEPLAY_TO_CLASS:
				break;
			case CLASS:
				break;
			case CLASS_TO_MISSION:
				break;

			/*********************关卡界面 *********************/
			case MISSION:
				break;
			case MISSION_TO_GAME:
				break;


			/*********************游戏界面 *********************/
			case HOMEPAGE_TO_GAME:
				if(gameinit==1)
					gameinit=0;

				imgHomePageBack.draw();
				help.draw();
				setting.draw();
				single.draw();
				multiple.draw();
				eyes.draw();
				black.draw();
				if(black.isChanging()==false)
				{
					black.isToBlack=false;
					scene = TO_GAME;
				}
				break;
			case TO_GAME:

				if(gameinit==0){
					gameinit=1;
					game = new Game(bntGrid, alp, this);
				}
				gameBack.draw();
				black.draw();
				if(black.isChanging()==false)
				{
					black.isToBlack=false;
					scene = GAME;
				}
				break;
			case GAME:
				game.draw();
				gameBack.draw();
				break;
			case GAME_BACK_MISSION:
				break;
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		screenH=height;
		screenW=width;
		if(screenH*gameScreenW<screenW*gameScreenH)
		{
			width=(screenH*gameScreenW)/gameScreenH;
		}else
		{
			height=(screenW*gameScreenH)/gameScreenW;
		}
		gl.glViewport((screenW-width)>>1, (screenH-height)>>1, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0.0f, gameScreenW, 0.0f, gameScreenH);
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// TODO Auto-generated method stub
		mainGl = gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glClearDepthf(1);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glShadeModel(GL10.GL_SMOOTH);
		new Texture(context, gl);
		Texture.init1();

		initGame();

	}
}
