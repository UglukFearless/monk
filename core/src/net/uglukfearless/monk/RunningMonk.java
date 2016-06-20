package net.uglukfearless.monk;


import com.badlogic.gdx.Game;

import net.uglukfearless.monk.screens.GameScreen;

public class RunningMonk extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}
