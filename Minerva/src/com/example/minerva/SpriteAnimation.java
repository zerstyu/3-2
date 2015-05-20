package com.example.minerva;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteAnimation extends GraphicObject {
	// 기본적으로 GraphicObject의 Bitmap과 m_x, m_y는 포함됨염.
	private Rect m_Rect;	// 그려줄 사각 영역
	private int m_fps;		// frame per second
	private int m_iFrames;	// 프레임 개수
	private int m_CurrentFrame;	// 현재 프레임
	private int m_SpriteWidth;	// 개별 프레임의 너비
	private int m_SpriteHeight;	// 개별 프레임의 높이
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
			// 안드로이드는 밀리초 단위로 프레임을 수행함(걍 그리는 함수에 단위가 밀리초겄지 뭐)
			// 30fps로 애니메이션을 그리려면 실제로는 1000 / 30 = 33.3ms에 한 번씩 스프라이트 
			// 이미지를 그려줘야 하기 땜시 일케 함.
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
