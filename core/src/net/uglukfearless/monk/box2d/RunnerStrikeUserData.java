package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.UserDataType;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class RunnerStrikeUserData extends UserData {

    public RunnerStrikeUserData() {
        super();
        userDataType = UserDataType.RUNNER_STRIKE;
    }

    public RunnerStrikeUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.RUNNER_STRIKE;
    }
}
