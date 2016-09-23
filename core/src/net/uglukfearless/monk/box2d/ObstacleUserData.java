package net.uglukfearless.monk.box2d;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 07.06.2016.
 */
public class ObstacleUserData extends UserData {

    private Vector2 linearVelocity;
    private int gravityScale;

    private final boolean isBlades;

    private boolean dead;

    private float scaleX;
    private float scaleY;
    private float offsetX;
    private float offsetY;

    public ObstacleType  obstacleType;

    public ObstacleUserData(ObstacleType type) {
        width = type.getWidth();
        height = type.getHeight();

        linearVelocity = type.getLinearVelocity();

        gravityScale = type.getGravityScale();


        userDataType = UserDataType.OBSTACLE;

        //С этим нужно что-то сделать
        if (type==ObstacleType.BLADES) {
            isBlades=true;
        } else {
            isBlades=false;
        }

        scaleX = type.getTextureScaleX();
        scaleY = type.getTextureScaleY();
        offsetX = type.getTextureOffsetX();
        offsetY = type.getTextureOffsetY();

        obstacleType = type;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }


    public float getY() {
        return obstacleType.getY();
    }


    public float getDensity() {
        return obstacleType.getDensity();
    }


    public String[] getRegions() {
        return obstacleType.getRegions();
    }


    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity.set(linearVelocity);
    }

    public void setLinearVelocity(float x, float y) {
        this.linearVelocity.set(x, y);
    }

    public int getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(int gravityScale) {
        this.gravityScale = gravityScale;
    }

    public boolean isArmour() {
        return obstacleType.isArmour();
    }

    public boolean isTrap() {
        return obstacleType.isTrap();
    }


    public boolean isSphere() {
        return obstacleType.isSphere();
    }


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isBlades() {
        return isBlades;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public String getKEY() {
        return obstacleType.getKEY();
    }
}
