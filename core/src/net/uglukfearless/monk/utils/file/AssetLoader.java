package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import net.uglukfearless.monk.constants.Constants;

import java.util.Locale;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class AssetLoader {

    public static Texture broadbordTexture;
    public static TextureRegion broadbord;

    public static TextureAtlas environmentAtlas;
    public static TextureAtlas enemiesAtlas;
    public static TextureAtlas obstaclesAtlas;
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

    public static TextureRegion buttonMenuOn;
    public static TextureRegion buttonMenuOff;
    public static TextureRegion buttonBackOn;
    public static TextureRegion buttonBackOff;

    public static Music levelMusic;

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

//        environmentAtlas = new TextureAtlas(Gdx.files.internal("new/environment.atlas"));
//        enemiesAtlas = new TextureAtlas(Gdx.files.internal("new/enemies.atlas"));
//        obstaclesAtlas = new TextureAtlas(Gdx.files.internal("new/obstacles.atlas"));
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


        monkStrike = Gdx.audio.newSound(Gdx.files.internal("sound/kiya.wav"));
        getBonus = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
        balanceBonus = Gdx.audio.newSound(Gdx.files.internal("sound/beep.wav"));
        retributionBonus = Gdx.audio.newSound(Gdx.files.internal("sound/beat.wav"));

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));

        broadbordTexture = new Texture(Gdx.files.internal("broadbord.png"));
        broadbord = new TextureRegion(broadbordTexture);

    }

    //Инициализация ресурсов уровня
    public static void initLevel(String levelName) {

        environmentAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/environment.atlas"));
        enemiesAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/enemies.atlas"));
        obstaclesAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/obstacles.atlas"));

        levelMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + levelName + "/music.mp3"));
        levelMusic.setLooping(true);
        levelMusic.setVolume(SoundSystem.getMusicValue());

    }

    public static void initMenu() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainTheme.mp3"));
        menuMusic.setVolume(SoundSystem.getMusicValue());

        monkStrike = Gdx.audio.newSound(Gdx.files.internal("sound/kiya.wav"));

        menuBackgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        guiAtlas = new TextureAtlas(Gdx.files.internal("gui/gui.atlas"));
        achieveAtlas = new TextureAtlas(Gdx.files.internal("achieve/achieve.atlas"));
        bonusesAtlas = new TextureAtlas(Gdx.files.internal("bonuses/bonuses.atlas"));

        buttonMenuOff = new TextureRegion(guiAtlas.findRegion("button_off"));
        buttonMenuOn = new TextureRegion(guiAtlas.findRegion("button_on"));
        buttonBackOn = new TextureRegion(guiAtlas.findRegion("back_on"));
        buttonBackOff = new TextureRegion(guiAtlas.findRegion("back_off"));
        broadbordTexture = new Texture(Gdx.files.internal("broadbord.png"));
        broadbord = new TextureRegion(broadbordTexture);

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
        monkStrike.dispose();
        menuBackgroundTexture.dispose();
        guiAtlas.dispose();
        achieveAtlas.dispose();
        bonusesAtlas.dispose();
        sGuiSkin.dispose();
        broadbordTexture.dispose();
        sGuiSkin.dispose();
    }

    public static void disposeGame() {
        monkAtlas.dispose();

        achieveAtlas.dispose();
        bonusesAtlas.dispose();
        lumpsAtlas.dispose();
        broadbordTexture.dispose();

        monkStrike.dispose();
        getBonus.dispose();
        balanceBonus.dispose();
        retributionBonus.dispose();

        disposeLevel();
    }

    public static void disposeLevel() {
        enemiesAtlas.dispose();
        obstaclesAtlas.dispose();
        environmentAtlas.dispose();

        levelMusic.dispose();
    }
}
