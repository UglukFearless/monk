package net.uglukfearless.monk.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class AssetLoader {

    public static Texture backgroundTexture;
    public static TextureRegion background;
    public static Texture groundTexture;
    public static TextureRegion ground;

    public static TextureAtlas charactersAtlas;
    public static Animation playerRun;
    public static TextureRegion [] playerFrames;
    public static TextureRegion playerJump;
    public static TextureRegion playerHit;

    public static Texture stoneTexture;
    public static TextureRegion stone;
    public static Texture boxTexture;
    public static TextureRegion box;
    public static TextureAtlas bladesAtlas;
    public static Animation blades;

    public static BitmapFont font;

    public static void init() {

        backgroundTexture = new Texture(Gdx.files.internal(net.uglukfearless.monk.constants.Constants.BACKGROUND_IMAGE_PATH));
        background = new TextureRegion(backgroundTexture);
        groundTexture = new Texture(Gdx.files.internal(net.uglukfearless.monk.constants.Constants.GROUND_IMAGE_PATH));
        ground = new TextureRegion(groundTexture);

        charactersAtlas = new TextureAtlas(Gdx.files.internal(net.uglukfearless.monk.constants.Constants.CHARACTERS_ATLAS_PATH));

        playerFrames = new TextureRegion[net.uglukfearless.monk.constants.Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for (int i=0;i< net.uglukfearless.monk.constants.Constants.RUNNER_RUNNING_REGION_NAMES.length;i++) {
            playerFrames[i] = charactersAtlas.findRegion(net.uglukfearless.monk.constants.Constants.RUNNER_RUNNING_REGION_NAMES[i]);
        }
        playerRun = new Animation(0.1f, playerFrames);
        playerJump = charactersAtlas.findRegion(net.uglukfearless.monk.constants.Constants.RUNNER_JUMPING_REGION_NAME);
        playerHit = charactersAtlas.findRegion(net.uglukfearless.monk.constants.Constants.RUNNER_HIT_REGION_NAME);

        stoneTexture = new Texture(Gdx.files.internal("stone.png"));
        stone = new TextureRegion(stoneTexture);
        boxTexture = new Texture(Gdx.files.internal("box.png"));
        box = new TextureRegion(boxTexture);
        bladesAtlas = new TextureAtlas(Gdx.files.internal("blades.atlas"));

        String [] textureRegions = new String [] { "blade1", "blade2", "blade3", "blade4" };

        TextureRegion[] frames = new TextureRegion[textureRegions.length];
        for (int i=0;i<textureRegions.length;i++) {
            frames[i] = bladesAtlas.findRegion(textureRegions[i]);
        }
        blades = new Animation(0.025f, frames);

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.025f, .025f);

    }

    public static void dispose() {
        charactersAtlas.dispose();
        stoneTexture.dispose();
        boxTexture.dispose();
        bladesAtlas.dispose();
        backgroundTexture.dispose();
        groundTexture.dispose();
        font.dispose();
    }

}
