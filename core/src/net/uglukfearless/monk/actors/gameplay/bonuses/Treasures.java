package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

/**
 * Created by Ugluk on 31.10.2016.
 */
public class Treasures extends GameBonus {

    public Treasures(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("rupee");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_TREASURES");
        mActiveTitle = "+1 ".concat(mName);
        mWorkingTime = 0.3f;
        mQuantum = true;
    }

    @Override
    public void activation() {
        PreferencesManager.addTreasures(1);
    }

    @Override
    public void deactivation() {

    }
}
