package net.uglukfearless.monk.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import net.uglukfearless.monk.listeners.baction.ButtonAction;
import net.uglukfearless.monk.utils.AssetLoader;

/**
 * Created by Ugluk on 05.08.2016.
 */
public class MenuButton {

    private float x, y, width, height;

    private TextureRegion buttonUp, buttonDown;

    private Rectangle bounds;
    private Vector2 mTextPosition;

    private boolean isPressed = false;

    protected ButtonAction mButtonAction;

    private String mTitle;

    public MenuButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(buttonDown, x, y, width, height);
            if (mTitle!=null&&mTitle!="") {
                AssetLoader.font.draw(batch, mTitle, bounds.x + width*0.05f
                        , bounds.y + bounds.height - height*0.3f, (int) width*0.9f, Align.center, true);
            }
        } else {
            batch.draw(buttonUp, x, y, width, height);
            if (mTitle!=null&&mTitle!="") {
                AssetLoader.font.draw(batch, mTitle, bounds.x + width*0.05f + 2
                        , bounds.y + bounds.height - height*0.3f - 2, (int) width*0.9f, Align.center, true);
                AssetLoader.font.getXHeight();
            }
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)&&isPressed) {
            isPressed = false;
            if (mButtonAction!=null) {
                mButtonAction.isTouchUp();
            }
            return true;
        }
        isPressed = false;
        return false;
    }

    public void setAction(ButtonAction buttonAction) {
        mButtonAction = buttonAction;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
