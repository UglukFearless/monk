package net.uglukfearless.monk.actors.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 30.06.2016.
 */
public class MenuBackground extends Actor {

    private float VIEWPORT_WIDTH;
    private float VIEWPORT_HEIGHT;

    private Texture mTexture;

    public MenuBackground(Texture texture, int VIEWPORT_WIDTH, int VIEWPORT_HEIGHT, float yViewportHeight , boolean first) {
        this.VIEWPORT_WIDTH = VIEWPORT_WIDTH;
        if (first) {
            this.VIEWPORT_HEIGHT = yViewportHeight;
        } else {
            this.VIEWPORT_HEIGHT = VIEWPORT_HEIGHT;
        }


        mTexture = texture;
    }

    public MenuBackground(Texture texture) {
        VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

        mTexture = texture;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {

        //страшный костыль, боюсь, что так делать низя нихья. Это связано с использованием ScrollPane... хз как она меняет цвет
        batch.setColor(1f, 1f, 1f, 1f);

        batch.draw(mTexture, 0, 0,
                VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        super.draw(batch, parentAlpha);
    }
}
