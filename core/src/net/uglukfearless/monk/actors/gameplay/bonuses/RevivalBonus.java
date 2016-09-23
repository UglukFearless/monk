package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 13.09.2016.
 */
public class RevivalBonus extends GameBonus {


    public RevivalBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("revival");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_REVIVAL");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 1.5f;



        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_WOS_KEY)) {
            mStage.addStartRevival();
        }
    }

    @Override
    public void activation() {
        mStage.addRevival();
        ScoreCounter.increaseRevival();
    }

    @Override
    public void deactivation() {

    }
}
