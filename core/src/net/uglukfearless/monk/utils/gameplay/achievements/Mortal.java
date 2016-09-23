package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class Mortal extends Achievement {

    public Mortal() {

        mKey = PreferencesConstants.ACHIEVE_MORTAL_KEY;
        mNameKey = "STATS_MORTAL_ACH";
        mDescribeKey = "STATS_MORTAL_ACH_D";
        mConditionKey = "STATS_MORTAL_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mRegion = Constants.ACHIEVE_MORTAL_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDeaths()>=1&&!isUnlock());
    }
}
