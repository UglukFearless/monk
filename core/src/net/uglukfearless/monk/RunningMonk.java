package net.uglukfearless.monk;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.uglukfearless.monk.screens.SplashScreen;
import net.uglukfearless.monk.utils.file.SoundSystem;

public class RunningMonk extends Game {
	
	@Override
	public void create () {
		Gdx.graphics.setVSync(true);
		SoundSystem.init();
		setScreen(new SplashScreen(this));
	}
}
