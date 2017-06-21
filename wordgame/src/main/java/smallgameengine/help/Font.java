package smallgameengine.help;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;

public class Font {
	public boolean Isdraw = true;
	public GL10 gl;
	public float x, y, z, w, h;
	public int textureId[];
	public FloatBuffer vb, tb;

	private String str;

	public void setw(float width) {
		w = width;
		float[] vertices = { -w * 0.5f, h * 0.5f, -w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f, w * 0.5f, -h * 0.5f, };

		ByteBuffer tmp = ByteBuffer.allocateDirect(vertices.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		vb = tmp.asFloatBuffer();
		vb.put(vertices);
		vb.position(0);

		float[] texCoords = { 0, 0, 0, 1, 1, 0, 1, 1, };

		tmp = ByteBuffer.allocateDirect(texCoords.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		tb = tmp.asFloatBuffer();
		tb.put(texCoords);
		tb.position(0);
	}

	public void seth(float height) {
		h = height;
		float[] vertices = { -w * 0.5f, h * 0.5f, -w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f, w * 0.5f, -h * 0.5f, };

		ByteBuffer tmp = ByteBuffer.allocateDirect(vertices.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		vb = tmp.asFloatBuffer();
		vb.put(vertices);
		vb.position(0);

		float[] texCoords = { 0, 0, 0, 1, 1, 0, 1, 1, };

		tmp = ByteBuffer.allocateDirect(texCoords.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		tb = tmp.asFloatBuffer();
		tb.put(texCoords);
		tb.position(0);
	}

	public Font(GL10 gl, String str, float x, float y, float z,  float h) {
		this.gl = gl;
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
		this.str = str;
		
		textureId = new int[1];
		gl.glGenTextures(1, textureId, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		Bitmap bmp = initBitmap(str);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0,bmp , 0);
		if(bmp!=null&&!bmp.isRecycled()){bmp.recycle(); bmp = null;} 
		System.gc();
		
		
		float[] vertices = { -w * 0.5f, h * 0.5f, -w * 0.5f, -h * 0.5f,
				w * 0.5f, h * 0.5f, w * 0.5f, -h * 0.5f, };

		ByteBuffer tmp = ByteBuffer.allocateDirect(vertices.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		vb = tmp.asFloatBuffer();
		vb.put(vertices);
		vb.position(0);

		float[] texCoords = { 0, 0, 0, 1, 1, 0, 1, 1, };

		tmp = ByteBuffer.allocateDirect(texCoords.length * 4);
		tmp.order(ByteOrder.nativeOrder());
		tb = tmp.asFloatBuffer();
		tb.put(texCoords);
		tb.position(0);
	}
	
	
	
	public void draw() {
		if (Isdraw) {
			gl.glPushMatrix();
			gl.glTranslatef(x+w/2, y+h/2, z);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glAlphaFunc(GL10.GL_GREATER, 0.5f);
			gl.glEnable(GL10.GL_CULL_FACE);// 根据函数glCullFace要求启用隐藏图形材料的面。
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vb);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tb);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glPopMatrix();
		}
	}

	public boolean isTouch(float x, float y) {
		if (x < this.x - w * 0.5f || x > this.x + w * 0.5f)
			return false;
		if (y < this.y - h * 0.5f || y > this.y + h * 0.5f)
			return false;
		return true;
	}

	private int fontColor=Color.BLACK;
	public void setFontColor(int c){
		fontColor=c;
	}
	private int fontSize=100;
	public void setFontSize(int fontSize){
		this.fontSize=fontSize;
	}
	public int strSize;
	public int len;
	
	public Bitmap initBitmap(String str) {
		strSize=str.length();
		if((strSize&(strSize-1))==0){
			len=strSize;
			
		}else
		{
			int t=strSize;
			int temp = t;
			strSize=str.length();
			while(t!=0)
			{
				temp=t;
				t=t&(t-1);
			}
			len=temp<<1;
		}
		w=h*len;
		Bitmap bmp;
		String mstrTitle = str;
		bmp = Bitmap.createBitmap(len*128, 128, Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmp);
		Paint p = new Paint();
		String familyName = "����";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setColor(fontColor);
		p.setTypeface(font);
		p.setTextSize(fontSize);
		canvasTemp.drawText(mstrTitle, 17, 105, p);
		return bmp;
	}

	@Override
	protected void finalize() {
		gl.glDeleteTextures(1, textureId,0);
		System.out.println("Font����");
	}
}
