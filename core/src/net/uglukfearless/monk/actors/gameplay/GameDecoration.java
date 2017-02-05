package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.Movable;

/**
 * Created by Ugluk on 05.08.2016.
 */
public class GameDecoration extends Actor implements Movable, Pool.Poolable {

    private float mMoveSpeed;
    private float mSpeedCof = 1f;

    private Sprite mSprite;

    private GameStage mStage;

    public GameDecoration(GameStage stage, Texture region, float width, float height) {

        mStage = stage;

        setSize(width, height);

        mSprite = new Sprite(region);
        mSprite.setSize(getWidth(), getHeight());

    }

    public void init(float moveSpeed, float x, float y) {
        mMoveSpeed = moveSpeed*mSpeedCof;
        setPosition(x,y);
        mStage.addMovable(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (inBounds()) {
            setX(getX() + mMoveSpeed*delta);
        }
    }

    private boolean inBounds() {
        return getX()+getWidth()>0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        mSprite.setPosition(getX(), getY());
        mSprite.draw(batch);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {
        mMoveSpeed = speedScale*mSpeedCof;
    }

    @Override
    public void reset() {
        if (mStage!=null) {
            mStage.removeMovable(this);
        }
        this.remove();
    }
}
