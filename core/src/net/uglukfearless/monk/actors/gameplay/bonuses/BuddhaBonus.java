package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 12.09.2016.
 */
public class BuddhaBonus extends GameBonus {
    public BuddhaBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion(Constants.BONUS_BUDDHA_REGION);
        mName = AssetLoader.sBundle.get("PLAY_BONUS_BUDDHA");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 10;
        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_MUSHROOM_KEY)) {
            mWorkingTime+=5;
        }
    }

    @Override
    public void activation() {
        mStage.getRunner().setBuddha(true, mWorkingTime, Constants.WORLD_STATIC_VELOCITY.x*2f);
        mStage.getRunner().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_GHOST);
        ScoreCounter.increaseBuddha();
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setBuddha(false);
        mStage.getRunner().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
    }

}
