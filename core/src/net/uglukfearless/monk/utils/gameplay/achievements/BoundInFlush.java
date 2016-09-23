package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class BoundInFlush extends Achievement {

    public BoundInFlush() {

        mKey = PreferencesConstants.ACHIEVE_BIF_KEY;
        mNameKey = "STATS_BOUND_IN_FLASH_ACH";
        mDescribeKey = "STATS_BOUND_IN_FLASH_ACH_D";
        mConditionKey = "STATS_BOUND_IN_FLASH_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_REBORN_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_GHOST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_GHOST_TIME1"));

        mRegion = Constants.ACHIEVE_BIF_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDeaths()>=100&&!isUnlock());
    }
}
