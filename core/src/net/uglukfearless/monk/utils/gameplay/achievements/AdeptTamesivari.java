package net.uglukfearless.monk.utils.gameplay.achievements;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public class AdeptTamesivari extends Achievement {

    public AdeptTamesivari() {

        mKey = PreferencesConstants.ACHIEVE_AT_KEY;
        mNameKey = "STATS_ADEPT_TAMESIVARI_ACH";
        mDescribeKey = "STATS_ADEPT_TAMESIVARI_ACH_D";
        mConditionKey = "STATS_ADEPT_TAMESIVARI_ACH_C";
        mImprovementKey = PreferencesConstants.ACHIEVE_MT_KEY;
        mNew = false;

        mUpdateList = new HashMap<String, String>();
        mUpdateList.put(Constants.BONUS_THUNDER_FIST_REGION, AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_MODIFY1")
                + "\n" + AssetLoader.sBundle.get("BONUS_UPGRADE_THUNDER_MODIFY2"));

        mRegion = Constants.ACHIEVE_AT_REGION;
    }

    @Override
    public boolean checkCondition() {
        return (PreferencesManager.getDestroyed()>=500&&!isUnlock());
    }
}
