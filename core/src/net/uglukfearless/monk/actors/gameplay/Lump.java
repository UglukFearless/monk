package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.LumpUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

import java.util.Random;

/**
 * Created by Ugluk on 02.06.2016.
 */
public class Lump extends GameActor implements Pool.Poolable, Movable {

    private float runTime;
    private static Random rand = new Random();
    private TextureRegion mRegion;

    private float mWind;
    private static final float mWindBasic = -0.5f;

    public Lump(Body body) {
        super(body);

        mWind = -0.5f;
    }

    public Lump(World world) {
        super(WorldUtils.createLupm(world));

        mWind = -0.5f;
    }

    @Override
    public void act(float delta) {

        super.act(delta);

        runTime += delta;

        if (runTime>0.5f&&body.getLinearVelocity().x>-10) {
                body.setLinearVelocity(body.getLinearVelocity().add(mWind, 0));
        }


        if (body.getPosition().y<-5f||body.getPosition().x<-5f) {
            this.remove();
            PoolsHandler.sLumpsPool.free(this);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (inFrame()) {
            super.draw(batch, parentAlpha);

            LumpUserData data = getUserData();

            batch.draw(mRegion,
                    body.getPosition().x - data.getWidth()*1.2f / 2,
                    body.getPosition().y - data.getHeight()*1.2f / 2,
                    data.getWidth() * 0.6f, data.getHeight() * 0.6f,
                    data.getWidth() * 1.2f, data.getHeight() * 1.2f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        }
    }

    @Override
    public LumpUserData getUserData() {
        return (LumpUserData)body.getUserData();
    }


    @Override
    public void reset() {
        body.setActive(false);
        body.setTransform(-10, -10, 0);
    }

    public void init(Stage stage,Body parent, TextureRegion textureRegion) {

        mRegion = textureRegion;
        body.setTransform(parent.getPosition(), 0);
        body.setAngularVelocity(0);
        body.setLinearVelocity(new Vector2(rand.nextFloat() * 20 - 10, rand.nextFloat() * 18 + 2));
        body.applyAngularImpulse(rand.nextFloat() * 0.5f, true);
        body.setActive(true);
//        stage.addActor(this);
        mWind = mWindBasic*(((GameStage)stage).getCurrentVelocity().x/Constants.WORLD_STATIC_VELOCITY_INIT.x);
        ((GameStage)stage).addMovable(this);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        mWind = mWindBasic*(speedScale/Constants.WORLD_STATIC_VELOCITY_INIT.x);
    }
}
