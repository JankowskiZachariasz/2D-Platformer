package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import org.VehicleWar.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration w = new AndroidApplicationConfiguration();
		w.hideStatusBar=true;
		w.useImmersiveMode=true;
		w.useWakelock=true;
		AndroidApplicationConfiguration config = w;
		initialize(new Main(), config);
	}
}
