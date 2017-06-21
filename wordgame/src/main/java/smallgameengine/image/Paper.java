
package smallgameengine.image;

import javax.microedition.khronos.opengles.GL10;

public class Paper extends Image {
	public boolean IsMoving=false;
	public int NumOfT;//有多少个阶段
	public int NowNum=0;//有多少个阶段
	public float Value []=new float [500];//每个阶段有两个值：速度、距离
	public Paper(GL10 gl, int textureId, float x, float y, float z, float w,
			float h) {
		super(gl, textureId, x, y, z, w, h);
		// TODO �Զ����ɹ��캯�����
		NowNum=0;
		int temp=120;
		NumOfT = 720*2/temp;
		
		for(int i=0;i<NumOfT;i++){
			
			Value[i]=(float) ((float)temp-(float)i*(float)temp/(float)(NumOfT-1));
		}
	}
	public void setMove(){
		IsMoving=true;
		NowNum=0;
	}
	public boolean IsMoving(){
		return IsMoving;
	}
	
	public void set(float a[],int n){
		if(n>500)n=500;
		NumOfT=n;
		IsMoving=true;
		for(int i=0;i<n;i++){
			//Value[i]=a[i];
		}
	}
	public void draw(){
		if(IsMoving&&NowNum<NumOfT)
		{
			x=x+Value[NowNum];NowNum++;
		}else if(NowNum>=NumOfT){
			IsMoving=false;
		}
		super.draw();
	}
}
