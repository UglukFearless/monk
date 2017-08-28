package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.Retributable;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

/**
 * Created by Ugluk on 07.06.2016.
 */
public class Obstacle extends GameActor implements Pool.Poolable, Movable, Retributable {

    private float deadTime;
    private float stateTime;

    private Animation mStayAnimation;
    private Animation mDieAnimation;
    private Animation mAnimation;

    public Obstacle(Body body) {
        super(body);

        stateTime = 0f;

        deadTime = 0f;

        mAnimation = getUserData().getStayAnimation();
        mStayAnimation = getUserData().getStayAnimation();
        mDieAnimation = getUserData().getDieAnimation();

        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

    public Obstacle(World world, ObstacleType obstacleType) {

        super(WorldUtils.createObstacle(world, obstacleType));

        stateTime = 0f;

        deadTime = 0f;

        mAnimation = getUserData().getStayAnimation();
        mStayAnimation = getUserData().getStayAnimation();
        mDieAnimation = getUserData().getDieAnimation();

        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

    public void init(Stage stage, float x, float y) {
        body.setTransform(x, getUserData().getObstacleType().getY() +
                y + getUserData().getObstacleType().getHeight() / 2f, 0);
        body.setLinearVelocity(getUserData().getLinearVelocity());
        getUserData().setLinearVelocity(((GameStage)stage).getCurrentVelocity());
        body.setLinearVelocity(getUserData().getLinearVelocity());
        body.setActive(true);
        stage.addActor(this);
        ((GameStage)stage).addMovable(this);
        ((GameStage)stage).addRetributable(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (inFrame()) {
            super.draw(batch, parentAlpha);

            ObstacleUserData data = getUserData();

            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw((TextureRegion) mAnimation.getKeyFrame(stateTime,true),
                    body.getPosition().x - (data.getWidth()*data.getScaleX()/2) + data.getOffsetX() ,
                    body.getPosition().y - (data.getHeight()/2) + data.getOffsetY(),
                    getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                    data.getWidth()*data.getScaleX(), data.getHeight()*data.getScaleY()
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
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
        } else if (!BodyUtils.bodyInBounds(body)) {
            this.remove();
            PoolsHandler.sObstaclePools.get(getUserData().getObstacleType().name()).free(this);
        } else {
            if (body.getPosition().x < Constants.GAME_WIDTH) {

                SpaceTable.setCell(body.getPosition().x, body.getPosition().y - getUserData().getObstacleType().getY() - getUserData().getHeight()/2f
                        , getUserData().getWidth(),getUserData().getObstacleType().getCategoryBit());
            }
        }
    }

    private void dead(float delta) {
        deadTime +=delta;
        body.applyForceToCenter(500, 50, false);
        if (mDieAnimation!=null) {
            mAnimation = mDieAnimation;
        }

        if (body.getPosition().x < Constants.GAME_WIDTH) {

            SpaceTable.setCell(body.getPosition().x, body.getPosition().y - getUserData().getObstacleType().getY() - getUserData().getHeight()/2f
                    , getUserData().getWidth(),getUserData().getObstacleType().getCategoryBit());
        }

        if (((deadTime>0.2f||getUserData().isTrap())||getUserData().isTerribleDeath()) && getStage() != null) {

            //exp
            if (AssetLoader.sFreeParticleDust.size>0) {
                ParticleEffect effect = AssetLoader.sFreeParticleDust.get(AssetLoader.sFreeParticleDust.size -1);
                AssetLoader.sFreeParticleDust.removeIndex(AssetLoader.sFreeParticleDust.size -1);
                effect.getEmitters().first().setPosition(body.getPosition().x, body.getPosition().y);
                effect.start();
                AssetLoader.sWorkParticleDust.add(effect);
            }

            GameStage stage = (GameStage) getStage();

            if (getUserData().isConteiner()) {
                stage.initItem(body.getPosition().x, body.getPosition().y);
            }

            stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump2"));
            stage.removeMovable(this);
            stage.removeRetributable(this);
            this.remove();
            PoolsHandler.sObstaclePools.get(getUserData().getObstacleType().name()).free(this);

            ScoreCounter.increaseScore(1);
            ScoreCounter.increaseDestroyed(getUserData().getKEY());
        }
    }

    @Override
    public void reset() {
        if (getStage()!=null) {
            ((GameStage)getStage()).removeMovable(this);
            ((GameStage)getStage()).removeRetributable(this);
        }
        getUserData().setDead(false);
        getUserData().setTerribleDeath(false);
        stateTime = 0f;
        deadTime = 0f;
        body.setLinearVelocity(getUserData().getLinearVelocity());
        body.setAngularVelocity(0f);
        body.setActive(false);
        if (!getUserData().isTrap()&&!getUserData().isArmour()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_SIMPLE);
        } else if (!getUserData().isTrap()&&getUserData().isArmour()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_ARMOUR);
        } else if (getUserData().isTrap()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_TRAP);
        }
        body.setTransform(-10, -10, 0);
        ((UserData)body.getUserData()).setLaunched(false);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {
        getUserData().setLinearVelocity(speedScale, 0);
        body.setLinearVelocity(getUserData().getLinearVelocity().x, body.getLinearVelocity().y);
    }

    @Override
    public void punish(int retributionLevel) {
        switch (retributionLevel) {
            case 0:
                if (getBody().getPosition().x<Constants.GAME_WIDTH
                            &&!getUserData().isTrap()
                            &&!getUserData().isArmour()
                            &&!getUserData().isDead()) {

                        getUserData().setDead(true);
//                        ScoreCounter.increaseScore(1);
//                        ScoreCounter.increaseDestroyed();
                    }
                break;
            case 1:
                if (getBody().getPosition().x<Constants.GAME_WIDTH
                            &&!getUserData().isTrap()
                            &&!getUserData().isDead()) {

                        getUserData().setDead(true);
//                        ScoreCounter.increaseScore(1);
//                        ScoreCounter.increaseDestroyed();
                    }
                break;
            case 2:
                if (getBody().getPosition().x<Constants.GAME_WIDTH
                            &&!getUserData().isDead()) {

                        getUserData().setDead(true);
//                        ScoreCounter.increaseScore(1);
//                        ScoreCounter.increaseDestroyed();
                    }
                break;
            case 3:
                getUserData().setDead(true);
                break;
        }
    }

}
