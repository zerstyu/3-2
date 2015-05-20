package com.example.minerva;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryManagement extends Activity implements OnItemClickListener {
	ListAdapter lst;
	ListView lv;
	Context con;
	DataTask dt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Log.e("#Act2#", "onCreate()");

		con = this;
		// 화면 구성하기

		setContentView(R.layout.inventorymanagement);
		
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		
		dt = new DataTask();
		dt.execute(1);
		
	}
	
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("Minerva 종료하시겠습니까?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setNegativeButton("No", null).show();
			return true;
		}
		return super.onKeyDown(KeyCode, event);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		LayoutInflater lv = getLayoutInflater();
		View v2 = lv.inflate(R.layout.information, null);
		
		InventoryIfo ifo = lst.invifo.get(arg2);
		TextView v = (TextView)v2.findViewById(R.id.textView2);
		// v.setText(ifo.img + " " + ifo.in + " " + ifo.out + " " + ifo.num);
		v.setText("안녕하세요\n Minerva입니다");
		AlertDialog alert = new AlertDialog.Builder( this ) 		
		.setTitle( "물품 정보" )
		.setPositiveButton( "OK", new DialogInterface.OnClickListener() 
		{						
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		})
		.setView( v2 )
		.show();		
	}
	
	public static String getString(String urls){
    	String res = "";
    	try
    	{
    	        HttpClient client = new DefaultHttpClient();  
    	        HttpGet get = new HttpGet(urls);
    	        HttpResponse responseGet = client.execute(get);  
    	        HttpEntity resEntityGet = responseGet.getEntity();
    	        
    	        if (resEntityGet != null)
    	        {  
    	        	res = EntityUtils.toString(resEntityGet);
    	        }
    	        
    	}
    	catch (Exception e)
    	{
    	        e.printStackTrace();
    	}
    	return res;
    }
	
	public class DataTask extends AsyncTask<Integer, Integer, Integer> {
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			lst = new ListAdapter(con);
			lv.setAdapter(lst);
			lst.notifyDataSetChanged();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			while (true) {
				try{
					publishProgress(1);
					Thread.sleep(50);
				}
				catch(Exception e){
					
				}
			}
		}

	}

}
