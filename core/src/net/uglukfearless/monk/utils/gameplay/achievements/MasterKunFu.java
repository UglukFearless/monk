package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class MasterKunFu extends Achievement {

    public MasterKunFu() {
        mKey = PreferencesConstants.ACHIEVE_MKF_KEY;
        mNameKey = "STATS_MASTER_KUN_FU_ACH";
        mDescribeKey = "STATS_MASTER_KUN_FU_ACH_D";
        mConditionKey = "STATS_MASTER_KUN_FU_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_GKF_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_STRONG_BEAT_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_STRONG_TIME_1"));
        mUpdateList.put(Constants.BONUS_THUNDER_FIST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_TIME_2"));

        mRegion = Constants.ACHIEVE_MKF_REGION;
    }

    @Override
    public boolean checkCondition() {
        return  (PreferencesManager.getKilled()>=10000&&!isUnlock());
    }
}
