package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 10.09.2016.
 */
public class WingsBonus extends GameBonus {

    public WingsBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion(Constants.BONUS_WINGS_REGION);
        mName = AssetLoader.sBundle.get("PLAY_BONUS_WINGS");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 15;
    }

    @Override
    public void activation() {
        mStage.getRunner().setWings(true);
        mStage.getRunner().getBody().setGravityScale(4.5f);
        ScoreCounter.increaseWings();
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setWings(false);
        mStage.getRunner().getBody().setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
//        mStage.getRunner().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
        mStage.getRunner().setCustomFilter();
    }
}
