package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.PitUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.SpaceTable;

/**
 * Created by Ugluk on 04.09.2016.
 */
public class Pit extends GameActor {


    public Pit(Body body) {
        super(body);
        body.setLinearVelocity(Constants.WORLD_STATIC_VELOCITY);

        if (userData.getWidth()>Constants.GROUND_PIT) {
            getUserData().setColumns(true);
        }
    }


    @Override
    public void act(float delta) {

        if (!BodyUtils.bodyInBounds(body)&&body.isActive()) {
            this.remove();
            body.setActive(false);
            body.setTransform(-10,-10,0);
        }

        SpaceTable.setPit(body.getPosition().x + userData.getWidth()/2, getUserData().isColumns());
    }

    @Override
    public PitUserData getUserData() {
        return (PitUserData) body.getUserData();
    }

    public void setPosition(float x) {
        body.setTransform(x + userData.getWidth()/2,userData.getHeight()/2,0);
        body.setActive(true);
    }
}
