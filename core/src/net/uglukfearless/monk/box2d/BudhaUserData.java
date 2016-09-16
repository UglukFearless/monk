package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 12.09.2016.
 */
public class BudhaUserData extends UserData {

    public BudhaUserData(float width, float height) {
        super(width, height);

        userDataType = UserDataType.BUDDHA;
    }
}
