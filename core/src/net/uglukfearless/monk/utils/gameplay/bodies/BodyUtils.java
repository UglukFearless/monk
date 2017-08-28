package net.uglukfearless.monk.utils.gameplay.bodies;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.actors.gameplay.Columns;
import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 19.05.2016.
 */
public class BodyUtils {

    private static UserData mUserData;

    public static boolean bodyInBounds(Body body) {
        mUserData = (UserData) body.getUserData();

        switch (mUserData.getUserDataType()) {
            case ENEMY:
                return (body.getPosition().x + mUserData.getWidth()/2f > 0)
                        &&(body.getPosition().y + mUserData.getHeight()/2f > 0)
                        &&(body.getPosition().y<25||!((EnemyUserData)body.getUserData()).isFly());
            case RUNNER:
            case OBSTACLE:
            case COLUMNS:
                return (body.getPosition().x + mUserData.getWidth()/2f > 0);
            case LUMP:
                return (body.getPosition().x + mUserData.getWidth()/2f + 4f > 0)
                        &&(body.getPosition().y + mUserData.getHeight()/2f > 0);
            case GROUND:
            case PIT:
                return (body.getPosition().x + mUserData.getWidth()/2f + 2.5f > 0);
            case RUNNER_STRIKE:
            case SHELL:
                return (body.getPosition().x + mUserData.getWidth()/2f > 0
                        &&body.getPosition().x - mUserData.getWidth()/2f < Constants.GAME_WIDTH + 1f
                        &&body.getPosition().y + mUserData.getHeight()/2f > 0);
        }

        return true;
    }

    public static boolean runnerIsFallDown(Body body) {
        UserData userData = (UserData) body.getUserData();
        return (body.getPosition().y + userData.getHeight()/2f < 0);
    }

    public static boolean runnerIsBehind(Body body) {
        UserData userData = (UserData) body.getUserData();
        return (body.getPosition().x + userData.getWidth()/2f < 0);
    }

    public static boolean bodyIsEnemy(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }

    public static boolean bodyIsRunnerStrike(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER_STRIKE;
    }

    public static boolean bodyIsLump(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.LUMP;
    }

    public static boolean bodyIsObstacle(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.OBSTACLE;
    }

    public static boolean bodyIsColumns(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.COLUMNS;
    }

    public static boolean bodyIsPit(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.PIT;
    }

    public static boolean bodyIsShell(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.SHELL;
    }

    public static boolean bodyIsBuddha(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.BUDDHA;
    }

    public static boolean bodyIsArmour(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.ARMOUR;
    }

    public static boolean bodyInFall(Body body) {

        mUserData = (UserData) body.getUserData();

        return (body.getPosition().y + mUserData.getHeight()/2f > 0);
    }
}
