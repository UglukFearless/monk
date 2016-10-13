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
    private boolean mLaunched;

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

    public void setUserDataType(UserDataType userDataType) {
        this.userDataType = userDataType;
    }


    //для реализации раскидывания мощным ударом. Здесь во имя упрощения кода и оптимизации
    public void setLaunched(boolean launched) {
        mLaunched = launched;
    }
    public boolean isLaunched() {
        return mLaunched;
    }
}
