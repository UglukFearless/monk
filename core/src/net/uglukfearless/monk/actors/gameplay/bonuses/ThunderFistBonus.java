package net.uglukfearless.monk.actors.gameplay.bonuses;


import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class ThunderFistBonus extends GameBonus {

    private int mLevel;

    public ThunderFistBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("thunder_fist");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_THUNDER_FIST");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 10;

        mLevel = 0;

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_AKF_KEY)) {
            mWorkingTime +=10;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_MKF_KEY)) {
            mWorkingTime +=10;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_NT_KEY)) {
            mLevel = 1;
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_AT_KEY)) {
            mLevel = 2;
        }
    }

    @Override
    public void activation() {
        mStage.getRunner().setThunderFist(true, mLevel);
        ScoreCounter.increaseThunderFist();
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setThunderFist(false, mLevel);
    }
}
