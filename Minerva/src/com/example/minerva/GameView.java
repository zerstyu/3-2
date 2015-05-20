package com.example.minerva;

/*
 * 다른 View는 화면 업데이트를 UI 스레드에서 처리하지만, SurfaceView는 백그라운드에서 처리하므로 빠름. 
 * OpenGL을 통한 가속도 지원함(3D가능).
 * 구조는 Surface와 SurfaceHolder로 되어있음. 실제 화면 처리는 홀더에서 함.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private IState m_state;
	private GameViewThread m_thread;
	public int age = 0;
	public Context m_context;	// Vibrator 쓸라공~(GameState에서)
	GameState gs;

	public GameView(Context context) {
		super(context);
		m_context = context;
		setFocusable(true);	// 키 입력 처리를 받기 위해
		
		AppManager.getInstance().setGameView(this);
		AppManager.getInstance().setResources(getResources());				
		
		gs = new GameState();

		float screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		float screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;

		gs.setData((int)screenWidth, (int)screenHeight);
		ChangeGameState(gs);
		
		getHolder().addCallback(this);
		m_thread = new GameViewThread(getHolder(), this);		
	}
	
	public void ChangeGameState(IState _state) {
		if(m_state != null)
			m_state.Destroy();
		_state.Init();
		m_state = _state;
	}

	public void Update() {
		// 프레임워크에서 사용자의 입력이나 안드로이드 내외부의 신호를 받지 않더라도 데이터를
		// 자동갱신할 때 사용할 메서드임.. GameViewThread의 run에서 수행됨.
		m_state.Update();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		m_state.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		m_state.onTouchEvent(event);		
		return true;
	}  

	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas != null){
			canvas.drawColor(Color.WHITE);
			m_state.Render(canvas);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {	// Surface가 생성될 때 스레드를 실행함
		m_thread.setRunning(true); // 스레드를 실행상태로..
		m_thread.start(); // 스레드 시작
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	// Surface가 파괴될 떄 스레드를 종료함
		boolean retry = true;
		m_thread.setRunning(false);
		while (retry) {
			try {
				m_thread.join(); // 스레드를 중지함. 여기서 에러가나면 retry = false가 실행안되고
									// catch로 가겠지.
				retry = false;
			} catch (InterruptedException e) {
				// catch에서 하는건 암것도 없으니 바로 다시 while문으로 감. m_thread.join에서 에러나면
				// retry = false가 수행이 안되니깐
				// 다시 while문이 돌게되는거지~
			}

		}

	}

}
