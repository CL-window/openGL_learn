
package smallgameengine.image;

import javax.microedition.khronos.opengles.GL10;

public class RotateButton extends Image {
	public float angle, speed;
	
	public RotateButton(GL10 gl, int textureId, float x, float y, float z,
			float w, float h) {
		super(gl, textureId, x, y, z, w, h);
		angle = 0;
		speed = 0.3f;
	}
	public void setSpeed(float s){
		speed=s;
	}
	@Override
	public void draw() {
		if( angle + speed > 360 ) angle = 0;
		else angle += speed;
		
		gl.glPushMatrix();
		gl.glTranslatef( x, y, z );
		gl.glRotatef( angle, 0, 0, 1 );
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
