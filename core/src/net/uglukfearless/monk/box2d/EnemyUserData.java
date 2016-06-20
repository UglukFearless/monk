package net.uglukfearless.monk.box2d;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 20.05.2016.
 */
public class EnemyUserData extends UserData {

    private Vector2 jumpingImpulse;
    private Vector2 linearVelocity;
    private String[] textureRegions;

    private int gravityScale;

    private float density;
    private float basicY;

    private boolean armour;
    private boolean jumper;
    private boolean shouter;
    private boolean striker;


    private boolean dead;
    private boolean jumping;
    private boolean doll;

    public EnemyUserData(EnemyType type) {
        super(type.getWidth(), type.getHeight());
        userDataType = UserDataType.ENEMY;
        this.textureRegions = type.getRegions();
        linearVelocity = type.getLinearVelocity();
        jumpingImpulse = type.getJumpingImpulse();
        gravityScale = type.getGravityScale();

        density = type.getDensity();
        basicY = type.getY();

        armour = type.isArmour();
        jumper = type.isJumper();
        shouter = type.isShouter();
        striker = type.isStriker();

        dead = false;
        jumping = false;
        doll = false;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public String[] getTextureRegions() {
        return textureRegions;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Vector2 getJumpingImpulse() {
        return jumpingImpulse;
    }

    public void setJumpingImpulse(Vector2 jumpingImpulse) {
        this.jumpingImpulse = jumpingImpulse;
    }

    public int getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(int gravityScale) {
        this.gravityScale = gravityScale;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getBasicY() {
        return basicY;
    }

    public void setBasicY(float basicY) {
        this.basicY = basicY;
    }

    public boolean isArmour() {
        return armour;
    }

    public void setArmour(boolean armour) {
        this.armour = armour;
    }

    public boolean isJumper() {
        return jumper;
    }

    public void setJumper(boolean jumper) {
        this.jumper = jumper;
    }

    public boolean isShouter() {
        return shouter;
    }

    public void setShouter(boolean shouter) {
        this.shouter = shouter;
    }

    public boolean isStriker() {
        return striker;
    }

    public void setStriker(boolean striker) {
        this.striker = striker;
    }

    public boolean isDoll() {
        return doll;
    }

    public void setDoll(boolean doll) {
        this.doll = doll;
    }
}
