package net.uglukfearless.monk.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 21.05.2016.
 */
public class Background extends Actor {


    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 10;

    public Background() {

        textureRegionBounds1 = new Rectangle(0 - Constants.GAME_WIDTH/2,
                Constants.BACKGROUND_OFFSET_Y, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.GAME_WIDTH/2,
                Constants.BACKGROUND_OFFSET_Y, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    }

    @Override
    public void act(float delta) {

        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(AssetLoader.background, textureRegionBounds1.x, textureRegionBounds1.y,
                Constants.GAME_WIDTH*1.01f, Constants.GAME_HEIGHT);
        batch.draw(AssetLoader.background, textureRegionBounds2.x, textureRegionBounds2.y,
                Constants.GAME_WIDTH*1.01f, Constants.GAME_HEIGHT);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x ) <= 0; // - (delta*speed)
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x +=-1f*delta*speed;
        textureRegionBounds2.x +=-1f*delta*speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Constants.GAME_WIDTH,
                Constants.BACKGROUND_OFFSET_Y, Constants.GAME_WIDTH*1.01f, Constants.GAME_HEIGHT);
    }
}
