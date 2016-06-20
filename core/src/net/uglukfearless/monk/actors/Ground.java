package net.uglukfearless.monk.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.GroundUserData;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Ground extends GameActor {



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

//        if (leftBoundsReached(delta)) {
//            resetBounds();
//        } else {
//            updateXBounds(delta);
//        }
        body.setLinearVelocity(Constants.GROUND_LINEAR_VELOCITY); //!
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(AssetLoader.ground, body.getPosition().x - userData.getWidth()/2,
                body.getPosition().y - userData.getHeight()/2,
                userData.getWidth(), userData.getHeight());

    }

//    private boolean leftBoundsReached(float delta) {
//        return (body.getPosition().x - userData.getWidth()/2) <= 0; //- (delta*speed)
//    }

//    private void resetBounds() {
//        textureRegionBounds1 = textureRegionBounds2;
//        textureRegionBounds2 = new Rectangle(textureRegionBounds1.x + userData.getWidth(),
//                0, userData.getWidth(),  userData.getHeight());
//    }
}
