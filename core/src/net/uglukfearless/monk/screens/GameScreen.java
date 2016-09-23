package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.stages.GameGuiStage;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameScreen implements Screen {

    private GameStage mGameStage;
    private float mYViewportHeight;
    private Game mGame;
    private GameGuiStage mGuiStage;

    InputMultiplexer mMultiplexer;

    public GameScreen(Game game) {
        AssetLoader.initGame();
        mYViewportHeight = Constants.GAME_WIDTH / ((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
        ScoreCounter.resetStats();

        mGameStage = new GameStage(this, mYViewportHeight);
        mGame = game;
        mGuiStage = new GameGuiStage(this, mGameStage, mYViewportHeight);

        mGameStage.setGuiStage(mGuiStage);

        mMultiplexer = new InputMultiplexer();
        mMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("click regist");
                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
        mMultiplexer.addProcessor(mGuiStage);
        mMultiplexer.addProcessor(mGameStage);

        Gdx.input.setInputProcessor(mMultiplexer);
    }

    @Override
    public void show() {
        AssetLoader.levelOneMusic.setVolume(SoundSystem.getMusicValue());
        SoundSystem.registrationMusic(AssetLoader.levelOneMusic);
        AssetLoader.levelOneMusic.setLooping(true);
        AssetLoader.levelOneMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mGameStage.getViewport().apply();
        mGameStage.draw();
        mGameStage.act(delta);

        mGuiStage.getViewport().apply();
        mGuiStage.draw();
        mGuiStage.act(delta);

    }

    public void newGame() {
        ScoreCounter.resetStats();
        mMultiplexer.removeProcessor(mGameStage);
        mGameStage.dispose();
        mGameStage = new GameStage(this, mYViewportHeight);
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

    }

    @Override
    public void dispose() {
        AssetLoader.levelOneMusic.stop();
        SoundSystem.removeMusic(AssetLoader.levelOneMusic);
        AssetLoader.disposeGame();
        if (((GameStage) mGameStage).getState()== GameState.RUN) {
            mGameStage.saveTimePoint();
            ScoreCounter.saveCalcStats();

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
}
