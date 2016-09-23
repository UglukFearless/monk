package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class WheelOfSamsara extends Achievement {

    public WheelOfSamsara() {

        mKey = PreferencesConstants.ACHIEVE_WOS_KEY;
        mNameKey = "STATS_WHEEL_OF_SUMSARA_ACH";
        mDescribeKey = "STATS_WHEEL_OF_SUMSARA_ACH_D";
        mConditionKey = "STATS_WHEEL_OF_SUMSARA_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_GHOST_REGION, "- время +10с");
        mUpdateList.put(Constants.BONUS_GHOST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_GHOST_TIME1"));
        mUpdateList.put(Constants.BONUS_REVIVAL_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_REVIVAL_MODIFY1")
                + "\n" + AssetLoader.sBundle.get("BONUS_UPGRADE_REVIVAL_MODIFY2"));

        mRegion = Constants.ACHIEVE_WOS_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDeaths()>=1000&&!isUnlock());
    }
}
