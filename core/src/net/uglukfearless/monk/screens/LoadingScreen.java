package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;

/**
 * Created by Ugluk on 28.04.2017.
 */

public class LoadingScreen implements Screen {

    private Game mGame;
    private LevelModel mLevelModel;

    private SpriteBatch batch;

    public LoadingScreen(Game game, LevelModel levelModel) {
        mGame = game;
        mLevelModel = levelModel;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        AssetLoader.initLoaderAssets();

        AssetLoader.initGameManager();

        mLevelModel.initAssets(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(AssetLoader.sLoadingImage, 10, Gdx.graphics.getHeight() / 2 - AssetLoader.sLoadingImage.getHeight() / 2,
                Gdx.graphics.getWidth() - 20, AssetLoader.sLoadingImage.getHeight());
        batch.end();

        if (AssetLoader.sAssetManager.update()) {
            this.dispose();
            mGame.setScreen(new GameScreen(mGame, mLevelModel));
        }
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
        batch.dispose();
    }
}
