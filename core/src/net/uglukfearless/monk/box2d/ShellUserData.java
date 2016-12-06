package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 05.09.2016.
 */
public class ShellUserData extends UserData {

    private boolean mDead;
    private String KEY;
    private boolean mStrong;

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

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String key) {
        this.KEY = new String(key);
    }

    public void setStrong(boolean strong) {
        mStrong = strong;
    }

    public boolean isStrong() {
        return mStrong;
    }
}
