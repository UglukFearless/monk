package net.uglukfearless.monk.listeners.baction;

import net.uglukfearless.monk.screens.MainMenu;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class MenuStatisticsAction implements ButtonAction {

    MainMenu mScreen;

    public MenuStatisticsAction(MainMenu screen) {
        mScreen = screen;
    }

    @Override
    public void isTouchDown() {

    }

    @Override
    public void isTouchUp() {
        mScreen.statisticsMenu();
    }

    @Override
    public void action() {
        mScreen.statisticsMenu();
    }
}
