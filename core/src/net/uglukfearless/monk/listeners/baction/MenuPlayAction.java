package net.uglukfearless.monk.listeners.baction;

import net.uglukfearless.monk.screens.MainMenu;

/**
 * Created by Ugluk on 05.08.2016.
 */
public class MenuPlayAction implements ButtonAction {

    MainMenu mScreen;

    public MenuPlayAction(MainMenu screen) {
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
