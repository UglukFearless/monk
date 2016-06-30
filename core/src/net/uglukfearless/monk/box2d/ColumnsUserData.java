package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 25.06.2016.
 */
public class ColumnsUserData extends UserData {

    public ColumnsUserData(float width, float height) {
        super(width, height);

        userDataType = UserDataType.COLUMNS;
    }
}
