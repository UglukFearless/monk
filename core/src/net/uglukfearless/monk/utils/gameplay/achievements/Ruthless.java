package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 19.09.2016.
 */
public class Ruthless extends Achievement {

    public Ruthless() {

        mKey = PreferencesConstants.ACHIEVE_RUTHLESS_KEY;
        mNameKey = "STATS_RUTHLESS_ACH";
        mDescribeKey = "STATS_RUTHLESS_ACH_D";
        mConditionKey = "STATS_RUTHLESS_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_RETRIBUTION_REGION , AssetLoader.sBundle.get("BONUS_UPGRADE_RETRIBUTION_TIME_1"));

        mRegion = Constants.ACHIEVE_RUTHLESS_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getKillPercent()>=70
                &&PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_NKF_KEY)
                &&!isUnlock());
    }
}
