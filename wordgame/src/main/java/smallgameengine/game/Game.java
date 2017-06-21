package smallgameengine.game;

import android.view.MotionEvent;

import com.cl.slack.wordgame.OpenGlRenderer;
import com.cl.slack.wordgame.R;

import java.io.InputStream;

import smallgameengine.help.FileAccess;
import smallgameengine.help.Font;
import smallgameengine.help.TxtReader;
import smallgameengine.image.Button;

public class Game {
	private OpenGlRenderer renderer;

	//游戏界面分成三块
	//字幕     grid  按钮

	/* 机制：
	 * 1、手点击下去将之前的先透明，把isDrawQV[lastDrawQV]=isDrawQH[lastDrawQH]=false;
	 * 2、把当前的isDrawQV[i]=isDrawQH[j]=true;
	 * 3、如果此时的i、j都是canBeDrawQV[i]==true&&canBeDrawQH[j]==true   那么将一个移动到1040 +72否则是1040
	 * */
	//字幕
	//目前的行列   更换的时候可以遮住
	private int lastDrawQV;
	private int lastDrawQH;
	//表示是否是一个行或者列
	private boolean canBeDrawQV[] = new boolean[10];
	private boolean canBeDrawQH[]  = new boolean[10];
	//可以draw吗？
	private boolean isDrawQV[] = new boolean[10];
	private boolean isDrawQH[]  = new boolean[10];
	//Font指向
	private Font fontQuestionVertival[] = new Font[10];
	private Font fontQuestionHorizontal[] = new Font[10];

	/**游戏图片
	 * 游戏分成
	 * 1、背景层（背景图片）
	 * 2、效果层（例如：焦点、需要填词的位置（黄色））
	 * 3、字体层（这一层拿去指向字母或者正确答案）
	 */
	/*
	 * 0代表 空格 1代表 填词 2代表 焦点 3代表 答案 4代表 5代表 6代表
	 * 100代表A 101代表B 。。。 。。。 125代表Z
	 */
	//i是列   j是行   [列][行]   [x][y]
	/*1、背景层不在这里，在主的renderer*/

	/*2、效果层*/
	//效果按钮
	private Button bntmean[] = new Button[3];
	//运行的时候的grid的意思  可以展示的效果
	public  int runMap[][] = new int[10][10];
	//填写的答案
	public  int myAnswerMap[][] = new int[10][10];
	//真正的答案
	public  int answerMap[][] = new int[10][10];

	/*3、字体层*/
	//存储26个字母的字体
	private Font fontALP[] = new Font[26];
	//存储答案字体
	private Font fontStrAnswer[][] = new Font[10][10];
	//draw的时候画的Font
	private Font fontAnswer[][] = new Font[10][10];
	//标记是否需要画出来
	private boolean isDranFont[][] = new boolean[10][10];



	//按钮
	private boolean isDrawALP[] = new boolean[26];
	private Button bntALP[] = new Button[26];
	private Rect rectALP[] = new Rect[26];

	FileAccess file;
	public Game(Button[] bnt, Button[] bntalp, OpenGlRenderer r) {
		this.bntmean = bnt;
		this.renderer = r;
		this.bntALP = bntalp;

		file = new FileAccess(r.context);

		String string0 = file.readFileSdcard("/mnt/sdcard/a.txt");
		if(string0.equals("")){
			InputStream inputStream = r.context.getResources().openRawResource(R.raw.a);
			String string1 = TxtReader.getString(inputStream);
			file.writeFileSdcard("/mnt/sdcard/a.txt", string1);
		}
		String string = file.readFileSdcard("/mnt/sdcard/a.txt");

		int Line=0;
		int strStart=0;
		int strEnd=string.indexOf('\n');
		String str="";
		for(;Line<10;Line++)
		{
			str=string.substring(strStart, strEnd);
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);
			for(int j=0;j<10;j++)
			{
				char charTemp=str.charAt(j);
				if(charTemp=='。')continue;
				else if(Line<10)
					fontStrAnswer[j][Line]=new Font(renderer.mainGl, ""+charTemp ,
							36 + 0 * 72, 1004 - 0 * 72, 0.09f, 72.0f);
			}
		}
		++Line;
		str=string.substring(strStart, strEnd);
		strStart=strEnd+1;
		strEnd=string.indexOf('\n',strEnd+1);
		for(;Line<21;Line++)
		{
			str=string.substring(strStart, strEnd);
			System.out.println(str);
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);

			for(int j=0;j<10;j++)
			{
				char charTemp=str.charAt(j);
				if(charTemp=='。')continue;
				else if(Line-11<10){
					runMap[j][Line-11] = 1;
					answerMap[j][Line-11] = charTemp-'a'+100;
				}
			}
		}
		++Line;
		str=string.substring(strStart, strEnd);
		strStart=strEnd+1;
		strEnd=string.indexOf('\n',strEnd+1);
		str=string.substring(strStart, strEnd);
		strStart=strEnd+1;
		strEnd=string.indexOf('\n',strEnd+1);
		while(!str.equals((String)"####")){
			int index=str.charAt(0)-'0';
			fontQuestionHorizontal[index]=new Font(renderer.mainGl, "横"+index+str.substring(2),
					0, 1040, 0.09f, 72.0f);
			canBeDrawQH[index]=true;
			str=string.substring(strStart, strEnd);
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);
		}
		++Line;
		str=string.substring(strStart, strEnd);
		strStart=strEnd+1;
		strEnd=string.indexOf('\n',strEnd+1);
		while(!str.equals((String)"####")){
			int index=str.charAt(0)-'0';
			fontQuestionVertival[index]=new Font(renderer.mainGl, "竖"+index+str.substring(2),
					0, 1040, 0.09f, 72.0f);
			canBeDrawQV[index]=true;
			str=string.substring(strStart, strEnd);
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);
		}

		for (int i = 0; i < 10; i++) {
			rectALP[i] = new Rect((36 + i * 72) * 1.0f, 1280 - 1014 * 1.0f,
					72 * 1.0f, 108 * 1.0f);
		}
		for (int i = 10; i < 20; i++) {
			rectALP[i] = new Rect((36 + (i - 10) * 72) * 1.0f,
					1280 - 108 - 1014 * 1.0f, 72 * 1.0f, 108 * 1.0f);
		}
		for (int i = 20; i < 26; i++) {
			rectALP[i] = new Rect((144 + 36 + (i - 20) * 72) * 1.0f, 1280 - 108
					- 108 - 1014 * 1.0f, 72 * 1.0f, 108 * 1.0f);
		}
		for (int i = 0; i < 26; i++) {
			fontALP[i] = new Font(renderer.mainGl, "" + (char) ('A' + i),
					36 + 0 * 72, 1004 - 0 * 72, 0.09f, 72.0f);
		}
	}

	public void draw() {
		//画grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int temp = runMap[i][j];
				if (temp < 3 && temp > 0) {
					bntmean[temp].x = 36 + i * 72;
					bntmean[temp].y = 1004 - j * 72;
					bntmean[temp].draw();
				}
			}
		}

		//画按钮
		for (int i = 0; i < 26; i++) {
			if (isDrawALP[i])
				bntALP[i].draw();
		}

		//画字体层
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (isDranFont[i][j]&&fontAnswer[i][j]!=null) {
					fontAnswer[i][j].x = i * 72;
					fontAnswer[i][j].y = 968 - j * 72;
					fontAnswer[i][j].draw();
				}
			}
		}


		//画字幕
		for(int i=1;i<10;i++)
		{
			if(isDrawQH[i]&&fontQuestionHorizontal[i]!=null)
			{
				fontQuestionHorizontal[i].draw();
			}
			if(isDrawQV[i]&&fontQuestionVertival[i]!=null)
			{

				fontQuestionVertival[i].draw();
			}
		}

	}

	public int lastI = -1, lastJ = -1;
	private int lastALP = -1;

	public void touchDown(float x, float y) {

		//转化为行列
		int i, j;
		if (x >= 0 && x <= 720.0f && y >= 320.f && y <= 1040.0f) {
			i = (int) (x / 72);
			j = 9 - (int) ((y - 320) / 72);
		} else {
			i = j = -1;
		}

		// System.out.println("x:" + x + ";;;y:" + y + ";;;   " + "i:" + i
		// + ";;;j:" + j + ";;;   ");

		//改变焦点     如果ij在地图里面  且可以触碰
		if (i >= 0 && i <= 9 && j >= 0 && j <= 9 && runMap[i][j] != 0) {
			if (lastI != -1 && lastJ != -1 && !(i == lastI && j == lastJ)) {
				if(answerMap[lastI][lastJ]>=100)
					runMap[lastI][lastJ] = 1;
				else runMap[lastI][lastJ] = answerMap[lastI][lastJ];
				isDrawQH[lastJ]=false;
				isDrawQV[lastI]=false;
			}

			if(canBeDrawQH[j])
				isDrawQH[j]=true;
			if(canBeDrawQV[i]){
				isDrawQV[i]=true;
				if(canBeDrawQH[j])
				{
					fontQuestionVertival[i].y=1040+72;
				}
				else fontQuestionVertival[i].y=1040;
			}

			runMap[i][j] = 2;
			lastI = i;
			lastJ = j;
		}


		//检查按钮
		for (int tempi = 0; tempi < 26; tempi++) {
			if (rectALP[tempi].isTouch(x, y)) {
				bntALP[tempi].setw(90);
				bntALP[tempi].seth(120);
				isDrawALP[tempi] = true;
				lastALP = tempi;
				if (lastI != -1 && lastJ != -1) {
					fontAnswer[lastI][lastJ] = fontALP[tempi];
					isDranFont[lastI][lastJ] = true;
					myAnswerMap[lastI][lastJ] = tempi+100;
					if(canBeDrawQV[lastI]&&isVerticalRight(lastI))
					{
						for(int tj=0;tj<10;tj++)
						{
							if(answerMap[lastI][tj]>=100)
							{
								fontAnswer[lastI][tj]=fontStrAnswer[lastI][tj];
							}
						}
					}
					if(canBeDrawQH[lastJ]&&isHorizontalRight(lastJ)){
						for(int ti=0;ti<10;ti++)
						{
							if(answerMap[ti][lastJ]>=100)
							{
								fontAnswer[ti][lastJ]=fontStrAnswer[ti][lastJ];
							}
						}
					}
					for (int tempt = 0; tempt < 4; tempt++) {
						if (runMap[lastI + dir[tempt][0]][lastJ + dir[tempt][1]] == 1) {
							runMap[lastI + dir[tempt][0]][lastJ + dir[tempt][1]] = 2;
							if(answerMap[lastI][lastJ]>=100)
								runMap[lastI][lastJ] = 1;
							else runMap[lastI][lastJ] = answerMap[lastI][lastJ];
							lastI = lastI + dir[tempt][0];
							lastJ = lastJ + dir[tempt][1];
							break;
						}
					}
				}
			}
		}
	}

	private boolean isVerticalRight(int lastI2) {
		// TODO Auto-generated method stub
		for(int j=0;j<10;j++){
			if(myAnswerMap[lastI2][j]!=answerMap[lastI2][j])return false;
		}
		return true;
	}
	
	private boolean isHorizontalRight(int lastJ2) {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			if(myAnswerMap[i][lastJ2]!=answerMap[i][lastJ2])return false;
		}
		return true;
	}

	public void touchUp(MotionEvent event) {
		if (lastALP != -1)
			isDrawALP[lastALP] = false;
		float x = event.getX();
		float y = renderer.screenH - event.getY();
		float gameX = ((x / renderer.screenW) * renderer.gameScreenW);
		float gameY = ((y / renderer.screenH) * renderer.gameScreenH);
	}

	public int dir[][] = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
}
