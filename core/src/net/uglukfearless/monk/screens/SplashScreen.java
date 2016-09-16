package net.uglukfearless.monk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.uglukfearless.monk.RunningMonk;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.fortween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Ugluk on 01.07.2016.
 */
public class SplashScreen implements Screen {

    private TweenManager mTweenManager;
    private SpriteBatch mBatch;
    private Sprite mSprite;
    private RunningMonk mGame;
    public SplashScreen mSplashScreen;

    public SplashScreen(RunningMonk game) {
        this.mGame = game;
        AssetLoader.initBundle();
        AssetLoader.initLogo();
        mSplashScreen = this;
    }


    @Override
    public void show() {
        AssetLoader.logoSound.play(SoundSystem.getSoundValue());

        mSprite = new Sprite(AssetLoader.logoPicture);
        mSprite.setColor(1,1,1,0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float desireWidth = width*0.4f;
        float scale = desireWidth/mSprite.getWidth();

        mSprite.setSize(mSprite.getWidth() * scale, mSprite.getHeight() * scale);
        mSprite.setPosition((width / 2) - (mSprite.getWidth() / 2f), (height / 2) - (mSprite.getHeight() / 2));

        setupTween();
        mBatch = new SpriteBatch();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        mTweenManager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                mSplashScreen.dispose();
                mGame.setScreen(new MainMenuScreen(mGame));
            }
        };

        Tween.to(mSprite, SpriteAccessor.ALPHA, 0.8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f).setCallback(callback)
                .setCallbackTriggers(TweenCallback.COMPLETE).start(mTweenManager);
    }

    @Override
    public void render(float delta) {
        mTweenManager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mSprite.draw(mBatch);
        mBatch.end();
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
        AssetLoader.logoSound.stop();
        AssetLoader.disposeLogo();
    }
}
