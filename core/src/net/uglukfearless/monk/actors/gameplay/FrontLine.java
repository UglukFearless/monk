package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.Movable;

/**
 * Created by Ugluk on 05.10.2017.
 */

public class FrontLine extends Actor implements Movable {

    private float speed = 0;
    private float mSpeedCof = 2f;

    private Sprite mSpriteUp1;
    private Sprite mSpriteUp2;
    private Sprite mSpriteDown1;
    private Sprite mSpriteDown2;

    private float offsetUp = 0.3f;
    private float offsetDown = -1.3f;

    private float width = 25f;
    private float height = 8f;

    private float mUpY;

    private static float VIEWPORT_HEIGHT;

    private Array<Sprite> mUpRow;
    private Array<Sprite> mDownRow;

    private Sprite mBufferSprite;

    public FrontLine(float viewport_height) {

        VIEWPORT_HEIGHT = viewport_height;
        mUpY = VIEWPORT_HEIGHT - height + offsetUp;

        TextureRegion textureRegion;

        mUpRow = new Array<Sprite>();
        mDownRow = new Array<Sprite>();

        mSpriteUp1 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontup", 1));
        mSpriteUp1.setSize(width, height);
        mSpriteUp1.setPosition(0, mUpY);
        mUpRow.add(mSpriteUp1);

        textureRegion = AssetLoader.environmentAtlas.findRegion("frontup", 2);
        if (textureRegion!=null) {

            mSpriteUp2 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontup", 2));
            mSpriteUp2.setSize(width, height);
            mSpriteUp2.setPosition(width, mUpY);
            mUpRow.add(mSpriteUp2);

            int i = 3;
            while (AssetLoader.environmentAtlas.findRegion("frontup", i)!=null) {
                mBufferSprite = new Sprite(AssetLoader.environmentAtlas.findRegion("frontup", i));
                mBufferSprite.setSize(width, height);
                mBufferSprite.setPosition(width*(i-1), mUpY);
                mUpRow.add(mBufferSprite);

                i++;
            }

        } else {
            mSpriteUp2 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontup", 1));
            mSpriteUp2.setSize(width, height);
            mSpriteUp2.setPosition(width, mUpY);
            mUpRow.add(mSpriteUp2);
        }


        mSpriteDown1 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontdown", 1));
        mSpriteDown1.setSize(width, height);
        mSpriteDown1.setPosition(0, offsetDown);
        mDownRow.add(mSpriteDown1);

        textureRegion = AssetLoader.environmentAtlas.findRegion("frontdown", 2);
        if (textureRegion!=null) {
            mSpriteDown2 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontdown", 2));
            mSpriteDown2.setSize(width, height);
            mSpriteDown2.setPosition(width, offsetDown);
            mDownRow.add(mSpriteDown2);

            int i = 3;
            while (AssetLoader.environmentAtlas.findRegion("frontdown", i)!=null) {
                mBufferSprite = new Sprite(AssetLoader.environmentAtlas.findRegion("frontdown", i));
                mBufferSprite.setSize(width, height);
                mBufferSprite.setPosition(width*(i-1), offsetDown);
                mDownRow.add(mBufferSprite);

                i++;
            }
        } else {
            mSpriteDown2 = new Sprite(AssetLoader.environmentAtlas.findRegion("frontdown", 1));
            mSpriteDown2.setSize(width, height);
            mSpriteDown2.setPosition(width, offsetDown);
            mDownRow.add(mSpriteDown2);
        }


        System.out.println(mUpRow.size);
        System.out.println(mDownRow.size);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!inBounds(mUpRow.get(0))) {
            mBufferSprite = mUpRow.get(0);
            mUpRow.removeIndex(0);
            mUpRow.add(mBufferSprite);
        }

        mUpRow.get(0).setX(mUpRow.get(0).getX() + speed*delta);

        for (int i = 1; i < mUpRow.size; i++) {
            mUpRow.get(i).setX(mUpRow.get(i - 1).getX() + mUpRow.get(i - 1).getWidth());
        }

        if (!inBounds(mDownRow.get(0))) {
            mBufferSprite = mDownRow.get(0);
            mDownRow.removeIndex(0);
            mDownRow.add(mBufferSprite);
        }

        mDownRow.get(0).setX(mDownRow.get(0).getX() + speed*delta);

        for (int i = 1; i < mDownRow.size; i++) {
            mDownRow.get(i).setX(mDownRow.get(i - 1).getX() + mDownRow.get(i - 1).getWidth());
        }

    }

    private boolean inBounds(Sprite sprite) {
        return ((sprite.getX() + sprite.getWidth()) > 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (Sprite sprite: mUpRow) {
            sprite.draw(batch);
        }

        for (Sprite sprite: mDownRow) {
            sprite.draw(batch);
        }

    }

    @Override
    public void changingStaticSpeed(float speedScale) {
        speed = speedScale*mSpeedCof;
    }
}
