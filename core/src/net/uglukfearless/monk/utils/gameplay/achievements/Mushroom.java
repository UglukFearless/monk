package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class Mushroom extends Achievement {

    public Mushroom() {

        mKey = PreferencesConstants.ACHIEVE_MUSHROOM_KEY;
        mNameKey = "STATS_MUSHROOM_ACH";
        mDescribeKey = "STATS_MUSHROOM_ACH_D";
        mConditionKey = "STATS_MUSHROOM_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_BUDDHA_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_BUDDHA_TIME_1"));

        mRegion = Constants.ACHIEVE_MUSHROOM_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getTime()>=18000&&!isUnlock());
    }
}
