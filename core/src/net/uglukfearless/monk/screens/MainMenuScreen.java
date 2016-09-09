package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.MainMenuStage;
import net.uglukfearless.monk.stages.OptionMenuStage;
import net.uglukfearless.monk.stages.StatisticsMenuStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.file.SoundSystem;

/**
 * Created by Ugluk on 30.06.2016.
 */
public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage;

    private float mYViewportHeight;

    public MainMenuScreen(Game game) {
        this.game = game;
        AssetLoader.initMenu();
        ScoreCounter.loadAchieve();
    }

    @Override
    public void show() {

        AssetLoader.menuMusic.setVolume(SoundSystem.getMusicValue());
        SoundSystem.registrationMusic(AssetLoader.menuMusic);
        AssetLoader.menuMusic.setLooping(true);
        AssetLoader.menuMusic.play();

        mYViewportHeight = Constants.APP_WIDTH / ((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight());

        stage = new MainMenuStage(this, mYViewportHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

    public void newGame() {
        this.dispose();
        stage.dispose();
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetLoader.menuMusic.stop();
        SoundSystem.removeMusic(AssetLoader.menuMusic);
        AssetLoader.disposeMenu();
    }

    public void mainMenu() {
        stage.dispose();
        stage = new MainMenuStage(this, mYViewportHeight);
    }

    public void optionMenu() {
        stage.dispose();
        stage = new OptionMenuStage(this, mYViewportHeight);
    }

    public void statisticsMenu() {
        stage.dispose();
        stage = new StatisticsMenuStage(this, mYViewportHeight);
    }
}
