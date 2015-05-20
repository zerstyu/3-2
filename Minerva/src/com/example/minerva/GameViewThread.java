package com.example.minerva;

/*
 * �׸��� �����ϴ� Ŭ������.
*/

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread {
		// ������ ���� ��� ����
	private SurfaceHolder m_surfaceHolder;
	private GameView m_gameview;
		// ������ ���� ����
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
			// ĵ���� ��ü�� ���� -> ĵ���� ��� -> �׸��� �׸� -> ������� -> ĵ������ ȭ�鿡 �Ѹ�.
			// ���ν����忡�� ĵ������ �����Ϸ��� SurfaceHolder�� ���ؾ� ��. 
			// �׸����߱� �Ҷ��� Invalidate�� �ȵǼ� postInvalidate �����ó��.. ����
			// �����忡�� ���� ���ϴµ�.
		Canvas _canvas;
		while (m_run) {
			_canvas = null;
			try {
				m_gameview.Update();
				_canvas = m_surfaceHolder.lockCanvas(null);
					// SurfaceHolder�� ���� Surface�� �����ؼ� ������.
				synchronized (m_surfaceHolder) {
					m_gameview.onDraw(_canvas);	// �׸��� �׸�
				}
			} finally {
				if (_canvas != null)
					m_surfaceHolder.unlockCanvasAndPost(_canvas);
						// Surface�� ȭ�鿡 ǥ����
			}
		}
	}

}
