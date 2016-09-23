package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 21.09.2016.
 */
public class Reborn extends Achievement {

    public Reborn() {

        mKey = PreferencesConstants.ACHIEVE_REBORN_KEY;
        mNameKey = "STATS_REBORN_ACH";
        mDescribeKey = "STATS_REBORN_ACH_D";
        mConditionKey = "STATS_REBORN_ACH_С";
        mImprovementKey = PreferencesConstants.ACHIEVE_WOS_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_GHOST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_GHOST_TIME1"));
        mUpdateList.put(Constants.BONUS_REVIVAL_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_REVIVAL_MODIFY1"));

        mRegion = Constants.ACHIEVE_REBORN_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDeaths()>=500&&!isUnlock());
    }
}
