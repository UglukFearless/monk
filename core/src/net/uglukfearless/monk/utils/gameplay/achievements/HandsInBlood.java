package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class HandsInBlood extends Achievement {

    public HandsInBlood() {

        mKey = PreferencesConstants.ACHIEVE_HIB_KEY;
        mNameKey = "STATS_HAND_IN_BLOOD_ACH";
        mDescribeKey = "STATS_HAND_IN_BLOOD_ACH_D";
        mConditionKey = "STATS_HAND_IN_BLOOD_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_NKF_KEY;
        mNew = false;

        mRegion = Constants.ACHIEVE_HIB_REGION;
    }

    @Override
    public boolean checkCondition() {
        return  (PreferencesManager.getKilled()>=1&&!isUnlock());
    }
}
