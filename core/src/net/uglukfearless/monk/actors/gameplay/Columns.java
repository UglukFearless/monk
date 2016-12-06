package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;

/**
 * Created by Ugluk on 25.06.2016.
 */
public class Columns extends GameActor implements Movable {

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

        if (!BodyUtils.bodyInBounds(body)&&body.isActive()) {
            this.remove();
            body.setActive(false);
            body.setTransform(-10,-10,0);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetLoader.environmentAtlas.findRegion("column"), body.getPosition().x - userData.getWidth() / 2,
                body.getPosition().y - userData.getHeight() / 2,
                userData.getWidth(), userData.getHeight());
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(new Vector2(speedScale, 0));
    }
}
