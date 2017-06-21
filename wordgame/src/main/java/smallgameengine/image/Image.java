
package smallgameengine.image;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


public class Image {
	public boolean Isdraw=true;
	public GL10 gl;
	public float x, y, z, w, h;
	public int textureId;
	public FloatBuffer vb, tb;
	
	public void setw(float width){
		w=width;
		float[] vertices = {
				-w * 0.5f, h * 0.5f,
				-w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f,
				w * 0.5f, -h * 0.5f,
		};
		
		ByteBuffer tmp = ByteBuffer.allocateDirect( vertices.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		vb = tmp.asFloatBuffer();
		vb.put( vertices );
		vb.position( 0 );
		
		float[] texCoords = {
				0, 0, 0, 1, 1, 0, 1, 1, 
		};
		
		tmp = ByteBuffer.allocateDirect( texCoords.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		tb = tmp.asFloatBuffer();
		tb.put( texCoords );
		tb.position( 0 );
	}
	public void seth(float height){
		h=height;
		float[] vertices = {
				-w * 0.5f, h * 0.5f,
				-w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f,
				w * 0.5f, -h * 0.5f,
		};
		
		ByteBuffer tmp = ByteBuffer.allocateDirect( vertices.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		vb = tmp.asFloatBuffer();
		vb.put( vertices );
		vb.position( 0 );
		
		float[] texCoords = {
				0, 0, 0, 1, 1, 0, 1, 1, 
		};
		
		tmp = ByteBuffer.allocateDirect( texCoords.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		tb = tmp.asFloatBuffer();
		tb.put( texCoords );
		tb.position( 0 );
	}
	public Image( GL10 gl, int textureId, float x, float y, float z, float w, float h ){
		this.gl = gl;
		this.x = x; this.y = y; this.z = z;
		this.w = w; this.h = h;
		this.textureId = textureId;
		
		float[] vertices = {
				-w * 0.5f, h * 0.5f,
				-w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f,
				w * 0.5f, -h * 0.5f,
		};
		
		ByteBuffer tmp = ByteBuffer.allocateDirect( vertices.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		vb = tmp.asFloatBuffer();
		vb.put( vertices );
		vb.position( 0 );
		
		float[] texCoords = {
				0, 0, 0, 1, 1, 0, 1, 1, 
		};
		
		tmp = ByteBuffer.allocateDirect( texCoords.length * 4 );
		tmp.order( ByteOrder.nativeOrder() );
		tb = tmp.asFloatBuffer();
		tb.put( texCoords );
		tb.position( 0 );
	}
	
	public void draw(){
		if(Isdraw){
		gl.glPushMatrix();
		gl.glTranslatef( x, y, z );
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glEnable( GL10.GL_ALPHA_TEST );
		gl.glAlphaFunc( GL10.GL_GREATER, 0.5f );
		gl.glEnable( GL10.GL_CULL_FACE );
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textureId );
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glVertexPointer( 2, GL10.GL_FLOAT, 0, vb );
		gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, tb );
		gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 0, 4 );
		gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glPopMatrix();
		}
	}
	
	public boolean isTouch( float x, float y ){
		if( x < this.x - w * 0.5f || x > this.x + w * 0.5f ) return false;
		if( y < this.y - h * 0.5f || y > this.y + h * 0.5f ) return false;
		return true;
	}
}

