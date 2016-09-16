package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class AssetLoader {

    public static Texture backgroundTexture;
    public static TextureRegion background;
    public static Texture groundTexture;
    public static TextureRegion ground;
    public static Texture broadbordTexture;
    public static TextureRegion broadbord;

    public static TextureAtlas charactersAtlas;
    public static TextureAtlas enemiesAtlas;
    public static TextureAtlas monkAtlas;
    public static TextureAtlas guiAtlas;
    public static TextureAtlas achieveAtlas;
    public static TextureAtlas bonusesAtlas;
    public static TextureAtlas lumpsAtlas;

    public static Animation playerStay;
    public static Animation playerRun;
    public static TextureRegion [] playerFrames;
    public static Animation playerJump;
    public static Animation playerHit;
    public static Animation playerStrike;
    public static Texture playerShell;

    public static Texture stoneTexture;
    public static TextureRegion stone;
    public static Texture boxTexture;
    public static TextureRegion box;
    public static Texture columnTexture;
    public static TextureRegion column;
    public static TextureAtlas bladesAtlas;
    public static Animation blades;

    public static HashMap<String,TextureRegion> sAchieveRegions;

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

    public static Sound monkStrike;
    public static Sound getBonus;
    public static Sound balanceBonus;
    public static Sound retributionBonus;

    public static I18NBundle sBundle;

    public static void initBundle() {
        FileHandle baseFileHandle = Gdx.files.internal("i18n/Bundle");
        if (PreferencesManager.getLanguage()=="") {
            sBundle = I18NBundle.createBundle(baseFileHandle);
            PreferencesManager.setLanguage(Locale.getDefault().getLanguage());
        } else {
            sBundle = I18NBundle.createBundle(baseFileHandle, new Locale(PreferencesManager.getLanguage()));
        }
    }

    public static void changeLanguage() {
        FileHandle baseFileHandle = Gdx.files.internal("i18n/Bundle");
        sBundle = I18NBundle.createBundle(baseFileHandle, new Locale(PreferencesManager.getLanguage()));
    }

    public static void initGame() {

        backgroundTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        background = new TextureRegion(backgroundTexture);
        groundTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));
        ground = new TextureRegion(groundTexture);

        charactersAtlas = new TextureAtlas(Gdx.files.internal(Constants.CHARACTERS_ATLAS_PATH));
        enemiesAtlas = new TextureAtlas(Gdx.files.internal("new/enemies.atlas"));
        monkAtlas = new TextureAtlas(Gdx.files.internal("new/monk.atlas"));
        achieveAtlas = new TextureAtlas(Gdx.files.internal("achieve/achieve.atlas"));
        bonusesAtlas = new TextureAtlas(Gdx.files.internal("bonuses/bonuses.atlas"));
        lumpsAtlas = new TextureAtlas(Gdx.files.internal("lumps.atlas"));

        playerStay  = new Animation(0.12f ,monkAtlas.findRegion(Constants.RUNNER_RUNNING_REGION_NAMES[0]));
        playerFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
        for (int i=0;i< Constants.RUNNER_RUNNING_REGION_NAMES.length;i++) {
            playerFrames[i] = monkAtlas.findRegion(Constants.RUNNER_RUNNING_REGION_NAMES[i]);
        }
        playerRun = new Animation(0.12f, playerFrames);
        playerJump = new Animation(0.12f ,monkAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME));
        playerHit = new Animation(0.12f ,monkAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME));
        playerStrike = new Animation(0.12f ,monkAtlas.findRegion(Constants.RUNNER_STRIKING_REGION_NAME));
        playerShell = new Texture(Gdx.files.internal("monkShell.png"));

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

        monkStrike = Gdx.audio.newSound(Gdx.files.internal("sound/kiya.wav"));
        getBonus = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
        balanceBonus = Gdx.audio.newSound(Gdx.files.internal("sound/beep.wav"));
        retributionBonus = Gdx.audio.newSound(Gdx.files.internal("sound/beat.wav"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.25f, .25f);

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));

        broadbordTexture = new Texture(Gdx.files.internal("broadbord.png"));
        broadbord = new TextureRegion(broadbordTexture);

        sAchieveRegions = new HashMap<String, TextureRegion>(Constants.ACHIEVE_NAMES.length);
        for (int i=0; i<Constants.ACHIEVE_NAMES.length -1;i++) {
            sAchieveRegions.put(PreferencesConstants.ALL_ACHIEVE_KEYS[i]
                    , achieveAtlas.findRegion(Constants.ACHIEVE_NAMES[i]));
        }

    }

    //Инициализация ресурсов уровня
    public static void initLevel() {

        backgroundTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        background = new TextureRegion(backgroundTexture);
        groundTexture = new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH));
        ground = new TextureRegion(groundTexture);

        charactersAtlas = new TextureAtlas(Gdx.files.internal(Constants.CHARACTERS_ATLAS_PATH));
        monkAtlas = new TextureAtlas(Gdx.files.internal("new/monk.atlas"));

//        playerFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
//        for (int i=0;i< Constants.RUNNER_RUNNING_REGION_NAMES.length;i++) {
//            playerFrames[i] = monkAtlas.findRegion(Constants.RUNNER_RUNNING_REGION_NAMES[i]);
//        }
//        playerRun = new Animation(0.12f, playerFrames);
//        playerJump = monkAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
//        playerHit = monkAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);
//        playerStrike = monkAtlas.findRegion(Constants.RUNNER_STRIKING_REGION_NAME);

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
        levelOneMusic.setLooping(true);
        levelOneMusic.setVolume(SoundSystem.getMusicValue());

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.025f, .025f);

    }

    public static void initMenu() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainTheme.mp3"));
        menuMusic.setVolume(SoundSystem.getMusicValue());

        monkStrike = Gdx.audio.newSound(Gdx.files.internal("sound/kiya.wav"));

        menuBackgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        guiAtlas = new TextureAtlas(Gdx.files.internal("gui/gui.atlas"));
        achieveAtlas = new TextureAtlas(Gdx.files.internal("achieve/achieve.atlas"));

        buttonMenuOff = new TextureRegion(guiAtlas.findRegion("button_off"));
        buttonMenuOn = new TextureRegion(guiAtlas.findRegion("button_on"));
        buttonBackOn = new TextureRegion(guiAtlas.findRegion("back_on"));
        buttonBackOff = new TextureRegion(guiAtlas.findRegion("back_off"));
        broadbordTexture = new Texture(Gdx.files.internal("broadbord.png"));
        broadbord = new TextureRegion(broadbordTexture);

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.6f, .6f);
        fontBig = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontBig.getData().setScale(1f, 1f);

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));

        //**********************************************************
        //!!!!!!!!!!!! ВОТ ТУТ СОМНИТЕЛЬНЫЙ ХОД!!!!!!!!!!!
        sAchieveRegions = new HashMap<String, TextureRegion>(Constants.ACHIEVE_NAMES.length);
        for (int i=0; i<Constants.ACHIEVE_NAMES.length - 1;i++) {
            sAchieveRegions.put(PreferencesConstants.ALL_ACHIEVE_KEYS[i]
                    , achieveAtlas.findRegion(Constants.ACHIEVE_NAMES[i]));
        }
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
        monkStrike.dispose();
        menuBackgroundTexture.dispose();
        guiAtlas.dispose();
        sAchieveRegions.clear();
        achieveAtlas.dispose();
        font.dispose();
        fontBig.dispose();
        sGuiSkin.dispose();
        broadbordTexture.dispose();
        sGuiSkin.dispose();
    }

    public static void disposeGame() {
        charactersAtlas.dispose();
        monkAtlas.dispose();
        enemiesAtlas.dispose();
        sAchieveRegions.clear();
        achieveAtlas.dispose();
        bonusesAtlas.dispose();
        lumpsAtlas.dispose();
        stoneTexture.dispose();
        boxTexture.dispose();
        columnTexture.dispose();
        bladesAtlas.dispose();
        backgroundTexture.dispose();
        broadbordTexture.dispose();
        groundTexture.dispose();
        levelOneMusic.dispose();
        monkStrike.dispose();
        getBonus.dispose();
        balanceBonus.dispose();
        retributionBonus.dispose();
        font.dispose();
    }
}
