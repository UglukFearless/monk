package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.ArmourUserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.utils.file.PreferencesManager;

/**
 * Created by Ugluk on 07.11.2016.
 */
public class Armour extends GameActor {

    private Runner mRunner;

    private float mHitTime;
    private final float mHitThreshold;
    private boolean mBroken;

    public Armour(Body body, Runner runner) {
        super(body);

        mRunner = runner;
        if (PreferencesManager.getArmour()!=null) {
            mRunner.armouring(this);
        } else {
            mRunner.setArmour(this);
        }

        mHitTime = 0;
        mHitThreshold = 2;

        mBroken=false;
    }

    @Override
    public ArmourUserData getUserData() {
        return (ArmourUserData)body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mRunner==null) {
            getUserData().destroyArmour();
            this.remove();
        } else if (!getUserData().isActive()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_GHOST);
            body.setTransform(-10, -10, 0);
        } else if (getUserData().isWait()) {

            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_GHOST);
            body.setTransform(-10,-10, 0);

            if (getUserData().isHit()) {
                if (getUserData().getCurrentStrength()<=0&&!mBroken) {
                    mRunner.unarmoured();
                    mBroken = true;
                }
                getUserData().setHit(false);
                mHitTime = 0;
                if (getUserData().getCurrentStrength()<=0) {
                    getUserData().destroyArmour();
                    mRunner.setCustomFilter();
                } else {
                    body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
                }
            }
        } else if (getUserData().isHit()){
            if (getUserData().getCurrentStrength()<=0&&!mBroken) {
                mRunner.unarmoured();
                mBroken = true;
            } else if (getUserData().getHitted()) {
                body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_GHOST);
                getUserData().setHitted(false);
            }
            mHitTime +=delta;
            if (mHitTime > mHitThreshold) {
                getUserData().setHit(false);
                mHitTime = 0;
                if (getUserData().getCurrentStrength()<=0) {
                    getUserData().destroyArmour();
                    mRunner.setCustomFilter();
                } else {
                    body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
                }
            }
        } else  {
            body.setTransform(mRunner.getBody().getPosition(), 0);
            body.setLinearVelocity(0f,0f);
            body.setAngularVelocity(0);
        }
    }

    public void hide() {
        getUserData().setWait(true);
    }

    public void unhide() {
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
        getUserData().setWait(false);
    }

    public void activate(ArmourType armourType) {

        mHitTime = 0;

        mBroken=false;

        getUserData().reset(armourType);

    }

    public void setRunner(Runner runner) {
        mRunner = runner;
    }

    public void setRunnerPassive(Runner runner) {
        mRunner = runner;

        mHitTime = 0;

        getUserData().nullReset();
    }
}
