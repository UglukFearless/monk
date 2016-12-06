package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 12.09.2016.
 */
public class BudhaUserData extends UserData {

    private boolean mDragon;

    public BudhaUserData(float width, float height) {
        super(width, height);

        userDataType = UserDataType.BUDDHA;

        mDragon = false;
    }

    public BudhaUserData(float width, float height, boolean dragon) {
        super(width, height);

        userDataType = UserDataType.BUDDHA;

        mDragon = true;
    }

    public boolean isDragon() {
        return mDragon;
    }
}
