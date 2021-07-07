package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class RetributionBonus extends GameBonus {

    private int mLevel;

    public RetributionBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("retribution");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_RETRIBUTION");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 10;

        mLevel = 0;

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_RUTHLESS_KEY)) {
            mWorkingTime +=5;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_GKF_KEY)) {
            mWorkingTime +=5;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_AT_KEY)) {
            mLevel = 1;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_GT_KEY)) {
            mLevel = 2;
        }
    }

    @Override
    public void activation() {
        mStage.getRunner().setRetribution(true, mLevel);
        ScoreCounter.increaseRetribution();
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setRetribution(false, mLevel);
    }
}
