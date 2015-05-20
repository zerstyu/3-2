package com.example.minerva;

/*
 * 그림을 관리하는 클래스임.
*/

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread {
		// 접근을 위한 멤버 변수
	private SurfaceHolder m_surfaceHolder;
	private GameView m_gameview;
		// 스레드 실행 상태
	private boolean m_run = false;
		
	public GameViewThread(SurfaceHolder surfaceHolder, GameView gameview) {
		m_surfaceHolder = surfaceHolder;
		m_gameview = gameview;
	}

	public void setRunning(boolean run) {
		m_run = run;
	}

	@Override
	public void run() {
			// 캔버스 객체에 접근 -> 캔버스 잠금 -> 그림을 그림 -> 잠금해제 -> 캔버스를 화면에 뿌림.
			// 내부스레드에서 캔버스에 접근하려면 SurfaceHolder를 통해야 함. 
			// 그림맞추기 할때도 Invalidate로 안되서 postInvalidate 썼던것처럼.. 내부
			// 스레드에선 접근 못하는덧.
		Canvas _canvas;
		while (m_run) {
			_canvas = null;
			try {
				m_gameview.Update();
				_canvas = m_surfaceHolder.lockCanvas(null);
					// SurfaceHolder를 통해 Surface에 접근해서 가져옴.
				synchronized (m_surfaceHolder) {
					m_gameview.onDraw(_canvas);	// 그림을 그림
				}
			} finally {
				if (_canvas != null)
					m_surfaceHolder.unlockCanvasAndPost(_canvas);
						// Surface를 화면에 표시함
			}
		}
	}

}
