package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 05.09.2016.
 */
public class ShellUserData extends UserData {

    private boolean mDead;

    public ShellUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.SHELL;
        mDead = false;
    }

    public void setDead(boolean dead) {
        mDead = dead;
    }

    public boolean isDead() {
        return mDead;
    }
}
