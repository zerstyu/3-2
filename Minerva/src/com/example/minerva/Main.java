package com.example.minerva;

import android.app.Activity;
import android.os.Bundle;

import android.app.TabActivity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;

import android.widget.TabHost;

public class Main extends TabActivity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// TabHost 객체 얻어오기
		TabHost tabHost = getTabHost();
		/*
		 * 한번에 탭을 만드는 요령. 본래는 4~5줄이 필요하다. setIndicator후에 반환값이 tabSpec이므로 또 그상태에서
		 * .이 먹는다.
		 */
		// 첫번째 탭 구성
		tabHost.addTab(tabHost.newTabSpec("tag1").setIndicator("카메라",getResources().getDrawable(R.drawable.camera1))
				.setContent(new Intent(this, Camera.class)));
		// 두번째 탭 구성
		tabHost.addTab(tabHost.newTabSpec("tag2").setIndicator("재고관리",getResources().getDrawable(R.drawable.box))
				.setContent(new Intent(this, InventoryManagement.class)));

	}
}
