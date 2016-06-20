package net.uglukfearless.monk.utils;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 19.05.2016.
 */
public class BodyUtils {

    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();

        switch (userData.getUserDataType()) {
            case ENEMY:
            case RUNNER:
            case OBSTACLE:
                return (body.getPosition().x + userData.getWidth()/2 > 0)
                        &&(body.getPosition().y + userData.getHeight()/2 > 0);
            case LUMP:
                return (body.getPosition().x + userData.getWidth()/2 + 4 > 0)
                        &&(body.getPosition().y + userData.getHeight()/2 > 0);
            case GROUND:
                return (body.getPosition().x + userData.getWidth()/2 > 0);
        }

        return true;
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
}
