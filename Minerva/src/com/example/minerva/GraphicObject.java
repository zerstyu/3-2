package com.example.minerva;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/*
 * 그림을 나타내는 클래스
 * [ 사용법 ]
 * GraphicObject obj = new GraphicObject(비트맵데이터);
 * obj.draw(canvas);
 */
public class GraphicObject {
	protected Bitmap m_bitmap;
	protected int m_x;
	protected int m_y;
	
	public GraphicObject(Bitmap bitmap){
		m_bitmap = bitmap;
		m_x = 0;
		m_y = 0;
	}
	
	public GraphicObject(Bitmap bitmap, int m_x, int m_y){
		m_bitmap = bitmap;
		this.m_x = m_x;
		this.m_y = m_y;
	}
	
	public void Draw(Canvas canvas){
		canvas.drawBitmap(m_bitmap, m_x, m_y, null);
	}
	
	public void SetPosition(int x, int y) {
		m_x = x;
		m_y = y;
	}
	
	public int GetX(){
		return m_x;
	}
	
	public int GetY(){
		return m_y;
	}
}
