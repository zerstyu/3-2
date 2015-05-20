package com.example.minerva;

import com.example.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;


public class GameState implements IState {

	protected Player m_player;
	protected GraphicObject m_test;

	long LastRegenEnemy = System.currentTimeMillis();
	long LastRegenMissile_Player = System.currentTimeMillis();
	Vibrator m_vibrator;
	protected int m_score;

	int x = 0;
	int y = 0;

	// 옵티머스 LTE : 1280*720
	// 갤럭시s3 1280 x 720

	int test_x, test_y, mid_y;
	
	@Override
	public void Init() {
		m_player = new Player(AppManager.getInstance().getBitmap(
				R.drawable.player));
		m_test = new GraphicObject(AppManager.getInstance().getBitmap(
				R.drawable.uimms));
		AppManager.getInstance().m_gamestate = this;
		m_vibrator = (Vibrator) AppManager.getInstance().getGameView().m_context
				.getSystemService(Context.VIBRATOR_SERVICE);
		m_score = 0;

	}

	@Override
	public void Destroy() {

	}
	
	public void setData(int wid, int hei){
		test_x = 50;
		test_y = hei;
		
		mid_y = hei/2;
	}

	@Override
	public void Update() {

		long GameTime = System.currentTimeMillis();
		m_test.SetPosition(test_x, test_y);
		//m_player.Update(GameTime);

		if (test_y <= mid_y-200) { // 액정 화면의 450까지 이동시킨다
		} else {
			test_y -= 5;
		}
		// m_player.SetPosition(x++, y);  player 이동시키기

	}

	@Override
	public void Render(Canvas canvas) {
		//m_player.Draw(canvas);
		m_test.Draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		m_player.SetPosition((int) event.getX() - 40, (int) event.getY() - 50);

		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
