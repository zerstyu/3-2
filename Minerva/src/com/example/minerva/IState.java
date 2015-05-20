package com.example.minerva;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

// ���ӻ��¿� ���� �κ�.. ������Ʈ ���� ���

public interface IState {
	public void Init();
		// �� ���·� �ٲ���� �� ������ �͵�
	public void Destroy();
		// �ٸ� ���·� �ٲ� �� ������ �͵�
	public void Update();
		// ���������� ������ �͵�
	public void Render(Canvas canvas);
		// �׷��� �� �͵�
	public boolean onKeyDown(int keyCode, KeyEvent event);
		// �ȵ���̵� Ű �Է� ó��
	public boolean onTouchEvent(MotionEvent event);
		// �ȵ���̵� ��ġ �Է� ó��
}
