package net.uglukfearless.monk.box2d;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.enums.UserDataType;
import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class RunnerUserData extends UserData {

    private boolean jumping1;
    private boolean jumping2;
    private boolean dead;
    private boolean striking;

    private final Vector2 runningPosition = new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y);
    private Vector2 jumpingLinearImpulse;

    public RunnerUserData() {
        super();
        jumpingLinearImpulse = Constants.RUNNER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.RUNNER;
    }

    public RunnerUserData(float width, float height) {
        super(width, height);
        jumpingLinearImpulse = Constants.RUNNER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.RUNNER;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public float getDodgeAngle() {
        return (float) (-90f*(Math.PI/180f));
    }

    public Vector2 getRunningPosition() {
        return runningPosition;
    }


    public float getHitAngularImpulse() {
        return Constants.RUNNER_HIT_ANGULAR_IMPULSE;
    }

    public boolean isJumping1() {
        return jumping1;
    }

    public void setJumping1(boolean jumping1) {
        this.jumping1 = jumping1;
    }

    public boolean isJumping2() {
        return jumping2;
    }

    public void setJumping2(boolean jumping2) {
        this.jumping2 = jumping2;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isStriking() {
        return striking;
    }

    public void setStriking(boolean striking) {
        this.striking = striking;
    }

}
