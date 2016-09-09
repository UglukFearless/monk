package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.uglukfearless.monk.constants.PreferencesConstants;

/**
 * Created by Ugluk on 17.08.2016.
 */
public class PreferencesManager {

    private static Preferences sStatistics = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_STATISTICS);
    private static Preferences sSetting = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_SETTING);

    //НАСТРОЙКИ*******************************************

    public static void setMusicEnable(boolean enable) {
        sSetting.putBoolean(PreferencesConstants.MUSIC_ENABLE_KEY, enable);
        sSetting.flush();
    }

    public static void setSoundEnable(boolean enable) {
        sSetting.putBoolean(PreferencesConstants.SOUND_ENABLE_KEY, enable);
        sSetting.flush();
    }

    public static void setMusicValue(float value) {
        if (value>1) {
            value = 1;
        }
        sSetting.putFloat(PreferencesConstants.MUSIC_VALUE_KEY, value);
        sSetting.flush();
    }

    public static void setSoundValue(float value) {
        if (value>1) {
            value = 1;
        }
        sSetting.putFloat(PreferencesConstants.SOUND_VALUE_KEY, value);
        sSetting.flush();
    }

    public static boolean getMusicEnable() {
        return sSetting.getBoolean(PreferencesConstants.MUSIC_ENABLE_KEY, true);
    }

    public static boolean getSoundEnable() {
        return sSetting.getBoolean(PreferencesConstants.SOUND_ENABLE_KEY, true);
    }

    public static float getMusicValue() {
        return sSetting.getFloat(PreferencesConstants.MUSIC_VALUE_KEY, 0.5f);
    }

    public static float getSoundValue() {
        return sSetting.getFloat(PreferencesConstants.SOUND_VALUE_KEY, 0.5f);
    }

    //СТАТИСТИКА*******************************************

    public static void setScore(int score) {
        if (score>getHighScore()) {
            sStatistics.putInteger(PreferencesConstants.STATS_HIGHSCORE_KEY, score);
            sStatistics.flush();
        }
    }

    public static int getHighScore() {
        return sStatistics.getInteger(PreferencesConstants.STATS_HIGHSCORE_KEY, 0);
    }

    public static int getTime() {
        return sStatistics.getInteger(PreferencesConstants.STATS_TOTALTIME_KEY, 0);
    }

    public static int getKilled() {
        return sStatistics.getInteger(PreferencesConstants.STATS_KILLED_KEY, 0);
    }

    public static int getDestroyed() {
        return sStatistics.getInteger(PreferencesConstants.STATS_DESTROYED_KEY, 0);
    }

    public static int getDeaths() {
        return sStatistics.getInteger(PreferencesConstants.STATS_DEATHS_KEY, 0);
    }

    public static float getEfficiency() {
        return sStatistics.getFloat(PreferencesConstants.STATS_EFFICIENCY_KEY, 0.0f);
    }

    public static void addTime(int time) {
        sStatistics.putInteger(PreferencesConstants.STATS_TOTALTIME_KEY, getTime() + time);
        sStatistics.flush();
    }

    public static void addKilled(int killed) {
        sStatistics.putInteger(PreferencesConstants.STATS_KILLED_KEY, getKilled() + killed);
        sStatistics.flush();
    }

    public static void addDestroyed(int destroyed) {
        sStatistics.putInteger(PreferencesConstants.STATS_DESTROYED_KEY, getDestroyed() + destroyed);
        sStatistics.flush();
    }

    public static void addDeath() {
        sStatistics.putInteger(PreferencesConstants.STATS_DEATHS_KEY, getDeaths() + 1);
        sStatistics.flush();
    }

    public static void calcAddEfficiency() {
        float efficiencyTotal;
        if (getDeaths()==0) {
            efficiencyTotal = (getKilled() + getDestroyed())/1f;
        } else {
            efficiencyTotal = (float)(getKilled() + getDestroyed())/getDeaths();
        }
        sStatistics.putFloat(PreferencesConstants.STATS_EFFICIENCY_KEY, efficiencyTotal);
        sStatistics.flush();
    }

    //АЧИВОЧКИ*******************************************
    public static void unlockAchieve(String achieve_key) {
        sStatistics.putBoolean(achieve_key, true);
        sStatistics.flush();
    }

    //извлечение значений
    public static boolean checkAchieve(String achieve_key) {
        return sStatistics.getBoolean(achieve_key, false);
    }
}
