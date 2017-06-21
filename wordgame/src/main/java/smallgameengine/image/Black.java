package smallgameengine.image;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import smallgameengine.help.*;

public class Black extends Image implements CONFIG{
	public int alpha;
	public boolean isToBlack;
	public Black(GL10 gl, int textureId, float x, float y, float z, float w,
			float h) {
		super(gl, textureId, x, y, z, w, h);
		// TODO Auto-generated constructor stub
		alpha = 0;
		isToBlack = false;
	}

	public float TimeToBlack=TIMETOBLACK;
	public int Mid=(int)TIMETOBLACKMID;
	public void draw(){
		if( isToBlack && alpha < TimeToBlack ){
			if( alpha < Mid ) alpha += 3; 
			else alpha += 1;
		}
		
		
		
		if( !isToBlack && alpha > 0 ){
			if( alpha > Mid ) alpha -= 1; 
			else alpha -= 3;
		}
//		if( alpha < 1 ) return;
		
		gl.glPushMatrix();
		gl.glTranslatef( x, y, z );
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glDisable( GL10.GL_ALPHA_TEST );
		gl.glEnable( GL10.GL_BLEND );
		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
		gl.glColor4f( 1, 1, 1, alpha / TimeToBlack );
		gl.glEnable( GL10.GL_CULL_FACE );
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textureId );
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glVertexPointer( 2, GL10.GL_FLOAT, 0, vb );
		gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, tb );
		gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 0, 4 );
		gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glDisable( GL10.GL_BLEND );
		gl.glColor4f( 1, 1, 1, 1 );
		gl.glPopMatrix();
	}
	
	public boolean isChanging(){
		return (isToBlack && alpha < TimeToBlack) ||( !isToBlack && alpha > 0);
	}
	
	public void setBlack( boolean a ){
		if( a ){
			alpha =(int) TimeToBlack;
			isToBlack = true;
		}
		else{
			alpha = 0;
			isToBlack = false;
		}
	}
}
