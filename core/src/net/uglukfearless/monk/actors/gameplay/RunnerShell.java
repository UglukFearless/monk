package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.RunnerStrikeUserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class RunnerShell extends GameActor implements Pool.Poolable{

    private TextureRegion mRegion;

    public RunnerShell(World world) {
        super(WorldUtils.createRunnerShell(world));

        mRegion = new TextureRegion(AssetLoader.playerShell);
    }

    @Override
    public RunnerStrikeUserData getUserData() {
        return (RunnerStrikeUserData) userData;
    }

    @Override
    public void reset() {
        body.setActive(false);
        this.remove();
        getUserData().setDead(false);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE_FLIP);
        body.setGravityScale(0);
        body.setTransform(-10, -10, 0);
    }

    public void init(Stage stage, Vector2 position, int thunderFistLevel) {
        body.setTransform(position.x + 1.3f, position.y + 1f, 0);
        body.setLinearVelocity(42f, 0);
        if (thunderFistLevel==1) {
            getUserData().setPiercing1(true);
        }
        if (thunderFistLevel==2) {
            getUserData().setPiercing2(true);
        }
        body.setActive(true);
        ((GameStage)stage).addToActionLayout(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!BodyUtils.bodyInBounds(body)||getUserData().isDead()) {
            PoolsHandler.sRunnerShellPool.free(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (inFrame()) {
            batch.draw(mRegion,
                    body.getPosition().x - getUserData().getWidth()/2,
                    body.getPosition().y -getUserData().getHeight()/2,
                    getUserData().getWidth() * 0.5f, getUserData().getHeight() * 0.5f,
                    getUserData().getWidth(), getUserData().getHeight()
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        }
    }
}
