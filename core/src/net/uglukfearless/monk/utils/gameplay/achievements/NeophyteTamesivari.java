package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class NeophyteTamesivari extends Achievement {

    public NeophyteTamesivari() {

        mKey = PreferencesConstants.ACHIEVE_NT_KEY;
        mNameKey = "STATS_NEOPHYTE_TAMESIVARI_ACH";
        mDescribeKey = "STATS_NEOPHYTE_TAMESIVARI_ACH_D";
        mConditionKey = "STATS_NEOPHYTE_TAMESIVARI_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_AT_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_THUNDER_FIST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_MODIFY1"));

        mRegion = Constants.ACHIEVE_NT_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDestroyed()>=50&&!isUnlock());
    }
}
