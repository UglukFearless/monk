package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class RunnerStrikeUserData extends UserData {

    private boolean mDead;
    private boolean mShell;
    private boolean mPiercing1;
    private boolean mPiercing2;

    public RunnerStrikeUserData() {
        super();
        userDataType = UserDataType.RUNNER_STRIKE;
        width = Constants.RUNNER_WIDTH * 2.4f;
        height = Constants.RUNNER_WIDTH * 2.4f;
        mShell = false;
        mPiercing1 = true;
        mPiercing2 = false;
    }

    public RunnerStrikeUserData(float width, float height, boolean shell) {
        super(width, height);
        userDataType = UserDataType.RUNNER_STRIKE;
        mShell = true;
        mPiercing1 = false;
        mPiercing2 = false;
    }

    public void setDead(boolean dead) {
        mDead = dead;
    }

    public void setPiercing1(boolean piercing) {
        mPiercing1 = piercing;
    }

    public void setPiercing2(boolean piercing2) {
        mPiercing2 = piercing2;
    }

    public boolean isDead() {
        return mDead;
    }

    public boolean isShell() {
        return mShell;
    }

    public boolean isPiercing1() {
        return mPiercing1;
    }

    public boolean isPiercing2() {
        return mPiercing2;
    }
}
