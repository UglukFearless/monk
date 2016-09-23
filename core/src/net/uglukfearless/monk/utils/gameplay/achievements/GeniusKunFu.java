package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 21.09.2016.
 */
public class GeniusKunFu extends Achievement {

    public GeniusKunFu() {

        mKey = PreferencesConstants.ACHIEVE_GKF_KEY;
        mNameKey = "STATS_GENIUS_KUN_FU_ACH";
        mDescribeKey = "STATS_GENIUS_KUN_FU_ACH_D";
        mConditionKey = "STATS_GENIUS_KUN_FU_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_STRONG_BEAT_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_STRONG_TIME_1"));
        mUpdateList.put(Constants.BONUS_THUNDER_FIST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_TIME_2"));
        mUpdateList.put(Constants.BONUS_RETRIBUTION_REGION ,AssetLoader.sBundle.get("BONUS_UPGRADE_RETRIBUTION_TIME_1"));

        mRegion = Constants.ACHIEVE_GKF_REGION;
    }

    @Override
    public boolean checkCondition() {
        return  (PreferencesManager.getKilled()>=50000&&!isUnlock());
    }
}
