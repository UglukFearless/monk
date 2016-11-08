package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Json;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameGuiStage;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.stages.ShopStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;


/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameScreen implements Screen {

    private Game mGame;
    private LevelModel mLevelModel;
    private float mYViewportHeight;
    private GameStage mGameStage;
    private GameGuiStage mGuiStage;
    private ShopStage mShopStage;

    InputMultiplexer mMultiplexer;

    public GameScreen(Game game, LevelModel levelModel) {

        mGame = game;

        AssetLoader.initGame();

        mLevelModel = levelModel;

        initGame();
    }

    private void initGame() {

        mLevelModel.init();
        AssetLoader.loadMonkAnimations(PreferencesManager.getArmour());


        for (int i=0;i<10;i++) {
            if (!mLevelModel.getEnemiesModels()[i].block) {
                EnemyType.values()[i].init(mLevelModel.getEnemiesModels()[i]);
            } else {
                EnemyType.values()[i].setBlock(true);
            }
        }

        for (int i=0;i<10;i++) {
            if (!mLevelModel.getObstaclesModels()[i].block) {
                ObstacleType.values()[i].init(mLevelModel.getObstaclesModels()[i]);
            } else {
                ObstacleType.values()[i].setBlock(true);
            }
        }

        mYViewportHeight = Constants.GAME_WIDTH / ((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
        ScoreCounter.resetStats();

        mGameStage = new GameStage(this, mYViewportHeight, mLevelModel);
        mGuiStage = new GameGuiStage(this, mGameStage, mYViewportHeight);

        mGameStage.setGuiStage(mGuiStage);

        mMultiplexer = new InputMultiplexer();
        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);

        Gdx.input.setInputProcessor(mMultiplexer);
    }

    @Override
    public void show() {
        AssetLoader.levelMusic.setVolume(SoundSystem.getMusicValue());
        SoundSystem.registrationMusic(AssetLoader.levelMusic);
        AssetLoader.levelMusic.setLooping(true);
        AssetLoader.levelMusic.play();
        System.out.print("GameScreenShow");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        mGameStage.getViewport().apply();
        mGameStage.draw();
        mGameStage.act(delta);

//        mGuiStage.getViewport().apply();
        mGuiStage.draw();
        mGuiStage.act(delta);

        if (mShopStage!=null) {
            mShopStage.draw();
            mShopStage.act(delta);
        }

    }

    public void returnGame() {
        mShopStage.dispose();
        initGame();
    }

    public void newGame() {
        ScoreCounter.resetStats();
        mMultiplexer.removeProcessor(mGameStage);
        mGameStage.dispose();

        mLevelModel.getDifficultyHandler().applyStep(0);

        mGameStage = new GameStage(this, mYViewportHeight, mLevelModel);
        mGameStage.setGuiStage(mGuiStage);
        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);
        Gdx.input.setInputProcessor(mMultiplexer);
        mGuiStage.setGameStage(mGameStage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (mGameStage.getState()==GameState.RUN) mGameStage.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        System.out.print("GameScreenHide!");
    }

    @Override
    public void dispose() {
        AssetLoader.levelMusic.stop();
        SoundSystem.removeMusic(AssetLoader.levelMusic);
        AssetLoader.disposeGame();
        if (mGameStage.getState()== GameState.RUN) {
            mGameStage.saveTimePoint();
            ScoreCounter.saveCalcStats(mGameStage.getLevelModel().getLEVEL_NAME());

            for (Achievement achievement: ScoreCounter.getAchieveList()) {
                achievement.checkAchieve();
            }

            ScoreCounter.resetStats();
        }
        System.out.print("GameScreenDispose");
    }

    public void setMenu() {
        this.dispose();
        mGame.setScreen(new MainMenuScreen(mGame));
    }

    public void setShop() {
        mMultiplexer.removeProcessor(mGameStage);
        mMultiplexer.removeProcessor(mGuiStage);
        mGameStage.dispose();
        mGuiStage.dispose();
        mShopStage = new ShopStage(this, mYViewportHeight);
        mMultiplexer.addProcessor(mShopStage);
    }
}
