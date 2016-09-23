package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.GroundUserData;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.Movable;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Ground extends GameActor  implements Movable {

    private Vector2 mPosition;

    public Ground(Body body) {
        super(body);

        mPosition = new Vector2(body.getPosition());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        mPosition.set(body.getPosition().x - getUserData().getWidth()/2,
                body.getPosition().y - getUserData().getHeight()/2);
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) userData;
    }
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.disableBlending();
        batch.draw(AssetLoader.ground, body.getPosition().x - userData.getWidth() / 2,
                body.getPosition().y - userData.getHeight() / 2,
                userData.getWidth(), userData.getHeight());
        batch.enableBlending();

    }


    public void setVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(new Vector2(speedScale, 0));
    }

    public Vector2 getPosition() {
        return mPosition;
    }
}
