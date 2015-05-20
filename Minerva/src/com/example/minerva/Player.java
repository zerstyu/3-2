package com.example.minerva;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Player extends SpriteAnimation {
	Rect m_BoundBox = new Rect();

	public Player(Bitmap bitmap) {
		super(bitmap);
		this.InitSpriteData(62, 116, 6, 6);
		this.SetPosition(210, 680);
	}

	@Override
	public void Update(long GameTime) {
		super.Update(GameTime);
		m_BoundBox.set(m_x+10, m_y, m_x+52, m_y+76);
	}
}
