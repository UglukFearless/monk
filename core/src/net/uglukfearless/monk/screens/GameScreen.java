package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.utils.ScoreCounter;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameScreen implements Screen{

    private GameStage stage;

    public GameScreen() {
        AssetLoader.init();
        stage = new GameStage(this);
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
        stage = new GameStage(this);
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
        AssetLoader.dispose();
    }
}
