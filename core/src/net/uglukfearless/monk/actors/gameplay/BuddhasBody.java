package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.BudhaUserData;
import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 12.09.2016.
 */
public class BuddhasBody extends GameActor {


    private Runner mRunner;

    public BuddhasBody(Body body, Runner runner) {
        super(body);
        mRunner = runner;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mRunner.isBuddha()) {
            body.setTransform(mRunner.getBody().getPosition().x,
                    mRunner.getBody().getPosition().y, 0f);
        } else {
            body.setTransform(-10f,-10f, 0f);
        }

    }

    @Override
    public BudhaUserData getUserData() {
        return (BudhaUserData) userData;
    }

    public void setRunner(Runner runner) {
        mRunner = runner;
    }
}
