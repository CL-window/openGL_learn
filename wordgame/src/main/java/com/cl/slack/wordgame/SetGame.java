package com.cl.slack.wordgame;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import smallgameengine.help.FileAccess;
import smallgameengine.help.TxtReader;

public class SetGame extends Activity {
	public int now=0;
	public int spinerNow=0;
	private String strQV[]=new String[10];
	private String strQH[]=new String[10];
	private char charALP[][]=new char[10][10];
	private char charAns[][]=new char[10][10];
	
	private ArrayAdapter<String> adapter;    
	public EditText text[][]=new EditText[10][10];
	private List<String> list = new ArrayList<String>();     
    private EditText myEdView;     
    private Spinner mySpinner; 
    
    FileAccess file;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int windowsHeight = metric.heightPixels;
		int windowsWight = metric.widthPixels;
		
		//动态改变布局
		LinearLayout production_factory = (LinearLayout)findViewById(R.id.linearlayout1);
		LayoutParams params = production_factory.getLayoutParams();
		params.height = windowsWight ;
		production_factory.setLayoutParams(params);

		text[0][0]=(EditText)findViewById(R.id.editText1);
		text[0][1]=(EditText)findViewById(R.id.editText2);
		text[0][2]=(EditText)findViewById(R.id.editText3);
		text[0][3]=(EditText)findViewById(R.id.editText4);
		text[0][4]=(EditText)findViewById(R.id.editText5);
		text[0][5]=(EditText)findViewById(R.id.editText6);
		text[0][6]=(EditText)findViewById(R.id.editText7);
		text[0][7]=(EditText)findViewById(R.id.editText8);
		text[0][8]=(EditText)findViewById(R.id.editText9);
		text[0][9]=(EditText)findViewById(R.id.editText10);
		text[1][0]=(EditText)findViewById(R.id.editText11);
		text[1][1]=(EditText)findViewById(R.id.editText12);
		text[1][2]=(EditText)findViewById(R.id.editText13);
		text[1][3]=(EditText)findViewById(R.id.editText14);
		text[1][4]=(EditText)findViewById(R.id.editText15);
		text[1][5]=(EditText)findViewById(R.id.editText16);
		text[1][6]=(EditText)findViewById(R.id.editText17);
		text[1][7]=(EditText)findViewById(R.id.editText18);
		text[1][8]=(EditText)findViewById(R.id.editText19);
		text[1][9]=(EditText)findViewById(R.id.editText20);
		text[2][0]=(EditText)findViewById(R.id.editText21);
		text[2][1]=(EditText)findViewById(R.id.editText22);
		text[2][2]=(EditText)findViewById(R.id.editText23);
		text[2][3]=(EditText)findViewById(R.id.editText24);
		text[2][4]=(EditText)findViewById(R.id.editText25);
		text[2][5]=(EditText)findViewById(R.id.editText26);
		text[2][6]=(EditText)findViewById(R.id.editText27);
		text[2][7]=(EditText)findViewById(R.id.editText28);
		text[2][8]=(EditText)findViewById(R.id.editText29);
		text[2][9]=(EditText)findViewById(R.id.editText30);
		text[3][0]=(EditText)findViewById(R.id.editText31);
		text[3][1]=(EditText)findViewById(R.id.editText32);
		text[3][2]=(EditText)findViewById(R.id.editText33);
		text[3][3]=(EditText)findViewById(R.id.editText34);
		text[3][4]=(EditText)findViewById(R.id.editText35);
		text[3][5]=(EditText)findViewById(R.id.editText36);
		text[3][6]=(EditText)findViewById(R.id.editText37);
		text[3][7]=(EditText)findViewById(R.id.editText38);
		text[3][8]=(EditText)findViewById(R.id.editText39);
		text[3][9]=(EditText)findViewById(R.id.editText40);
		text[4][0]=(EditText)findViewById(R.id.editText41);
		text[4][1]=(EditText)findViewById(R.id.editText42);
		text[4][2]=(EditText)findViewById(R.id.editText43);
		text[4][3]=(EditText)findViewById(R.id.editText44);
		text[4][4]=(EditText)findViewById(R.id.editText45);
		text[4][5]=(EditText)findViewById(R.id.editText46);
		text[4][6]=(EditText)findViewById(R.id.editText47);
		text[4][7]=(EditText)findViewById(R.id.editText48);
		text[4][8]=(EditText)findViewById(R.id.editText49);
		text[4][9]=(EditText)findViewById(R.id.editText50);
		text[5][0]=(EditText)findViewById(R.id.editText51);
		text[5][1]=(EditText)findViewById(R.id.editText52);
		text[5][2]=(EditText)findViewById(R.id.editText53);
		text[5][3]=(EditText)findViewById(R.id.editText54);
		text[5][4]=(EditText)findViewById(R.id.editText55);
		text[5][5]=(EditText)findViewById(R.id.editText56);
		text[5][6]=(EditText)findViewById(R.id.editText57);
		text[5][7]=(EditText)findViewById(R.id.editText58);
		text[5][8]=(EditText)findViewById(R.id.editText59);
		text[5][9]=(EditText)findViewById(R.id.editText60);
		text[6][0]=(EditText)findViewById(R.id.editText61);
		text[6][1]=(EditText)findViewById(R.id.editText62);
		text[6][2]=(EditText)findViewById(R.id.editText63);
		text[6][3]=(EditText)findViewById(R.id.editText64);
		text[6][4]=(EditText)findViewById(R.id.editText65);
		text[6][5]=(EditText)findViewById(R.id.editText66);
		text[6][6]=(EditText)findViewById(R.id.editText67);
		text[6][7]=(EditText)findViewById(R.id.editText68);
		text[6][8]=(EditText)findViewById(R.id.editText69);
		text[6][9]=(EditText)findViewById(R.id.editText70);
		text[7][0]=(EditText)findViewById(R.id.editText71);
		text[7][1]=(EditText)findViewById(R.id.editText72);
		text[7][2]=(EditText)findViewById(R.id.editText73);
		text[7][3]=(EditText)findViewById(R.id.editText74);
		text[7][4]=(EditText)findViewById(R.id.editText75);
		text[7][5]=(EditText)findViewById(R.id.editText76);
		text[7][6]=(EditText)findViewById(R.id.editText77);
		text[7][7]=(EditText)findViewById(R.id.editText78);
		text[7][8]=(EditText)findViewById(R.id.editText79);
		text[7][9]=(EditText)findViewById(R.id.editText80);
		text[8][0]=(EditText)findViewById(R.id.editText81);
		text[8][1]=(EditText)findViewById(R.id.editText82);
		text[8][2]=(EditText)findViewById(R.id.editText83);
		text[8][3]=(EditText)findViewById(R.id.editText84);
		text[8][4]=(EditText)findViewById(R.id.editText85);
		text[8][5]=(EditText)findViewById(R.id.editText86);
		text[8][6]=(EditText)findViewById(R.id.editText87);
		text[8][7]=(EditText)findViewById(R.id.editText88);
		text[8][8]=(EditText)findViewById(R.id.editText89);
		text[8][9]=(EditText)findViewById(R.id.editText90);
		text[9][0]=(EditText)findViewById(R.id.editText91);
		text[9][1]=(EditText)findViewById(R.id.editText92);
		text[9][2]=(EditText)findViewById(R.id.editText93);
		text[9][3]=(EditText)findViewById(R.id.editText94);
		text[9][4]=(EditText)findViewById(R.id.editText95);
		text[9][5]=(EditText)findViewById(R.id.editText96);
		text[9][6]=(EditText)findViewById(R.id.editText97);
		text[9][7]=(EditText)findViewById(R.id.editText98);
		text[9][8]=(EditText)findViewById(R.id.editText99);
		text[9][9]=(EditText)findViewById(R.id.editText100);

		myEdView=(EditText)findViewById(R.id.editText0);
		myEdView.setText(strQH[0]);
		myEdView.setOnFocusChangeListener(new OnFocusChangeListener() {          

	        public void onFocusChange(View v, boolean hasFocus) {
	            if(!hasFocus)
	            {
	            	
	            	if(spinerNow<10){
	            		strQH[spinerNow]=myEdView.getText().toString();
	            	}
	            	else {
	            		strQV[spinerNow-10]=myEdView.getText().toString();
	            	}
	            }
	               //do job here owhen Edittext lose focus 
	        }
	    });


		list.add("横1");
		list.add("横2");
		list.add("横3");
		list.add("横4");
		list.add("横5");
		list.add("横6");
		list.add("横7");
		list.add("横8");
		list.add("横9");
		list.add("横10");

		list.add("竖1");
		list.add("竖2");
		list.add("竖3");
		list.add("竖4");
		list.add("竖5");
		list.add("竖6");
		list.add("竖7");
		list.add("竖8");
		list.add("竖9");
		list.add("竖10");
        
        mySpinner = (Spinner)findViewById(R.id.spinner_num);

		//第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		//第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//第四步：将适配器添加到下拉列表上
		mySpinner.setAdapter(adapter);
		//第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
		mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView arg0, View arg1, int arg2, long id) {
				// TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/

				if(spinerNow<10){
					strQH[spinerNow]=myEdView.getText().toString();
				}
				else {
					strQV[spinerNow-10]=myEdView.getText().toString();
				}


				if(id<10){
					myEdView.setText(strQH[(int) id]);
					spinerNow=(int)id;
				}
				else {
					myEdView.setText(strQV[(int) id-10]);
					spinerNow=(int)id;
				}
				arg0.setVisibility(View.VISIBLE);
			}
			@SuppressWarnings("unchecked")
			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub
				arg0.setVisibility(View.VISIBLE);
			}
		});
        /*下拉菜单弹出的内容选项触屏事件处理*/
		mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				// TODO Auto-generated method stub

				return false;
			}
		});
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
		mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				v.setVisibility(View.VISIBLE);
			}
		});
		file = new FileAccess(this);

        String string0 = file.readFileSdcard("/mnt/sdcard/a.txt");
        if(string0.equals("")){
        	InputStream inputStream = getResources().openRawResource(R.raw.a);
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
				charAns[j][Line]=charTemp;
				text[Line][j].setText(""+charTemp);
			}
		}
		++Line;
		str=string.substring(strStart, strEnd);
		strStart=strEnd+1;
		strEnd=string.indexOf('\n',strEnd+1);
		for(;Line<21;Line++)
		{
			str=string.substring(strStart, strEnd);
			
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);
			
			for(int j=0;j<10;j++)
			{
				char charTemp=str.charAt(j);
				charALP[j][Line-11]=charTemp;
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

			strQH[index]=str.substring(2);
			
			
			
			
			
			
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


			
			strQV[index]=str.substring(2);
			
			
			str=string.substring(strStart, strEnd);
			strStart=strEnd+1;
			strEnd=string.indexOf('\n',strEnd+1);
		}
		
		myEdView.setText(strQH[0]);
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO �Զ����ɷ������
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(spinerNow<10){
        		
        		strQH[spinerNow]=myEdView.getText().toString();
        	}
        	else {
        		strQV[spinerNow-10]=myEdView.getText().toString();
        	}
			
			String str="";
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++)
				{
					str+=charAns[j][i];
				}
				str+="\n";
			}
			str+="####\n";
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++)
				{
					str+=charALP[j][i];
				}
				str+="\n";
			}
			str+="####\n";
			for(int i=0;i<10;i++)
			{
				if(strQH[i]!=null&&!strQH[i].equals("")){
					str+=""+i+"��"+strQH[i]+"\n";
				}
			}
			str+="####\n";
			for(int i=0;i<10;i++)
			{
				if(strQV[i]!=null&&!strQV[i].equals("")){
					str+=""+i+"��"+strQV[i]+"\n";
				}
			}
			str+="####\n";
			
			System.out.println(str);
			file.writeFileSdcard("/mnt/sdcard/a.txt", str);
			
			finish();
		}
		return true;
	}
	public void onClicklast(View view){
		if(now==1)
		{
			now=0;
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++){
					charALP[j][i] = text[i][j].getText().charAt(0);
					text[i][j].setText(""+charAns[j][i]);
				}
			}
		}
	}
	
	public void onClicknext(View view){
		if(now==0)
		{
			now=1;
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++){
					charAns[j][i] = text[i][j].getText().charAt(0);
					text[i][j].setText(""+charALP[j][i]);
				}
			}
			
		}
	}
}
