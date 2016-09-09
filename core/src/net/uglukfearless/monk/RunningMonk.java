package net.uglukfearless.monk;


import com.badlogic.gdx.Game;

import net.uglukfearless.monk.screens.SplashScreen;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.SoundSystem;

public class RunningMonk extends Game {
	
	@Override
	public void create () {
		SoundSystem.init();
		setScreen(new SplashScreen(this));
	}
}
