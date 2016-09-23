package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class NeophyteKunFu extends Achievement {

    public NeophyteKunFu() {
        mKey = PreferencesConstants.ACHIEVE_NKF_KEY;
        mNameKey = "STATS_NEOPHYTE_KUN_FU_ACH";
        mDescribeKey = "STATS_NEOPHYTE_KUN_FU_ACH_D";
        mConditionKey = "STATS_NEOPHYTE_KUN_FU_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_AKF_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_STRONG_BEAT_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_STRONG_TIME_1"));

        mRegion = Constants.ACHIEVE_NKF_REGION;
    }

    @Override
    public boolean checkCondition() {
        return  (PreferencesManager.getKilled()>=100&&!isUnlock());
    }
}
