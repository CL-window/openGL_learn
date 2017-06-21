
package smallgameengine.help;

import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.cl.slack.wordgame.R;

public class Texture {
	private static Context context;
	private static GL10 gl;
	private static Bitmap bitmap;
	public static boolean isInitial1, isInitial2, isInitial3, isInitial4, isInitial5;
	public static int[] textures;
	
	public static final int nTextures = 100;
	public static int startback=0;
	public static int homepageback=1;
	public static int homepagebntmul=2;
	public static int homepagebntsingle=3;
	public static int homepagebnthelp=4;
	public static int homepagebntsetting=5;
	public static int homepagebntabout0=6;
	public static int homepagebntabout1=7;
	public static int homepagebntabout2=8;
	public static int homepagebntabout3=9;
	public static int gamepageback=10;
	
	public static int  alpA=11;
	public static int  alpB=12;
	public static int  alpC=13;
	public static int  alpD=14;
	public static int  alpE=15;
	public static int  alpF=16;
	public static int  alpG=17;
	public static int  alpH=18;
	public static int  alpI=19;
	public static int  alpJ=20;
	public static int  alpK=21;
	public static int  alpL=22;
	public static int  alpM=23;
	public static int  alpN=24;
	public static int  alpO=25;
	public static int  alpP=26;
	public static int  alpQ=27;
	public static int  alpR=28;
	public static int  alpS=29;
	public static int  alpT=30;
	public static int  alpU=31;
	public static int  alpV=32;
	public static int  alpW=33;
	public static int  alpX=34;
	public static int  alpY=35;
	public static int  alpZ=36;
	
	public static int  GRID1=37;
	public static int  GRID2=38;
	public static int  GRID3=39;



	public static int black=99;
	
	public Texture( Context context, GL10 gl ){
		Texture.context = context;
		Texture.gl = gl;
		isInitial1 = isInitial2 = isInitial3 = isInitial4 = isInitial5 = false;
		textures = new int[ nTextures ];
		gl.glGenTextures( nTextures, textures, 0 );
	}
	
	public static void init1(){
		if( isInitial1 ) return; 
		else isInitial1 = true;
		/**************s首页资源********************/
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.back);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ startback ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );

		if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.homepageback);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepageback ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.black);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ black ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}


		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.bnthelp);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebnthelp ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}


		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.bntmultiplayer);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntmul ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}


		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.bntsetting);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntsetting ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}


		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.bntsingleplayer);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntsingle ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		////////////////
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.spritesabout0);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntabout0 ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.spritesabout1);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntabout1 ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.spritesabout2);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D,textures[ homepagebntabout2 ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.spritesabout3);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ homepagebntabout3 ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}



		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.gameback);//把资源转化为位图
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ gamepageback ] );
		gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;}


		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.a);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpA ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.b);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpB ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.c);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpC ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.d);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpD ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.e);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpE ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.f);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpF ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.g);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpG ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.h);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpH ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.i);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpI ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.j);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpJ ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.k);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpK ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.l);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpL ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.m);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpM ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.n);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpN ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.o);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpO ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.p);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpP ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.q);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpQ ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.r);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpR ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.s);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpS ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.t);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpT ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.u);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpU ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.v);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpV ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.w);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpW ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.x);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpX ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.y);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpY ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.z);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ alpZ ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 

		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.gridempty);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ GRID1 ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.gridunkonw);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ GRID2 ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 
		bitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.gridjiaodian);gl.glBindTexture( GL10.GL_TEXTURE_2D, textures[ GRID3 ] );gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );if(bitmap != null && !bitmap.isRecycled()){bitmap.recycle(); bitmap = null;} 

		
		
		System.gc();
	}
	
	public static void init2(){
		if( isInitial2 ) return; else isInitial2 = true;

		
	}
	
	
	public static void init4(){
		if( isInitial4 ) return; else isInitial4 = true;
		
		
	}
	
	public static void init5(){
		if( isInitial5 ) return; else isInitial5 = true;
		
		
	}
	
}