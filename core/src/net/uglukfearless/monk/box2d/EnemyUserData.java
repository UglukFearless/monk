package net.uglukfearless.monk.box2d;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 20.05.2016.
 */
public class EnemyUserData extends UserData {


    private Vector2 linearVelocity;

    private int gravityScale;


    private boolean dead;
    private boolean jumping;
    private boolean doll;
    private boolean mStrike;
    private boolean mShoot;

    private float scaleX;
    private float scaleY;
    private float offsetX;
    private float offsetY;

    //временное
    private EnemyType  enemyType;


    public EnemyUserData(EnemyType type) {
        super(type.getWidth(), type.getHeight());
        userDataType = UserDataType.ENEMY;

        width = type.getWidth();
        height = type.getHeight();

        linearVelocity = type.getLinearVelocity();
        gravityScale = type.getGravityScale();

        dead = false;
        jumping = false;
        doll = false;
        mStrike = false;
        mShoot = false;

        scaleX = type.getTextureScaleX();
        scaleY = type.getTextureScaleY();
        offsetX = type.getTextureOffsetX();
        offsetY = type.getTextureOffsetY();

        this.enemyType = type;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
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

    public float getJumpingImpulse() {
        return enemyType.getJumpingImpulse();
    }

    public int getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(int gravityScale) {
        this.gravityScale = gravityScale;
    }

    public float getDensity() {
        return enemyType.getDensity();
    }

    public float getBasicY() {
        return enemyType.getY();
    }

    public boolean isArmour() {
        return enemyType.isArmour();
    }

    public boolean isJumper() {
        return enemyType.isJumper();
    }

    public boolean isShouter() {
        return enemyType.isShouter();
    }


    public boolean isStriker() {
        return enemyType.isStriker();
    }


    public boolean isDoll() {
        return doll;
    }

    public void setDoll(boolean doll) {
        this.doll = doll;
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

    public void setStrike(boolean strike) {
        mStrike = strike;
    }

    public boolean isStrike() {
        return mStrike;
    }

    public boolean isShoot() {
        return mShoot;
    }

    public void setShoot(boolean shoot) {
        mShoot = shoot;
    }

    public String getKEY() {
        return enemyType.getKEY();
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }


    //геттеры по анимациям
    public TextureRegion getShellRegion() {
        return enemyType.getShellRegion();
    }

    public Animation getStayAnimation() {
        return enemyType.getStayAnimation();
    }

    public Animation getRunAnimation() {
        return enemyType.getRunAnimation();
    }

    public Animation getJumpAnimation() {
        return enemyType.getJumpAnimation();
    }

    public Animation getStrikeAnimation() {
        return enemyType.getStrikeAnimation();
    }

    public Animation getDieAnimation() {
        return enemyType.getDieAnimation();
    }

    public Animation getAnimation() {
        return enemyType.getAnimation();
    }
}
