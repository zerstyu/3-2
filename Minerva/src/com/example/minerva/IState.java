package com.example.minerva;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

// 게임상태에 대한 부분.. 스테이트 패턴 사용

public interface IState {
	public void Init();
		// 이 상태로 바뀌었을 때 실행할 것들
	public void Destroy();
		// 다른 상태로 바뀔 때 실행할 것들
	public void Update();
		// 지속적으로 수행할 것들
	public void Render(Canvas canvas);
		// 그려야 할 것들
	public boolean onKeyDown(int keyCode, KeyEvent event);
		// 안드로이드 키 입력 처리
	public boolean onTouchEvent(MotionEvent event);
		// 안드로이드 터치 입력 처리
}
