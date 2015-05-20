package com.example.minerva;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteAnimation extends GraphicObject {
	// �⺻������ GraphicObject�� Bitmap�� m_x, m_y�� ���Եʿ�.
	private Rect m_Rect;	// �׷��� �簢 ����
	private int m_fps;		// frame per second
	private int m_iFrames;	// ������ ����
	private int m_CurrentFrame;	// ���� ������
	private int m_SpriteWidth;	// ���� �������� �ʺ�
	private int m_SpriteHeight;	// ���� �������� ����
	private long m_FrameTimer;
	
	protected boolean m_Reply = true;
	protected boolean m_End = false;
	
	
	public SpriteAnimation(Bitmap bitmap) {
		super(bitmap);
		m_Rect = new Rect(0,0,0,0);
		m_CurrentFrame = 0;
		m_FrameTimer = 0;
	}
	
	public void InitSpriteData(int _width, int _height, int _fps, int iFrame) {
		m_SpriteWidth = _width;
		m_SpriteHeight = _height;
		m_Rect.top = 0;
		m_Rect.left = 0;
		m_Rect.bottom = m_SpriteHeight;
		m_Rect.right = m_SpriteWidth;
		m_fps = 1000 / _fps;
			// �ȵ���̵�� �и��� ������ �������� ������(�� �׸��� �Լ��� ������ �и��ʰ��� ��)
			// 30fps�� �ִϸ��̼��� �׸����� �����δ� 1000 / 30 = 33.3ms�� �� ���� ��������Ʈ 
			// �̹����� �׷���� �ϱ� ���� ���� ��.
		m_iFrames = iFrame;
	}

	@Override
	public void Draw(Canvas canvas) {

		Rect dest = new Rect(m_x, m_y, m_x + m_SpriteWidth, m_y + m_SpriteHeight);
		canvas.drawBitmap(m_bitmap, m_Rect, dest, null);
	}
	
	public void Update(long GameTime) {
		if (!m_End) {

			if (GameTime > m_FrameTimer + m_fps) {
				m_FrameTimer = GameTime;
				m_CurrentFrame += 1;

				if (m_CurrentFrame >= m_iFrames) {
					if (m_Reply)
						m_CurrentFrame = 0;
					else
						m_End = true;
				}

			}
			m_Rect.left = m_CurrentFrame * m_SpriteWidth;
			m_Rect.right = m_Rect.left + m_SpriteWidth;
		}
	}
	
	public boolean getAnimationEnd() {
		return m_End;
	}
}
