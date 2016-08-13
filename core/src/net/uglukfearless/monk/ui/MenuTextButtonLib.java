package net.uglukfearless.monk.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.uglukfearless.monk.listeners.baction.ButtonAction;

/**
 * Created by Ugluk on 10.08.2016.
 */
public class MenuTextButtonLib extends TextButton {
    private boolean mOffsetOn = false;
    float mNewX = 0;
    float mNewY = 0;
    ButtonAction mAction;

    public MenuTextButtonLib(String text, Skin skin, ButtonAction action) {
        super(text, skin);

        mAction = action;

        setupListener(mAction);
    }

    public MenuTextButtonLib(String text, Skin skin, String styleName, ButtonAction action) {
        super(text, skin, styleName);

        mAction = action;

        setupListener(mAction);
    }

    private void setupListener(final ButtonAction action) {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mOffsetOn = false;
                action.action();
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (super.isPressed()&&super.getLabel()!=null) {
            if (!mOffsetOn) {
                mNewX = super.getLabel().getX() - 2;
                mNewY = super.getLabel().getY() + 2;
            }
            super.getLabel().setPosition(mNewX, mNewY);
            mOffsetOn = true;
        }
        super.draw(batch, parentAlpha);
    }

}
