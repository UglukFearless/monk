package net.uglukfearless.monk;


import com.badlogic.gdx.Game;

import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.screens.MainMenu;
import net.uglukfearless.monk.screens.SplashScreen;

public class RunningMonk extends Game {
	
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}
}
