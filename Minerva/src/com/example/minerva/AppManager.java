package com.example.minerva;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * �� �����ӿ�ũ�� ����ϴ� ������ �����ϱ� ���� Ŭ������.
 * ���⼱ GameView�� GameView�� ���ҽ� ������ ���ؼ��� ���..
 * ���߿� ��� ������ ������ ����� ���� ���������� ���� ���� ������ �����ϴ� ����� �߰��� �� ����.
*/
public class AppManager {
	private static AppManager s_instance;
	private GameView m_gameview;	// Main GameView
	private Resources m_resources;	// Main GameView�� ���ҽ�
	public GameState m_gamestate;	// Enemy���� �̻����� ������ �� GameState�� ���̻��ϸ���Ʈ�� �־���ϴµ� ������ ����� ������.
	
	public static AppManager getInstance() {
		if(s_instance == null) {
			s_instance = new AppManager();
		}
		return s_instance;
	}
	
	void setGameView(GameView _gameview) {
		m_gameview = _gameview;
		
	}
	
	void setResources(Resources _resources) {	
		m_resources = _resources;
	}
	
	public GameView getGameView() {
		return m_gameview;
	}
	
	public Resources getResources() {
		return m_resources;
	}
	
	public Bitmap getBitmap(int image) {
		// ��Ʈ�� �������� �Լ��� �����ϱ����ؼ�~
		// �Ը� �� ū ������Ʈ�� ���鶧�� ���ҽ� �Ŵ����� ���� ����°� ����.
		return BitmapFactory.decodeResource(m_resources, image);
	}
}