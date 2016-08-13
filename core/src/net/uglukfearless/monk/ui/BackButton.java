package net.uglukfearless.monk.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.uglukfearless.monk.screens.MainMenu;

/**
 * Created by Ugluk on 08.08.2016.
 */


public class BackButton extends Button {

   MainMenu mScreen;

    public BackButton(Skin skin, MainMenu screen) {
        super(skin, "back");
        this.mScreen = screen;

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mScreen.mainMenu();
                super.clicked(event, x, y);
            }
        });
    }
}
