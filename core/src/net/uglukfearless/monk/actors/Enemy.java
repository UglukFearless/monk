package net.uglukfearless.monk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.ObstaclesMap;

/**
 * Created by Ugluk on 20.05.2016.
 */
public class Enemy extends GameActor {


    private Animation animation;
    private float stateTime;
    private float deadTime;

    private float jumpTime;

    private boolean starting;


    public Enemy(Body body) {
        super(body);

        TextureRegion [] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        for (int i=0;i<getUserData().getTextureRegions().length;i++) {
            runningFrames[i] = AssetLoader.charactersAtlas
                    .findRegion(getUserData().getTextureRegions()[i]);
        }
        animation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
        deadTime = 0f;
        jumpTime = 0f;

        body.setLinearVelocity(Constants.WORLD_STATIC_VELOCITY);

        starting = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(stateTime, true),
                body.getPosition().x - (userData.getWidth()*0.1f) - userData.getWidth()/2,
                body.getPosition().y - userData.getHeight()/2,getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                userData.getWidth()*1.2f, userData.getHeight()*1.1f, 1f, 1f, (float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public EnemyUserData getUserData() {
        return (EnemyUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getPosition().x < Constants.GROUND_WIDTH/2&&!starting) {
            body.setLinearVelocity(getUserData().getLinearVelocity());
            starting = true;
        }

        if (getUserData().isDead()) {
            dead(delta);
        }

    }

    public void dead(float delta) {
        deadTime +=delta;
        body.applyForceToCenter(500,50,false);
        if (deadTime>0.2f&&getStage()!=null) {
            GameStage stage = (GameStage) getStage();
            stage.createLump(body);
            stage.createLump(body);
            stage.createLump(body);
            stage.createLump(body);
            ((UserData)body.getUserData()).setDestroy(true);
            this.remove();
        }
    }

}
