package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.box2d.BackgroundUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.WorldUtils;

/**
 * Created by Ugluk on 21.05.2016.
 */
public class Background extends Actor implements Movable {

    private float speed = 0;
    private Body mBody1, mBody2;

//    private Sprite mSprite1, mSprite2;

    public Background(World world, int viewport_width, float viewport_height) {

        mBody1 = WorldUtils.createBackground(world,0 - viewport_width*0.05f/2, viewport_width*1.05f, viewport_height);
        mBody2 = WorldUtils.createBackground(world,viewport_width + viewport_width*0.05f/2, viewport_width*1.05f, viewport_height);

//        mSprite1 = new Sprite(AssetLoader.background, 0,0,840, 480);
//        mSprite1.setPosition(mBody1.getPosition().x - ((UserData) mBody1.getUserData()).getWidth() / 2f,
//                mBody1.getPosition().y - ((UserData) mBody1.getUserData()).getHeight() / 2f);
//        mSprite1.setSize(((UserData) mBody1.getUserData()).getWidth()
//                ,((UserData) mBody1.getUserData()).getHeight());
//
//        mSprite2 = new Sprite(AssetLoader.background, 0,0,840, 480);
//        mSprite2.setPosition(mBody2.getPosition().x - ((UserData) mBody2.getUserData()).getWidth() / 2f,
//                mBody2.getPosition().y - ((UserData) mBody2.getUserData()).getHeight() / 2f);
//        mSprite2.setSize(((UserData) mBody2.getUserData()).getWidth()
//                ,((UserData) mBody2.getUserData()).getHeight());
    }

    @Override
    public void act(float delta) {

        if (leftBoundsReached(mBody1)) {
            repositionBodies(mBody1, mBody2);
        } else if (leftBoundsReached(mBody2)) {
            repositionBodies(mBody2, mBody1);
        }

//        mSprite1.setPosition(mBody1.getPosition().x - ((UserData) mBody1.getUserData()).getWidth() / 2f,
//                mBody1.getPosition().y - ((UserData) mBody1.getUserData()).getHeight() / 2f);
//        mSprite2.setPosition(mBody2.getPosition().x - ((UserData) mBody2.getUserData()).getWidth() / 2f,
//                mBody2.getPosition().y - ((UserData) mBody2.getUserData()).getHeight() / 2f);

    }

    private void repositionBodies(Body body1, Body body2) {
        body1.setTransform(body2.getPosition().x + ((UserData) body2.getUserData()).getWidth(),
                ((UserData) body2.getUserData()).getHeight() / 2, 0);
    }

    private boolean leftBoundsReached(Body body) {
        return body.getPosition().x + ((UserData)body.getUserData()).getWidth()/2<=0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.disableBlending();
        batch.draw(AssetLoader.background
                , mBody1.getPosition().x - ((UserData) mBody1.getUserData()).getWidth() / 2f
                , mBody1.getPosition().y - ((UserData) mBody1.getUserData()).getHeight() / 2f
                , ((UserData) mBody1.getUserData()).getWidth()
                , ((UserData) mBody1.getUserData()).getHeight());
        batch.draw(AssetLoader.background
                , mBody2.getPosition().x - ((UserData)mBody2.getUserData()).getWidth()/2f
                , mBody2.getPosition().y - ((UserData)mBody2.getUserData()).getHeight()/2f
                , ((UserData)mBody2.getUserData()).getWidth()
                , ((UserData)mBody2.getUserData()).getHeight());
//        mSprite1.draw(batch);
//        mSprite2.draw(batch);
        batch.enableBlending();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        mBody1.setLinearVelocity(new Vector2(speed, 0));
        mBody2.setLinearVelocity(new Vector2(speed, 0));
    }


    @Override
    public void changingStaticSpeed(float speedScale) {

        mBody1.setLinearVelocity(new Vector2(speedScale*Constants.BACKGROUND_VELOCITY_COFF, 0));
        mBody2.setLinearVelocity(new Vector2(speedScale*Constants.BACKGROUND_VELOCITY_COFF, 0));
    }
}
