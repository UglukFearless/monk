package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.GroundUserData;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.SpaceTable;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Ground extends net.uglukfearless.monk.actors.gameplay.GameActor {


    private Vector2 mVelocity = Constants.NULL_VELOCITY;

    public Ground(Body body) {
        super(body);
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) userData;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        body.setLinearVelocity(mVelocity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.disableBlending();
        batch.draw(AssetLoader.ground, body.getPosition().x - userData.getWidth()/2,
                body.getPosition().y - userData.getHeight()/2,
                userData.getWidth(), userData.getHeight());
        batch.enableBlending();

    }


    public void setVelocity(Vector2 velocity) {
        mVelocity = velocity;
    }
}
