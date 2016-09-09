package net.uglukfearless.monk.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 30.06.2016.
 */
public class MenuBackground extends Actor {
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(AssetLoader.menuBackgroundTexture, 0, 0,
                Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }
}
