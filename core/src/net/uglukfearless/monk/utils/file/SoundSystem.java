package net.uglukfearless.monk.utils.file;


import com.badlogic.gdx.audio.Music;


/**
 * Created by Ugluk on 16.08.2016.
 */
public class SoundSystem {

    private static boolean sMusicEnable = true;
    private static boolean sSoundEnable = true;

    private static float sMusicValue = 0.5f;
    private static float sSoundValue = 0.5f;

    private static Music sCurrentMusic;

    public static void init() {
        sMusicEnable = PreferencesManager.getMusicEnable();
        sSoundEnable = PreferencesManager.getSoundEnable();

        sMusicValue = PreferencesManager.getMusicValue();
        sSoundValue = PreferencesManager.getSoundValue();
    }

    public static boolean isMusicEnable() {
        return sMusicEnable;
    }

    public static void setMusicEnable(boolean musicEnable) {
        sMusicEnable = musicEnable;
        if (musicEnable) {
            sCurrentMusic.setVolume(sMusicValue);
        } else {
            sCurrentMusic.setVolume(0);
        }
    }

    public static boolean isSoundEnable() {
        return sSoundEnable;
    }

    public static void setSoundEnable(boolean soundEnable) {
        sSoundEnable = soundEnable;
    }

    public static float getMusicValue() {
        if (!sMusicEnable) {
            return 0;
        }
        return sMusicValue;
    }

    public static void setMusicValue(float musicValue) {
        if (musicValue>1) {
            musicValue = 1;
        }
        sMusicValue = musicValue;
    }

    public static float getSoundValue() {
        if (!sSoundEnable) {
            return 0;
        }
        return sSoundValue;
    }

    public static void setSoundValue(float soundValue) {
        if (soundValue>1) {
            soundValue = 1;
        }
        sSoundValue = soundValue;
    }


    public static void registrationMusic(Music music) {
        sCurrentMusic = music;
    }

    public static void removeMusic(Music music) {
        sCurrentMusic = null;
    }

    public static void setCurrentMusicValue(float value) {
        if (value>1) {
            value = 1;
        }
        sCurrentMusic.setVolume(value);
    }
}
