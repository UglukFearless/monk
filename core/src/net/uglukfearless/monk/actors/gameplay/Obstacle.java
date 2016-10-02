package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

/**
 * Created by Ugluk on 07.06.2016.
 */
public class Obstacle extends GameActor implements Pool.Poolable, Movable{

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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        ObstacleUserData data = getUserData();


        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(mAnimation.getKeyFrame(stateTime,true),
                body.getPosition().x - (data.getWidth()*data.getScaleX()/2) + data.getOffsetX() ,
                body.getPosition().y - (data.getHeight()/2) + data.getOffsetY(),
                getUserData().getWidth()*0.5f,getUserData().getHeight()*0.5f,
                data.getWidth()*data.getScaleX(), data.getHeight()*data.getScaleY()
                , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
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
        if ((deadTime>0.2f||getUserData().isTrap()) && getStage() != null) {
            GameStage stage = (GameStage) getStage();
            stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump2"));
            stage.removeMovable(this);
            this.remove();
            PoolsHandler.sObstaclePools.get(getUserData().getObstacleType().name()).free(this);
        }
    }

    @Override
    public void reset() {
        if (getStage()!=null) {
            ((GameStage)getStage()).removeMovable(this);
        }
        getUserData().setDead(false);
        stateTime = 0f;
        deadTime = 0f;
        body.setLinearVelocity(getUserData().getLinearVelocity());
        body.setActive(false);
        if (!getUserData().isTrap()&&!getUserData().isArmour()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_SIMPLE);
        }
        body.setTransform(-10, -10, 0);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {
        getUserData().setLinearVelocity(speedScale, 0);
        body.setLinearVelocity(getUserData().getLinearVelocity().x, body.getLinearVelocity().y);
    }
}
