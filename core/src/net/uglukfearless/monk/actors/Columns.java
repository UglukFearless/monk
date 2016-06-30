package net.uglukfearless.monk.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.ColumnsUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.AssetLoader;

/**
 * Created by Ugluk on 25.06.2016.
 */
public class Columns extends GameActor {

    public Columns(Body body) {
        super(body);
        body.setLinearVelocity(Constants.COLUMNS_LINEAR_VELOCITY);
    }

    @Override
    public UserData getUserData() {
        return (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getUserData()!=null) {
            if (getUserData().isDestroy()) {
                body.setLinearVelocity(Constants.COLUMNS_LINEAR_VELOCITY);
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(AssetLoader.ground, body.getPosition().x - userData.getWidth() / 2,
                body.getPosition().y - userData.getHeight() / 2,
                userData.getWidth(), userData.getHeight());

    }
}
