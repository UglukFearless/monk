package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class RunnerStrikeUserData extends UserData {

    private boolean mDead;
    private boolean mShell;

    public RunnerStrikeUserData() {
        super();
        userDataType = UserDataType.RUNNER_STRIKE;
        width = Constants.RUNNER_WIDTH * 2.4f;
        height = Constants.RUNNER_WIDTH * 2.4f;
        mShell = false;
    }

    public RunnerStrikeUserData(float width, float height, boolean shell) {
        super(width, height);
        userDataType = UserDataType.RUNNER_STRIKE;
        mShell = true;
    }

    public void setDead(boolean dead) {
        mDead = dead;
    }

    public boolean isDead() {
        return mDead;
    }

    public boolean isShell() {
        return mShell;
    }
}
