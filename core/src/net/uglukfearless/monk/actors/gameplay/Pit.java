package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.PitUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;

/**
 * Created by Ugluk on 04.09.2016.
 */
public class Pit extends GameActor implements Movable {

    private TextureRegion pitRegionLeft;
    private TextureRegion pitRegionRight;

    public Pit(Body body) {
        super(body);
        body.setLinearVelocity(Constants.WORLD_STATIC_VELOCITY_INIT);

        if (userData.getWidth()>Constants.GROUND_PIT_INIT) {
            getUserData().setColumns(true);
        }

        pitRegionLeft = AssetLoader.environmentAtlas.findRegion("pitLeft");
        pitRegionRight = AssetLoader.environmentAtlas.findRegion("pitRight");
    }


    @Override
    public void act(float delta) {

        if (!BodyUtils.bodyInBounds(body)&&body.isActive()) {
            this.remove();
            body.setActive(false);
            body.setTransform(-10,-10,0);
        }

        SpaceTable.setPit(body.getPosition().x + userData.getWidth()/2, getUserData().isColumns());
        SpaceTable.setPit(body.getPosition().x - userData.getWidth()/2, getUserData().isColumns());
    }

    @Override
    public PitUserData getUserData() {
        return (PitUserData) body.getUserData();
    }

    public void setPosition(float x) {
        body.setTransform(x + userData.getWidth()/2,userData.getHeight()/2,0);
        body.setActive(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(pitRegionLeft, body.getPosition().x - userData.getWidth()/2f - 1f,
                Constants.GROUND_Y - userData.getHeight() + Constants.GROUND_HEIGHT*(Constants.GROUND_HEIGHT_FIX_INIT - 1)/2f
                , 2.5f, userData.getHeight()*2f*Constants.GROUND_HEIGHT_FIX_INIT);
        batch.draw(pitRegionRight, body.getPosition().x + userData.getWidth()/2f - 1.5f,
                Constants.GROUND_Y - userData.getHeight() + Constants.GROUND_HEIGHT*(Constants.GROUND_HEIGHT_FIX_INIT - 1)/2f
                , 2.5f , userData.getHeight()*2f*Constants.GROUND_HEIGHT_FIX_INIT);

    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(new Vector2(speedScale, 0));
    }
}
