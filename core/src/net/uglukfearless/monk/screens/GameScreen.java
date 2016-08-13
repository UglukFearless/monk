package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.utils.ScoreCounter;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameScreen implements Screen{

    private Stage stage;
    private float mYViewportHeight;

    public GameScreen() {
        AssetLoader.init();
        mYViewportHeight = Constants.GAME_WIDTH / ((float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
        stage = new GameStage(this, mYViewportHeight);
        AssetLoader.levelOneMusic.setLooping(true);
        AssetLoader.levelOneMusic.setVolume(0.1f);
        AssetLoader.levelOneMusic.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);

    }

    public void newGame() {
        ScoreCounter.resetScore();
        stage.dispose();
        stage = new GameStage(this, mYViewportHeight);
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
        AssetLoader.levelOneMusic.stop();
        AssetLoader.dispose();
        System.out.print("GameScreenDispose");
    }
}
