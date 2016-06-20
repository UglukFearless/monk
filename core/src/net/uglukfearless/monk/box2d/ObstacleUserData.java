package net.uglukfearless.monk.box2d;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 07.06.2016.
 */
public class ObstacleUserData extends UserData {

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String [] regions;
    private Vector2 linearVelocity;
    private int gravityScale;

    private boolean armour;
    private boolean isTrap;
    private boolean isSphere;
    private final boolean isBlades;

    private boolean dead;

    public ObstacleUserData(ObstacleType type) {
        width = type.getWidth();
        height = type.getHeight();
        x = type.getX();
        y = type.getY();

        density = type.getDensity();
        linearVelocity = type.getLinearVelocity();
        gravityScale = type.getGravityScale();

        regions = type.getRegions();

        armour = type.isArmour();
        isTrap = type.isTrap();
        isSphere = type.isSphere();

        userDataType = UserDataType.OBSTACLE;

        if (type==ObstacleType.BLADES) {
            isBlades=true;
        } else {
            isBlades=false;
        }
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public String[] getRegions() {
        return regions;
    }

    public void setRegions(String[] regions) {
        this.regions = regions;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public int getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(int gravityScale) {
        this.gravityScale = gravityScale;
    }

    public boolean isArmour() {
        return armour;
    }

    public void setArmour(boolean armour) {
        this.armour = armour;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public void setIsTrap(boolean isTrap) {
        this.isTrap = isTrap;
    }

    public boolean isSphere() {
        return isSphere;
    }

    public void setIsSphere(boolean isSphere) {
        this.isSphere = isSphere;
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
}
