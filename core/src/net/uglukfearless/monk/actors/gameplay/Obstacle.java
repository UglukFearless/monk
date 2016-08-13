package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.AssetLoader;

/**
 * Created by Ugluk on 07.06.2016.
 */
public class Obstacle extends net.uglukfearless.monk.actors.gameplay.GameActor {

    private float deadTime;
    private float stateTime;

    public Obstacle(Body body) {
        super(body);

        stateTime = 0f;

        deadTime = 0f;
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //ВНИМАНИЕ! ЭТО ПЕРЕПИСАТЬ! ЛАЖА ПОЛНАЯ
        if (!getUserData().isSphere()&&getUserData().isArmour()) {
            batch.draw(AssetLoader.stone,
                    body.getPosition().x - userData.getWidth()/2,
                    body.getPosition().y - userData.getHeight()/2,getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                    userData.getWidth(), userData.getHeight(), 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        } else if (!getUserData().isSphere()&&!getUserData().isArmour()) {
            batch.draw(AssetLoader.box,
                    body.getPosition().x - userData.getWidth()/2,
                    body.getPosition().y - userData.getHeight()/2,getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                    userData.getWidth(), userData.getHeight(), 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        } else {
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(AssetLoader.blades.getKeyFrame(stateTime,true),
                    body.getPosition().x - (userData.getWidth()*0.1f) - userData.getWidth()/2,
                    body.getPosition().y - userData.getHeight()/2,getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                    userData.getWidth()*1.2f, userData.getHeight()*1.1f, 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        }
    }

    @Override
    public ObstacleUserData getUserData() {
        return (ObstacleUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getUserData().isArmour()&&!getUserData().isDead()&&body.getLinearVelocity().y>0) {
            body.setLinearVelocity(getUserData().getLinearVelocity());
        }

        if (getUserData().isDead()) {
            dead(delta);
        }
    }

    private void dead(float delta) {
        deadTime +=delta;
        body.applyForceToCenter(500, 50, false);
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
