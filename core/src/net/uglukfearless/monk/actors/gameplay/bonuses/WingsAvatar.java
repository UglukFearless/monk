package net.uglukfearless.monk.actors.gameplay.bonuses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 25.11.2016.
 */
public class WingsAvatar extends Actor {

    TextureRegion mRegion;
    GameStage mStage;

    public WingsAvatar(GameStage gameStage, float gameWidth, float gameHeight) {

        mRegion = AssetLoader.bonusesAtlas.findRegion(Constants.BONUS_WINGS_REGION);
        mStage = gameStage;
        setVisible(false);
        setSize(2f,2f);
        setPosition(gameWidth - 6f, gameHeight - getWidth());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (mStage.getWingsRevival()>0) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mRegion, getX(), getY(), getWidth(), getHeight());
    }


}
