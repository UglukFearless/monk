package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.bodies.DragonBody;

import static sun.management.snmp.util.JvmContextFactory.getUserData;

/**
 * Created by Ugluk on 04.12.2016.
 */

public class Dragon extends Actor {

    private float stateTime;
    private Color mColor;
    private float mAlpha;

    private enum DragonState {
        TREND,
        AVATAR
    }

    private DragonBody mDragonBody;
    private GameStage mStage;
    private DragonState mState;

    private TextureRegion mSegmentRegion;
    private TextureRegion mFirstRegion;
    private Animation mHeadAnimation;


    public Dragon(DragonBody dragonBody, GameStage gameStage) {
        mDragonBody = dragonBody;
        mStage = gameStage;
        mState = DragonState.TREND;

        mSegmentRegion = AssetLoader.dragonAtlas.findRegion("pieceofdragon1");
        mFirstRegion = AssetLoader.dragonAtlas.findRegion("pieceofdragon1");

        TextureRegion [] headRegions = new TextureRegion[2];
        for (int i=1;i<3;i++) {
            headRegions[i-1] = AssetLoader.dragonAtlas.findRegion("dragonhead" + i);
        }

        mHeadAnimation = new Animation(0.2f, headRegions);
        mHeadAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        switch (mState) {
            case TREND:
                if (mStage.getRunner()!=null&&mStage.getRunner().body!=null) {
                    mDragonBody.follow(mStage.getRunner().getBody());
                }
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        mColor = batch.getColor();
        batch.setColor(mColor.r,mColor.g,mColor.b, mAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        float x;
        float y;
        UserData data;

        //сегменты по порядку
        for (Body body : mDragonBody.getSpine()) {
            data = (UserData) body.getUserData();

            x = body.getPosition().x - data.getWidth()*1.5f/2f;
            y = body.getPosition().y - data.getHeight()* 1.2f/2f;

            batch.draw(mSegmentRegion, x, y,
                    data.getWidth()  * 0.5f, data.getHeight()  * 0.5f,
                    data.getWidth() *1.5f, data.getHeight() * 1.4f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));

        }

        //первый сегмент
        data = (UserData) mDragonBody.getFirstSegment().getUserData();
        x = mDragonBody.getFirstSegment().getPosition().x - data.getWidth()*1.6f/2f;
        y = mDragonBody.getFirstSegment().getPosition().y - data.getHeight()* 1.2f/2f;
        batch.draw(mFirstRegion, x, y,
                data.getWidth()  * 0.5f, data.getHeight()  * 0.5f,
                data.getWidth() *1.6f, data.getHeight() * 1.4f
                , 1f, 1f, (float) Math.toDegrees(mDragonBody.getFirstSegment().getAngle()));

        //голова
        data = (UserData) mDragonBody.getHead().getUserData();
        x = mDragonBody.getHead().getPosition().x - data.getWidth()*1.7f/2f;
        y = mDragonBody.getHead().getPosition().y - data.getHeight()*1.5f/2f;

        batch.draw(mHeadAnimation.getKeyFrame(stateTime), x, y,
                data.getWidth()  * 0.5f, data.getHeight()  * 0.5f,
                data.getWidth() *1.6f, data.getHeight() * 1.5f
                , 1f, 1f, (float) Math.toDegrees(mDragonBody.getHead().getAngle()));


        batch.setColor(mColor);
    }

    public void takeShape() {
        mDragonBody.setFilter(FilterConstants.FILTER_DRAGON);
        mState = DragonState.AVATAR;
        setAlpha(1f);
    }

    public void trend() {
        mDragonBody.setFilter(FilterConstants.FILTER_GHOST);
        mState = DragonState.TREND;
        setAlpha(0f);
    }

    public float getDragonY() {
        return mDragonBody.getHeadAnchor().getPosition().y;
    }

    public void headUp() {

        switch (mState) {
            case TREND:
                break;
            case AVATAR:
                mDragonBody.headUp();
                mStage.screenShake(0.1f, 0.15f);
                break;
        }
    }

    public void headDown() {

        switch (mState) {
            case TREND:
                break;
            case AVATAR:
                mDragonBody.headDown();
                mStage.screenShake(0.1f, 0.15f);
                break;
        }
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }
}
