package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import net.uglukfearless.monk.utils.gameplay.models.GameProgressModel;
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

    private Json mJsonIn;
    private FileHandle mFileLevel;

    public GameScreen(Game game, LevelModel levelModel) {

        mGame = game;

        AssetLoader.initGame();

        mLevelModel = levelModel;

        ScoreCounter.initDeadPoints();

        initGame(true, null);
    }

    private void initGame(boolean newGame, GameProgressModel progressModel) {

        if (!newGame) {
            AssetLoader.levelMusic.dispose();
            ScoreCounter.initDeadPoints();
        }

        if (!newGame) {
            mLevelModel.initAssets(true);
        }
        mLevelModel.init();
        AssetLoader.loadMonkAnimations(PreferencesManager.getArmour(), PreferencesManager.getWeapon());


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
        if (newGame) {
            ScoreCounter.resetStats();
            mGameStage = new GameStage(this, mYViewportHeight, mLevelModel, 0, 0);
        } else {
            mGameStage = new GameStage(this, mYViewportHeight, mLevelModel, progressModel.getRevival(), progressModel.getWingsRevival());

            AssetLoader.levelMusic.setVolume(SoundSystem.getMusicValue());
            SoundSystem.registrationMusic(AssetLoader.levelMusic);
            AssetLoader.levelMusic.setLooping(true);
            AssetLoader.levelMusic.play();
        }
        mGuiStage = new GameGuiStage(this, mGameStage, mYViewportHeight);

        mGameStage.setGuiStage(mGuiStage);

        mMultiplexer = new InputMultiplexer();
        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);

        Gdx.input.setInputProcessor(mMultiplexer);
    }

    @Override
    public void show() {
        if (AssetLoader.levelMusic!=null) {
            AssetLoader.levelMusic.setVolume(SoundSystem.getMusicValue());
            SoundSystem.registrationMusic(AssetLoader.levelMusic);
            AssetLoader.levelMusic.setLooping(true);
            AssetLoader.levelMusic.play();
        }
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
        mMultiplexer = new InputMultiplexer();
        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);
        Gdx.input.setInputProcessor(mMultiplexer);
        mGameStage.getRunner().setWeaponType(PreferencesManager.getWeapon());
        mGameStage.getRunner().armouring(PreferencesManager.getArmour());
    }

    public void newGame() {
        ScoreCounter.resetStats();
        mMultiplexer.removeProcessor(mGameStage);
        mGameStage.dispose();

        mLevelModel.getDifficultyHandler().applyStep(0);

        mGameStage = new GameStage(this, mYViewportHeight, mLevelModel, 0, 0);
        mGameStage.setGuiStage(mGuiStage);
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
        if (AssetLoader.levelMusic!=null) {
            AssetLoader.levelMusic.stop();
            SoundSystem.removeMusic(AssetLoader.levelMusic);
        }
        AssetLoader.disposeGame();
        if (mGameStage.getState() == GameState.RUN
                ||mGameStage.getState() == GameState.PAUSE
                ||mGameStage.getState() == GameState.FINISH
                ||mGameStage.getState() == GameState.SLOWDOWN
                ||(mGameStage.getState() == GameState.START&&ScoreCounter.getScore()!=0)) {

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
//        mGameStage.dispose();
//        mGuiStage.dispose();
        mShopStage = new ShopStage(this, mYViewportHeight);
        mMultiplexer.addProcessor(mShopStage);
    }

    public void loadNextLevel(GameProgressModel progress) {
        mJsonIn = new Json();
        try {
            mFileLevel = Gdx.files.internal("levels/level".concat(String.valueOf(progress.getCurrentGrade())).concat(".json"));
            mLevelModel = mJsonIn.fromJson(LevelModel.class, mFileLevel);
        } catch (Exception e) {
            mFileLevel = Gdx.files.internal("levels/level1.json");
            mLevelModel = mJsonIn.fromJson(LevelModel.class, mFileLevel);
        }

        initGame(false, progress);
        mMultiplexer.removeProcessor(mGameStage);
        mGameStage.dispose();

        mLevelModel.getDifficultyHandler().applyStep(0);

        mGameStage = new GameStage(this, mYViewportHeight, mLevelModel, progress.getRevival(), progress.getWingsRevival());
        mGameStage.setGuiStage(mGuiStage);
//        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);
        Gdx.input.setInputProcessor(mMultiplexer);
        mGuiStage.setGameStage(mGameStage);
    }
}
