package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 04.09.2016.
 */
public class PitUserData extends UserData {

    private boolean mColumns;

    public PitUserData(float width, float height) {
        this.width = width;
        this.height = height;
        userDataType = UserDataType.PIT;
    }

    public void setColumns(boolean columns) {
        mColumns = columns;
    }

    public boolean isColumns() {
        return mColumns;
    }
}
