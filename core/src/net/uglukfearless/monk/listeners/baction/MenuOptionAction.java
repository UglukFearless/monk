package net.uglukfearless.monk.listeners.baction;

import net.uglukfearless.monk.screens.MainMenuScreen;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class MenuOptionAction implements ButtonAction {

    MainMenuScreen mScreen;

    public MenuOptionAction(MainMenuScreen screen) {
        mScreen = screen;
    }
    @Override
    public void isTouchDown() {

    }

    @Override
    public void isTouchUp() {
        mScreen.optionMenu();
    }

    @Override
    public void action() {
        mScreen.optionMenu();
    }
}
