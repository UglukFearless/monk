package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 18.05.2016.
 */
public abstract class UserData {

    protected UserDataType userDataType;
    protected float width;
    protected float height;

    private boolean destroy;

    public UserData() {
        destroy = false;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
        destroy = false;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
}
