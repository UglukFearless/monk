package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.enums.WeaponType;
import net.uglukfearless.monk.utils.gameplay.CircularList;

import java.util.Locale;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class AssetLoader {

    public static NinePatch broadbord;

    public static TextureAtlas environmentAtlas;
    public static TextureAtlas enemiesAtlas;
    public static TextureAtlas obstaclesAtlas;
    public static TextureAtlas monkAtlas;
    public static TextureAtlas monkArmour1Atlas;
    public static TextureAtlas monkArmour2Atlas;
    public static TextureAtlas monkArmour3Atlas;
    public static TextureAtlas achieveAtlas;
    public static TextureAtlas bonusesAtlas;
    public static TextureAtlas lumpsAtlas;
    public static TextureAtlas itemsAtlas;
    public static TextureAtlas dragonAtlas;

    public static Texture playerShell;
    public static Texture levelLock;

    public static Texture finishDecoration;

    public static Animation playerStay;
    public static Animation playerRun;
    public static Animation playerJump;
    public static Animation playerHit;

    public static CircularList<Animation> playerStrikeList;

    public static Music levelMusic;

    public static Skin sGuiSkin;

    public static Music menuMusic;
    public static Texture menuBackgroundTexture1;
    public static Texture menuBackgroundTexture2;

    public static Texture logoPicture;
    public static Music logoSound;

//    public static Sound monkStrikeSound1;
    public static Sound monkStrikeSound2;
    public static Sound monkStrikeSound3;
    public static Sound monkStrikeSound4;
    public static Sound monkStrikeSound5;

    public static Array<Sound> monkStrikeSounds;
    public static Array<Sound> monkWeaponStrikeSounds;
    public static Array<Sound> monkArmourHitSounds;

    public static Array<Sound> enemyDeathSounds;

    public static Sound getBonusSound;
    public static Sound balanceBonusSound;
    public static Sound retributionBonusSound;
    public static Sound deathSound;
    public static Sound menuTestValueSound;

    public static I18NBundle sBundle;

    //exp
    public static Array<ParticleEffect> sFreeParticleBlood;
    public static Array<ParticleEffect> sWorkParticleBlood;

    public static Array<ParticleEffect> sFreeParticleDust;
    public static Array<ParticleEffect> sWorkParticleDust;

    public static ParticleEffect sHitParticle;

    public static ParticleEffect sSnowParticle;
    public static ParticleEffect sSnowParticleBack;
    public static ParticleEffect sCandleParticle;

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

        monkAtlas = new TextureAtlas(Gdx.files.internal("textures/monk_textures/monk.atlas"));
        monkArmour1Atlas = new TextureAtlas(Gdx.files.internal("textures/monk_textures/armour/monk_armour1.atlas"));
        monkArmour2Atlas = new TextureAtlas(Gdx.files.internal("textures/monk_textures/armour/monk_armour2.atlas"));
        monkArmour3Atlas = new TextureAtlas(Gdx.files.internal("textures/monk_textures/armour/monk_armour3.atlas"));
        achieveAtlas = new TextureAtlas(Gdx.files.internal("achieve/achieve.atlas"));
        bonusesAtlas = new TextureAtlas(Gdx.files.internal("bonuses/bonuses.atlas"));
        lumpsAtlas = new TextureAtlas(Gdx.files.internal("lumps.atlas"));
        dragonAtlas = new TextureAtlas(Gdx.files.internal("textures/dragon.atlas"));

        itemsAtlas = new TextureAtlas(Gdx.files.internal("textures/items.atlas"));

        playerShell = new Texture(Gdx.files.internal("monkShell.png"));
        menuBackgroundTexture1 = new Texture(Gdx.files.internal("textures/menu/menu_background1.png"));
        menuBackgroundTexture2 = new Texture(Gdx.files.internal("textures/menu/menu_background2.png"));

        finishDecoration = new Texture(Gdx.files.internal("textures/decoration/besedka.png"));

        loadMonkAnimations(PreferencesManager.getArmour(), PreferencesManager.getWeapon());

        monkStrikeSounds = new Array<Sound>();
//        monkStrikeSound1 = Gdx.audio.newSound(Gdx.files.internal("sound/kiya1.wav"));
        monkStrikeSound2 = Gdx.audio.newSound(Gdx.files.internal("sound/kiya2.wav"));
        monkStrikeSound3 = Gdx.audio.newSound(Gdx.files.internal("sound/kiya3.wav"));
        monkStrikeSound4 = Gdx.audio.newSound(Gdx.files.internal("sound/kiya4.wav"));
        monkStrikeSound5 = Gdx.audio.newSound(Gdx.files.internal("sound/kiya5.wav"));
//        monkStrikeSounds.add(monkStrikeSound1);
        monkStrikeSounds.add(monkStrikeSound2);
        monkStrikeSounds.add(monkStrikeSound3);
        monkStrikeSounds.add(monkStrikeSound4);
        monkStrikeSounds.add(monkStrikeSound5);

        monkWeaponStrikeSounds = new Array<Sound>();

        Sound weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/weapon1.mp3"));
        monkWeaponStrikeSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/weapon2.mp3"));
        monkWeaponStrikeSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/weapon3.mp3"));
        monkWeaponStrikeSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/weapon4.mp3"));
        monkWeaponStrikeSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/weapon5.mp3"));
        monkWeaponStrikeSounds.add(weaponStrikeSound);

        monkArmourHitSounds = new Array<Sound>();
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/armour1.mp3"));
        monkArmourHitSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/monksounds/armour2.mp3"));
        monkArmourHitSounds.add(weaponStrikeSound);

        enemyDeathSounds = new Array<Sound>();
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/enemy/2.wav"));
        enemyDeathSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/enemy/3.wav"));
        enemyDeathSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/enemy/4.wav"));
        enemyDeathSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/enemy/5.wav"));
        enemyDeathSounds.add(weaponStrikeSound);
        weaponStrikeSound = Gdx.audio.newSound(Gdx.files.internal("sound/enemy/6.wav"));
        enemyDeathSounds.add(weaponStrikeSound);

        getBonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
        balanceBonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/beep.wav"));
        retributionBonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/beat.wav"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sound/death.mp3"));

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));

        broadbord = new NinePatch(sGuiSkin.getAtlas().createPatch("broadbord"));

        //exp
        sFreeParticleBlood = new Array<ParticleEffect>();

        for (int i=0;i<6;i++) {
            ParticleEffect effect = new ParticleEffect();
            effect.load(Gdx.files.internal("particles/blood2v3.p"), Gdx.files.internal("particles"));
            sFreeParticleBlood.add(effect);
        }

        sWorkParticleBlood = new Array<ParticleEffect>();

        sFreeParticleDust = new Array<ParticleEffect>();

        for (int i=0;i<6;i++) {
            ParticleEffect effect = new ParticleEffect();
            effect.load(Gdx.files.internal("particles/dust2.p"), Gdx.files.internal("particles"));
            sFreeParticleDust.add(effect);
        }

        sWorkParticleDust = new Array<ParticleEffect>();

        sHitParticle = new ParticleEffect();
        sHitParticle.load(Gdx.files.internal("particles/hit.p"), Gdx.files.internal("particles"));

        sSnowParticle = new ParticleEffect();
        sSnowParticle.load(Gdx.files.internal("particles/snow.p"), Gdx.files.internal("particles"));

        sSnowParticleBack = new ParticleEffect();
        sSnowParticleBack.load(Gdx.files.internal("particles/snow_back.p"), Gdx.files.internal("particles"));

        ArmourType.init();
        WeaponType.init();
    }

    //загрузка анимаций монаха по типу брони и оружия
    public static void loadMonkAnimations(ArmourType armourType, WeaponType weaponType) {

        TextureAtlas currentMonkAtlas = null;

        if (armourType!=null) {
            switch (armourType) {
                case ARMOUR_TYPE1:
                    currentMonkAtlas = monkArmour1Atlas;
                    break;
                case ARMOUR_TYPE2:
                    currentMonkAtlas = monkArmour2Atlas;
                    break;
                case ARMOUR_TYPE3:
                    currentMonkAtlas = monkArmour3Atlas;
                    break;
            }
        } else {
            currentMonkAtlas = monkAtlas;
        }

        loadMonkAnimations(currentMonkAtlas, weaponType);

    }
    //загрузка анимаций монаха по атласу
    private static void loadMonkAnimations(TextureAtlas currentMonkAtlas, WeaponType weaponType) {

        boolean stopSeek;
        boolean stopSeekStrike;
        int regionNum;
        int strikeNum;
        TextureRegion currentRegion;

        Array<Animation> animations = new Array<Animation>();
        Array<TextureRegion> regions = new Array<TextureRegion>();
        StringBuilder stringBuilder = new StringBuilder();

            for (int i=0; i<Constants.RUNNER_ANIMATION_GROUP_NAMES.length; i++) {

                stopSeek = false;
                regions.clear();

                if (weaponType!=null) {
                    stringBuilder.append("weapon").append(weaponType.getGrade()).append("_");
                }

                stringBuilder.append(Constants.RUNNER_ANIMATION_GROUP_NAMES[i]);
                regionNum = 0;

                while (!stopSeek) {
                    regionNum++;

                    currentRegion = (currentMonkAtlas.findRegion(stringBuilder.toString()
                            + String.valueOf(regionNum)));
                    if (currentRegion!=null) {
                        regions.add(currentRegion);
                    } else {
                        stopSeek = true;
                        regionNum = 0;
                    }
                }

                stopSeek = false;
                if (regions.size>0) {
                    animations.add(new Animation(0.1f, regions));
                } else {
                    animations.add(null);
                }

                stringBuilder.delete(0, stringBuilder.length());
            }

            playerStay = animations.get(0);
            playerRun = animations.get(1);
            playerJump = animations.get(2);
            playerHit = animations.get(3);

            playerStrikeList = new CircularList<Animation>();
            stopSeekStrike = false;
            strikeNum = 0;

            while (!stopSeekStrike&&strikeNum<100) {

                strikeNum++;
                stopSeek = false;
                regions.clear();
                regionNum = 0;

                while (!stopSeek) {

                    if (weaponType!=null) {
                        stringBuilder.append("weapon").append(weaponType.getGrade()).append("_");
                    }

                    stringBuilder.append(Constants.RUNNER_ANIMATION_STRIKE_NAMES[0]).append(strikeNum);

                    regionNum++;


                    stringBuilder.append(Constants.FRAME_STRING).append(regionNum);

                    currentRegion = (currentMonkAtlas.findRegion(stringBuilder.toString()));

                    if (currentRegion!=null) {
                        regions.add(currentRegion);
                    } else {
                        stopSeek = true;
                        regionNum = 0;
                    }

                    stringBuilder.delete(0, stringBuilder.length());
                }

                stopSeek = false;
                if (regions.size>0) {
                    playerStrikeList.add(new Animation(0.2f/regions.size, regions, Animation.PlayMode.NORMAL));
                } else {
                    stopSeekStrike=true;
                }

                stringBuilder.delete(0, stringBuilder.length());
            }

    }

    //Инициализация ресурсов уровня
    public static void initLevel(String levelName) {

        environmentAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/environment.atlas"));
        enemiesAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/enemies.atlas"));
        obstaclesAtlas = new TextureAtlas(Gdx.files.internal("textures/" + levelName + "/obstacles.atlas"));

        if (levelMusic==null||!levelMusic.isPlaying()) {
            levelMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + levelName + "/music.mp3"));
            levelMusic.setLooping(true);
            levelMusic.setVolume(SoundSystem.getMusicValue());
        }
    }

    public static void initMenu() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainTheme.mp3"));
        menuMusic.setVolume(SoundSystem.getMusicValue());

        menuTestValueSound = Gdx.audio.newSound(Gdx.files.internal("sound/kiya3.wav"));

        menuBackgroundTexture1 = new Texture(Gdx.files.internal("textures/menu/menu_background1.png"));
        menuBackgroundTexture2 = new Texture(Gdx.files.internal("textures/menu/menu_background2.png"));
        levelLock = new Texture(Gdx.files.internal("textures/menu/lock.png"));
        achieveAtlas = new TextureAtlas(Gdx.files.internal("achieve/achieve.atlas"));
        bonusesAtlas = new TextureAtlas(Gdx.files.internal("bonuses/bonuses.atlas"));

        sGuiSkin = new Skin(Gdx.files.internal("gui/forskin/exp/gui_exp.json"));
        broadbord = new NinePatch(sGuiSkin.getAtlas().createPatch("broadbord"));

        sSnowParticle = new ParticleEffect();
        sSnowParticle.load(Gdx.files.internal("particles/snow.p"), Gdx.files.internal("particles"));

        sSnowParticleBack = new ParticleEffect();
        sSnowParticleBack.load(Gdx.files.internal("particles/snow_back.p"), Gdx.files.internal("particles"));

        sCandleParticle = new ParticleEffect();
        sCandleParticle.load(Gdx.files.internal("particles/candle2.p"), Gdx.files.internal("particles"));

    }

    public static void initLogo() {
        logoPicture = new Texture(Gdx.files.internal("Logo.png"));
        logoSound = Gdx.audio.newMusic(Gdx.files.internal("sound/Thunder.mp3"));
    }

    public static void disposeLogo() {
        logoPicture.dispose();
        logoSound.dispose();
    }

    public static void disposeMenu() {
        menuMusic.dispose();
        menuTestValueSound.dispose();
        menuBackgroundTexture1.dispose();
        menuBackgroundTexture2.dispose();
        levelLock.dispose();
        achieveAtlas.dispose();
        bonusesAtlas.dispose();
        sGuiSkin.dispose();
        sGuiSkin.dispose();
        sSnowParticle.dispose();
        sSnowParticleBack.dispose();
        sCandleParticle.dispose();
    }

    public static void disposeGame() {
        monkAtlas.dispose();
        monkArmour1Atlas.dispose();
        monkArmour2Atlas.dispose();
        monkArmour3Atlas.dispose();
        menuBackgroundTexture1.dispose();
        menuBackgroundTexture2.dispose();

        finishDecoration.dispose();

        itemsAtlas.dispose();

        achieveAtlas.dispose();
        bonusesAtlas.dispose();
        lumpsAtlas.dispose();

        dragonAtlas.dispose();

//        monkStrikeSound1.dispose();
        monkStrikeSound2.dispose();
        monkStrikeSound3.dispose();
        monkStrikeSound4.dispose();
        monkStrikeSound5.dispose();

        for (Sound weaponSound : monkWeaponStrikeSounds){
            weaponSound.dispose();
        }

        for (Sound armourSound : monkArmourHitSounds){
            armourSound.dispose();
        }

        for (Sound deathSound : enemyDeathSounds){
            deathSound.dispose();
        }

        getBonusSound.dispose();
        balanceBonusSound.dispose();
        retributionBonusSound.dispose();
        deathSound.dispose();

        disposeLevel();

        for (ParticleEffect effect : AssetLoader.sWorkParticleBlood) {
            effect.dispose();
        }

        for (ParticleEffect effect : AssetLoader.sFreeParticleBlood) {
            effect.dispose();
        }

        for (ParticleEffect effect : AssetLoader.sWorkParticleDust) {
            effect.dispose();
        }

        for (ParticleEffect effect : AssetLoader.sFreeParticleDust) {
            effect.dispose();
        }

        sHitParticle.dispose();

        sSnowParticle.dispose();
        sSnowParticleBack.dispose();
    }

    public static void disposeLevel() {
        enemiesAtlas.dispose();
        obstaclesAtlas.dispose();
        environmentAtlas.dispose();

        levelMusic.dispose();
    }
}
