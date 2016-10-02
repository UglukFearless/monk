package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 21.09.2016.
 */
public class GeniusTamesivari extends Achievement {

    public GeniusTamesivari() {

        mKey = PreferencesConstants.ACHIEVE_GT_KEY;
        mNameKey = "STATS_GENIUS_TAMESIVARI_ACH";
        mDescribeKey = "STATS_GENIUS_TAMESIVARI_ACH_D";
        mConditionKey = "STATS_GENIUS_TAMESIVARI_ACH_C";
        mImprovementKey = null;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_THUNDER_FIST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_MODIFY1")
                + "\n" + AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_MODIFY2"));
        mUpdateList.put(Constants.BONUS_RETRIBUTION_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_RETRIBUTION_MODIFY1")
                + "\n" + AssetLoader.sBundle.get("BONUS_UPGRADE_RETRIBUTION_MODIFY2"));

        mRegion = Constants.ACHIEVE_GT_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDestroyed()>=25000&&!isUnlock());
    }
}
