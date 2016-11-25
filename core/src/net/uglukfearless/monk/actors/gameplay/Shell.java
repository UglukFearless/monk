package net.uglukfearless.monk.actors.gameplay;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.ShellUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

/**
 * Created by Ugluk on 05.09.2016.
 */
public class Shell extends GameActor implements Pool.Poolable, Movable {

    private TextureRegion mRegion;
    private float mPreviousVelocity;

    public Shell(World world) {
        super(WorldUtils.createEnemyShell(world));

        mRegion = AssetLoader.enemiesAtlas.findRegion("enemy2_shell1");

    }

    @Override
    public ShellUserData getUserData() {
        return (ShellUserData) userData;
    }

    @Override
    public void reset() {
        ((GameStage)getStage()).removeMovable(this);
        body.setActive(false);
        this.remove();
        getUserData().setDead(false);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE);
        body.setGravityScale(0);
        body.setTransform(-10, -10, 0);
    }

    public void init(Stage stage, Vector2 position, float speed, String key) {
        getUserData().setKEY(key);
        body.setTransform(position.x, position.y + 0.5f, 0);
        body.setAngularVelocity(15);
        body.setActive(true);
        stage.addActor(this);
        if (speed-10f>((GameStage)stage).getCurrentVelocity().x-14f) {
            body.setLinearVelocity(((GameStage)stage).getCurrentVelocity().x - 14f, 0);
        } else {
            body.setLinearVelocity(speed - 10f, 0);
        }
        ((GameStage)stage).addMovable(this);
        mPreviousVelocity = ((GameStage)stage).getCurrentVelocity().x;
    }

    public void init(Stage stage, Vector2 position, float speed, TextureRegion region, String key) {
        mRegion = region;
        getUserData().setKEY(key);
        body.setTransform(position.x, position.y + 0.5f, 0);
        body.setAngularVelocity(15);
        body.setActive(true);
        stage.addActor(this);
        if (speed-10f>((GameStage)stage).getCurrentVelocity().x-14f) {
            body.setLinearVelocity(((GameStage)stage).getCurrentVelocity().x - 14f, 0);
        } else {
            body.setLinearVelocity(speed - 10f, 0);
        }
        ((GameStage)stage).addMovable(this);
        mPreviousVelocity = ((GameStage)stage).getCurrentVelocity().x;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!BodyUtils.bodyInBounds(body)||getUserData().isDead()) {
            PoolsHandler.sShellPool.free(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mRegion,
                body.getPosition().x - getUserData().getWidth()/2,
                body.getPosition().y -getUserData().getHeight()/2,
                getUserData().getWidth() * 0.5f, getUserData().getHeight() * 0.5f,
                getUserData().getWidth(), getUserData().getHeight()
                , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(body.getLinearVelocity().x
                + (speedScale-(mPreviousVelocity)), body.getLinearVelocity().y);
        mPreviousVelocity = speedScale;
    }
}
