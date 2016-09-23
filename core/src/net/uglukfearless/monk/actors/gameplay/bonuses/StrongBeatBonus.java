package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class StrongBeatBonus extends GameBonus {


    public StrongBeatBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("strong_beat");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_STRONG_BEAT");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 20;

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_NKF_KEY)) {
            mWorkingTime +=10;
        }
    }

    @Override
    public void activation() {
        mStage.getRunnerStrike().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_STRIKE_STRONG);
        ScoreCounter.increaseStrongBeat();
    }

    @Override
    public void deactivation() {
        mStage.getRunnerStrike().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_STRIKE);
    }
}
