package net.uglukfearless.monk.listeners.baction;

import net.uglukfearless.monk.screens.MainMenuScreen;

/**
 * Created by Ugluk on 05.08.2016.
 */
public class MenuPlayAction implements ButtonAction {

    MainMenuScreen mScreen;

    public MenuPlayAction(MainMenuScreen screen) {
        mScreen = screen;
    }

    @Override
    public void isTouchDown() {

    }

    @Override
    public void isTouchUp() {
        mScreen.newGame();
    }

    @Override
    public void action() {
        mScreen.newGame();
    }
}
