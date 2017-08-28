package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.constants.Constants;
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
    private boolean mNear;

    public GameDecoration(GameStage stage, Texture region, float width, float height, boolean near) {

        mStage = stage;

        setSize(width, height);

        mSprite = new Sprite(region);
        mSprite.setSize(getWidth(), getHeight());

        mNear = near;

    }

    //дефолтный конструктор для пула
    public GameDecoration() {}

    public GameDecoration(GameStage stage, TextureRegion region, float width, float height, boolean near) {

        mStage = stage;

        setSize(width, height);

        mSprite = new Sprite(region);
        mSprite.setSize(getWidth(), getHeight());

        mNear = near;

    }

    public void init(float moveSpeed, float x, float y) {
        mMoveSpeed = moveSpeed*mSpeedCof;
        setPosition(x,y);
        mStage.addMovable(this);
    }

    //расширенный инит для объектоа полученных из пула
    public void init(GameStage stage, TextureRegion region, float width, float height, boolean near, float moveSpeed, float x, float y) {
        mStage = stage;

        setSize(width, height);

        mSprite = new Sprite(region);
        mSprite.setSize(getWidth(), getHeight());

        mNear = near;

        mMoveSpeed = moveSpeed*mSpeedCof;
        setPosition(x,y);
        mStage.addMovable(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (inBounds()) {
            setX(getX() + mMoveSpeed*delta);
        } else {
            ((GameStage)getStage()).removeDecoration(this, mNear);
            this.remove();
        }
    }

    private boolean inBounds() {
        return getX()+getWidth()>0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (inFrame()) {
            mSprite.setPosition(getX(), getY());
            mSprite.draw(batch);
        }
    }

    private boolean inFrame() {
        return (getX()+getWidth()>0)&&(getX()< Constants.GAME_WIDTH);
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

    public boolean isNear() {
        return mNear;
    }

    public void setSpeedCof(float speedCof) {
        mSpeedCof = speedCof;
    }
}
