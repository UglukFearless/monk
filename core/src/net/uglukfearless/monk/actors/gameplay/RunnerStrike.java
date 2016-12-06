package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.RunnerStrikeUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.WeaponDistance;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class RunnerStrike extends GameActor {

    private Runner runner;
    private Body mRunnerDefBody;

    public RunnerStrike(Body body, Runner runner) {
        super(body);
        this.runner = runner;

        mRunnerDefBody = WorldUtils.createRunnerDefStrike(body.getWorld());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if ((((GameStage)getStage()).getRunner()!=null)&&!((GameStage)getStage()).getRunner().getUserData().isDead()&&
                runner.getWeaponType()==null&&getDistance().equals(WeaponDistance.SHORT)) {
            if (runner.getUserData().isStriking()) {
                body.setTransform(runner.getBody().getPosition().x - 0.2f + Constants.RUNNER_WIDTH/2,
                        runner.getBody().getPosition().y, 0f);

                mRunnerDefBody.setTransform(runner.getBody().getPosition().x,
                        runner.getBody().getPosition().y, 0f);
            } else {
                body.setTransform(3f,-10f, 0f);
                mRunnerDefBody.setTransform(3f,-10f, 0f);
            }
        } else if ((((GameStage)getStage()).getRunner()!=null)&&!((GameStage)getStage()).getRunner().getUserData().isDead()&&
                runner.getUserData().isStriking()&&runner.getWeaponType()!=null&&runner.getWeaponType().getDistance().equals(getDistance())) {
            body.setTransform(runner.getBody().getPosition().x + runner.getWeaponType().getxOffset() + Constants.RUNNER_WIDTH/2,
                    runner.getBody().getPosition().y + runner.getWeaponType().getyOffset(), 0f);
            mRunnerDefBody.setTransform(runner.getBody().getPosition().x,
                    runner.getBody().getPosition().y, 0f);
        } else {
            body.setTransform(3f,-10f, 0f);
            mRunnerDefBody.setTransform(3f,-10f, 0f);
        }

    }

    @Override
    public RunnerStrikeUserData getUserData() {
        return (RunnerStrikeUserData) body.getUserData();
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public WeaponDistance getDistance() {
        return getUserData().getWeaponDistance();
    }

    public Body getRunnerDefBody() {
        return mRunnerDefBody;
    }
}
