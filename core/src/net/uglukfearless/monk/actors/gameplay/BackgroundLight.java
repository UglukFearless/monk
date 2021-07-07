package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.stages.GameStage;

/**
 * Created by Ugluk on 01.10.2017.
 */

public class BackgroundLight extends Actor {
    GameStage mStage;
    private Sprite mSprite;
    private boolean mPulse;
    private float mStateTime;
    private float mAlpha;
    private float mAlphaCoff;

    public BackgroundLight(GameStage stage, TextureRegion region, float viewport_height, boolean pulse) {

        mStage = stage;
        mPulse = pulse;

        setSize(25f, 11.75f);
        mSprite = new Sprite(region);
        mSprite.setSize(25f, 11.75f);

        mSprite.setPosition(0, viewport_height - 11.25f);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (mPulse) {
            mSprite.setAlpha(mAlpha);
            mSprite.draw(batch);
        } else {
            mSprite.draw(batch);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (mPulse) {

            mAlphaCoff = ((GameStage) getStage()).getCurrentVelocity().x/15;
            mAlphaCoff = (float) Math.pow(mAlphaCoff, 2f);
            mStateTime += delta;
            mAlpha = Math.abs((float) Math.sin(0.2*mStateTime*mAlphaCoff ));
        }

    }
}
