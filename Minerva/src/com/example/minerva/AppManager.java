package com.example.minerva;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * 요 프레임워크를 사용하는 어플을 관리하기 위한 클래스임.
 * 여기선 GameView와 GameView의 리소스 접근을 위해서만 사용..
 * 나중에 상용 수준의 게임을 만들면 어플 실행정보나 여러 가지 정보를 관리하는 기능을 추가할 수 있음.
*/
public class AppManager {
	private static AppManager s_instance;
	private GameView m_gameview;	// Main GameView
	private Resources m_resources;	// Main GameView의 리소스
	public GameState m_gamestate;	// Enemy에서 미사일을 생성할 때 GameState의 적미사일리스트에 넣어야하는데 접근할 방법이 없으니.
	
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
		// 비트맵 가져오는 함수를 쉽게하기위해서~
		// 규모가 더 큰 프로젝트를 만들때는 리소스 매니저를 따로 만드는게 좋음.
		return BitmapFactory.decodeResource(m_resources, image);
	}
}