package net.uglukfearless.monk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.utils.ScoreCounter;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Runner extends GameActor {


    private RunnerUserData data;

    private float stateTime;
    private float strikeTime;
    private float deadTime;

    public Runner(Body body) {
        super(body);
        stateTime = 0f;
        data = (RunnerUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = body.getPosition().x - (data.getWidth()*0.1f) - data.getWidth()/2;
        float y = body.getPosition().y - data.getHeight()/2;
        float width = data.getWidth()*1.2f;

        if (data.isDead()) {
            batch.draw(AssetLoader.playerHit,x, y, width*0.5f, data.getHeight()*0.5f, width,
                    data.getHeight(), 1f, 1f,(float) Math.toDegrees(body.getAngle()));
        } else if (data.isJumping1()) {
            batch.draw(AssetLoader.playerJump, x, y, width, data.getHeight());
        } else {
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(AssetLoader.playerRun.getKeyFrame(stateTime, true),x, y,
                    width, data.getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (data.isStriking()==true) {
            strikeTime +=delta;
            if (strikeTime > 0.2f) {
                data.setStriking(false);
            }
        }

        if (data.isDead()) {
            dead(delta);
        } else {
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

        if (!body.getWorld().isLocked()) {
            if (body.getUserData()!=null&&!getUserData().isDead()&&
                    body.getPosition().y - Constants.RUNNER_HEIGHT/2>=
                            Constants.GROUND_Y + Constants.GROUND_HEIGHT/2 + 2) {
                body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
            }
        }
    }

    private void dead(float delta) {
        deadTime +=delta;
        if (deadTime>0.3f&&getStage()!=null) {
            GameStage stage = (GameStage) getStage();
            stage.createLump(body);
            stage.createLump(body);
            stage.createLump(body);
            stage.createLump(body);
            ((UserData)body.getUserData()).setDestroy(true);
            this.remove();
            ScoreCounter.checkScore();
        }
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void jump() {

        if (!((data.isJumping1() && data.isJumping2())||data.isDead())) {
            body.applyLinearImpulse(data.getJumpingLinearImpulse()
                    , body.getWorldCenter(), true);
            if (!data.isJumping1()) {
                data.setJumping1(true);
            } else {
                data.setJumping2(true);
            }
        }
    }

    public void landed() {
        data.setJumping1(false);
        data.setJumping2(false);
    }


    public void strike() {
        if (!data.isStriking()&&!data.isDead()) {
            data.setStriking(true);
            strikeTime = 0f;
        }
    }

    public void hit() {
        body.applyAngularImpulse(data.getHitAngularImpulse(), true);
        body.setFixedRotation(false);
        data.setDead(true);
    }

}
