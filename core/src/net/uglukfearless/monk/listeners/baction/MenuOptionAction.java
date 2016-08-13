package net.uglukfearless.monk.listeners.baction;

import net.uglukfearless.monk.screens.MainMenu;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class MenuOptionAction implements ButtonAction {

    MainMenu mScreen;

    public MenuOptionAction(MainMenu screen) {
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
