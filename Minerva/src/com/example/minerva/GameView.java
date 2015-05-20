package com.example.minerva;

/*
 * �ٸ� View�� ȭ�� ������Ʈ�� UI �����忡�� ó��������, SurfaceView�� ��׶��忡�� ó���ϹǷ� ����. 
 * OpenGL�� ���� ���ӵ� ������(3D����).
 * ������ Surface�� SurfaceHolder�� �Ǿ�����. ���� ȭ�� ó���� Ȧ������ ��.
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
	public Context m_context;	// Vibrator �����~(GameState����)
	GameState gs;

	public GameView(Context context) {
		super(context);
		m_context = context;
		setFocusable(true);	// Ű �Է� ó���� �ޱ� ����
		
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
		// �����ӿ�ũ���� ������� �Է��̳� �ȵ���̵� ���ܺ��� ��ȣ�� ���� �ʴ��� �����͸�
		// �ڵ������� �� ����� �޼�����.. GameViewThread�� run���� �����.
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
	public void surfaceCreated(SurfaceHolder holder) {	// Surface�� ������ �� �����带 ������
		m_thread.setRunning(true); // �����带 ������·�..
		m_thread.start(); // ������ ����
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	// Surface�� �ı��� �� �����带 ������
		boolean retry = true;
		m_thread.setRunning(false);
		while (retry) {
			try {
				m_thread.join(); // �����带 ������. ���⼭ ���������� retry = false�� ����ȵǰ�
									// catch�� ������.
				retry = false;
			} catch (InterruptedException e) {
				// catch���� �ϴ°� �ϰ͵� ������ �ٷ� �ٽ� while������ ��. m_thread.join���� ��������
				// retry = false�� ������ �ȵǴϱ�
				// �ٽ� while���� ���ԵǴ°���~
			}

		}

	}

}
