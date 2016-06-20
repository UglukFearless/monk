package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 02.06.2016.
 */
public class LumpUserData extends UserData {
    public LumpUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.LUMP;
    }
}
