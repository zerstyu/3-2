package com.example.minerva;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	public int m_state = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
        	// °ÔÀÓ ºä¸¦ È­¸é¿¡ ¶ç¿ò.
    }    
}