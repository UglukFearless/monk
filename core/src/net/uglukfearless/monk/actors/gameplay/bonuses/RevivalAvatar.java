package net.uglukfearless.monk.actors.gameplay.bonuses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 13.09.2016.
 */
public class RevivalAvatar extends Actor {

    private TextureRegion mRegion;
    private GameStage mStage;
    private boolean mVisible;

    public RevivalAvatar(GameStage gameStage, float gameHeight) {

        mRegion = AssetLoader.bonusesAtlas.findRegion(Constants.BONUS_REVIVAL_REGION);
        mStage = gameStage;
        setVisible(false);
        setSize(2f,2f);
        setPosition(6f, gameHeight - getWidth());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (mStage.getRevival()>0) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mVisible) {
            batch.draw(mRegion, getX(), getY(), getWidth(), getHeight());
        }
    }
}
