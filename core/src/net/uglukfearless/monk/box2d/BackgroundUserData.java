package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 30.08.2016.
 */
public class BackgroundUserData extends UserData {

    public BackgroundUserData(float width, float height) {
        this.width = width;
        this.height = height;
        userDataType = UserDataType.BACKGROUND;
    }
}
