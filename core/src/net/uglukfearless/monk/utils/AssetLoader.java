package net.uglukfearless.monk.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class AssetLoader {

    public static Texture backgroundTexture;
    public static TextureRegion background;
    public static Texture groundTexture;
    public static TextureRegion ground;

    public static TextureAtlas charactersAtlas;
    public static TextureAtlas enemiesAtlas;
    public static TextureAtlas monkAtlas;
    public static TextureAtlas guiAtlas;

    public static Animation playerRun;
    public static TextureRegion [] playerFrames;
    public static TextureRegion playerJump;
    public static TextureRegion playerHit;
    public static TextureRegion playerStrike;

    public static Texture stoneTexture;
    public static TextureRegion stone;
    public static Texture boxTexture;
    public static TextureRegion box;
    public static Texture columnTexture;
    public static TextureRegion column;
    public static TextureAtlas bladesAtlas;
    public static Animation blades;

    public static TextureRegion buttonMenuOn;
    public static TextureRegion buttonMenuOff;
    public static TextureRegion buttonBackOn;
    public static TextureRegion buttonBackOff;

    public static Music levelOneMusic;

    public static BitmapFont font;
    public static BitmapFont fontBig;

    public static Skin sGuiSkin;

    public static Music menuMusic;
    public static Texture menuBackgroundTexture;

    public static Texture logoPicture;
    public static Sound logoSound;

    public static void init() {

        backgroundTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        background = new TextureRegion(backgroundTexture);
        groundTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));
        ground = new TextureRegion(groundTexture);

        charactersAtlas = new TextureAtlas(Gdx.files.internal(Constants.CHARACTERS_ATLAS_PATH));
        enemiesAtlas = new TextureAtlas(Gdx.files.internal("new/enemies.atlas"));
        monkAtlas = new TextureAtlas(Gdx.files.internal("new/monk.atlas"));

        playerFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for (int i=0;i< Constants.RUNNER_RUNNING_REGION_NAMES.length;i++) {
            playerFrames[i] = monkAtlas.findRegion(Constants.RUNNER_RUNNING_REGION_NAMES[i]);
        }
        playerRun = new Animation(0.12f, playerFrames);
        playerJump = monkAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
        playerHit = monkAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);
        playerStrike = monkAtlas.findRegion(Constants.RUNNER_STRIKING_REGION_NAME);

        stoneTexture = new Texture(Gdx.files.internal("stone.png"));
        stone = new TextureRegion(stoneTexture);
        boxTexture = new Texture(Gdx.files.internal("box.png"));
        box = new TextureRegion(boxTexture);
        columnTexture = new Texture(Gdx.files.internal("column.png"));
        column = new TextureRegion(columnTexture);
        bladesAtlas = new TextureAtlas(Gdx.files.internal("blades.atlas"));

        String [] textureRegions = new String [] { "blade1", "blade2", "blade3", "blade4" };

        TextureRegion[] frames = new TextureRegion[textureRegions.length];
        for (int i=0;i<textureRegions.length;i++) {
            frames[i] = bladesAtlas.findRegion(textureRegions[i]);
        }
        blades = new Animation(0.025f, frames);

        levelOneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/level1.mp3"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.025f, .025f);

    }

    //Инициализация ресурсов уровня
    public static void initLevel() {

        backgroundTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        background = new TextureRegion(backgroundTexture);
        groundTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));
        ground = new TextureRegion(groundTexture);

        charactersAtlas = new TextureAtlas(Gdx.files.internal(Constants.CHARACTERS_ATLAS_PATH));
        monkAtlas = new TextureAtlas(Gdx.files.internal("new/monk.atlas"));

        playerFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for (int i=0;i< Constants.RUNNER_RUNNING_REGION_NAMES.length;i++) {
            playerFrames[i] = monkAtlas.findRegion(Constants.RUNNER_RUNNING_REGION_NAMES[i]);
        }
        playerRun = new Animation(0.12f, playerFrames);
        playerJump = monkAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
        playerHit = monkAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);
        playerStrike = monkAtlas.findRegion(Constants.RUNNER_STRIKING_REGION_NAME);

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

        levelOneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/level1.mp3"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.025f, .025f);

    }

    public static void initMenu() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainTheme.mp3"));
        menuBackgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        guiAtlas = new TextureAtlas(Gdx.files.internal("gui/gui.atlas"));
        buttonMenuOff = new TextureRegion(guiAtlas.findRegion("button_off"));
        buttonMenuOn = new TextureRegion(guiAtlas.findRegion("button_on"));
        buttonBackOn = new TextureRegion(guiAtlas.findRegion("back_on"));
        buttonBackOff = new TextureRegion(guiAtlas.findRegion("back_off"));
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.6f, .6f);
        fontBig = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontBig.getData().setScale(1f, 1f);

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));
    }

    public static void initLogo() {
        logoPicture = new Texture(Gdx.files.internal("Logo.png"));
        logoSound = Gdx.audio.newSound(Gdx.files.internal("sound/Thunder.mp3"));
    }

    public static void disposeLogo() {
        logoPicture.dispose();
        logoSound.dispose();
    }

    public static void disposeMenu() {
        menuMusic.dispose();
        menuBackgroundTexture.dispose();
        guiAtlas.dispose();
        font.dispose();
        fontBig.dispose();
        sGuiSkin.dispose();
    }

    public static void dispose() {
        charactersAtlas.dispose();
        monkAtlas.dispose();
        enemiesAtlas.dispose();
        stoneTexture.dispose();
        boxTexture.dispose();
        columnTexture.dispose();
        bladesAtlas.dispose();
        backgroundTexture.dispose();
        groundTexture.dispose();
        levelOneMusic.dispose();
        font.dispose();
    }
}
