package net.uglukfearless.monk.actors;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.RunnerStrikeUserData;
import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class RunnerStrike extends GameActor {

    private Runner runner;

    public RunnerStrike(Body body, Runner runner) {
        super(body);
        this.runner = runner;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (runner.getUserData().isStriking()) {
            body.setTransform(runner.getBody().getPosition().x - 0.2f + Constants.RUNNER_WIDTH/2,
                    runner.getBody().getPosition().y, 0f);
        } else {
            body.setTransform(3f,-10f, 0f);
        }

    }

    @Override
    public RunnerStrikeUserData getUserData() {
        return (RunnerStrikeUserData) body.getUserData();
    }
}
