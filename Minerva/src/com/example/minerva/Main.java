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
		// TabHost ��ü ������
		TabHost tabHost = getTabHost();
		/*
		 * �ѹ��� ���� ����� ���. ������ 4~5���� �ʿ��ϴ�. setIndicator�Ŀ� ��ȯ���� tabSpec�̹Ƿ� �� �׻��¿���
		 * .�� �Դ´�.
		 */
		// ù��° �� ����
		tabHost.addTab(tabHost.newTabSpec("tag1").setIndicator("ī�޶�",getResources().getDrawable(R.drawable.camera1))
				.setContent(new Intent(this, Camera.class)));
		// �ι�° �� ����
		tabHost.addTab(tabHost.newTabSpec("tag2").setIndicator("������",getResources().getDrawable(R.drawable.box))
				.setContent(new Intent(this, InventoryManagement.class)));

	}
}
