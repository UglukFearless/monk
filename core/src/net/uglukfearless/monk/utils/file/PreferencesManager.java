package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.enums.WeaponType;

import java.util.Map;

/**
 * Created by Ugluk on 17.08.2016.
 */
public class PreferencesManager {

    private static Preferences sStatistics = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_STATISTICS);
    private static Preferences sSetting = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_SETTING);

    private static Preferences sDangersKeys = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_DANGERS_KEYS);
    private static Preferences sDangersNames = Gdx.app.getPreferences(PreferencesConstants.PREFERENCES_DANGERS_NAMES);

    //НАСТРОЙКИ*******************************************

    public static void setMusicEnable(boolean enable) {
        sSetting.putBoolean(PreferencesConstants.SET_MUSIC_ENABLE_KEY, enable);
        sSetting.flush();
    }

    public static void setSoundEnable(boolean enable) {
        sSetting.putBoolean(PreferencesConstants.SET_SOUND_ENABLE_KEY, enable);
        sSetting.flush();
    }

    public static void setMusicValue(float value) {
        if (value>1) {
            value = 1;
        }
        sSetting.putFloat(PreferencesConstants.SET_MUSIC_VALUE_KEY, value);
        sSetting.flush();
    }

    public static void setSoundValue(float value) {
        if (value>1) {
            value = 1;
        }
        sSetting.putFloat(PreferencesConstants.SET_SOUND_VALUE_KEY, value);
        sSetting.flush();
    }

    public static boolean getMusicEnable() {
        return sSetting.getBoolean(PreferencesConstants.SET_MUSIC_ENABLE_KEY, true);
    }

    public static boolean getSoundEnable() {
        return sSetting.getBoolean(PreferencesConstants.SET_SOUND_ENABLE_KEY, true);
    }

    public static float getMusicValue() {
        return sSetting.getFloat(PreferencesConstants.SET_MUSIC_VALUE_KEY, 0.5f);
    }

    public static float getSoundValue() {
        return sSetting.getFloat(PreferencesConstants.SET_SOUND_VALUE_KEY, 0.5f);
    }

    public static boolean isRussianLanguage() {
        return sSetting.getString(PreferencesConstants.SET_LANGUAGE).equals("ru");
    }

    public static boolean isEnglishLanguage() {
        return sSetting.getString(PreferencesConstants.SET_LANGUAGE).equals("en");
    }

    public static String getLanguage() {
        return sSetting.getString(PreferencesConstants.SET_LANGUAGE);
    }

    public static void setLanguage(String language) {
        sSetting.putString(PreferencesConstants.SET_LANGUAGE, language);
        sSetting.flush();
    }

    //СТАТИСТИКА*******************************************

    public static void setLastDate(long mileSeconds) {
        sStatistics.putLong(PreferencesConstants.STATS_LAST_DATE, mileSeconds);
        sStatistics.flush();
    }

    public static long getLastDate() {
        return sStatistics.getLong(PreferencesConstants.STATS_LAST_DATE, 0);
    }

    //ФИНАНСЫ И ПРЕДМЕТЫ ***************************************************************************
    public static void addTreasures(int quantetly) {
        if (quantetly>0) {
            sStatistics.putInteger(PreferencesConstants.STATS_TREASURES, getTreasures() + quantetly);
            sStatistics.flush();
            sStatistics.putInteger(PreferencesConstants.STATS_TREASURES_TOTAL, getTreasuresTotal() + quantetly);
            sStatistics.flush();
        }
    }

    public static int getTreasuresTotal() {
        return sStatistics.getInteger(PreferencesConstants.STATS_TREASURES_TOTAL, 0);
    }

    public static int getTreasures() {
        return sStatistics.getInteger(PreferencesConstants.STATS_TREASURES, 0);
    }

    public static boolean purchase(int quantetly) {
        if (quantetly>getTreasures()) {
            return false;
        } else {
            sStatistics.putInteger(PreferencesConstants.STATS_TREASURES, getTreasures() - quantetly);
            sStatistics.flush();
            return true;
        }
    }

    public static ArmourType getArmour() {
        String currentArmour = sStatistics.getString(PreferencesConstants.ITEM_ARMOUR, "null");
        if (!currentArmour.equals("null")) {
            return ArmourType.valueOf(currentArmour);
        }
        return null;
    }

    public static WeaponType getWeapon() {
        String currentWeapon = sStatistics.getString(PreferencesConstants.ITEM_WEAPON, "null");
        if (!currentWeapon.equals("null")) {
            return WeaponType.valueOf(currentWeapon);
        }
        return null;
    }

    public static void setArmour(ArmourType armour) {
        sStatistics.putString(PreferencesConstants.ITEM_ARMOUR, armour.name());
        sStatistics.flush();
    }

    public static void setWeapon(WeaponType weapon) {
        sStatistics.putString(PreferencesConstants.ITEM_WEAPON, weapon.name());
        sStatistics.flush();
    }

    public static void clearArmour() {
        sStatistics.putString(PreferencesConstants.ITEM_ARMOUR, "null");
        sStatistics.flush();
    }

    public static void clearWeapon() {
        sStatistics.putString(PreferencesConstants.ITEM_WEAPON, "null");
        sStatistics.flush();
    }

    //МЕТРИКИ ЗАБЕГОВ ******************************************************************************
    public static void setScore(int score, String levelName) {
        if (score>getHighScore()) {
            sStatistics.putInteger(PreferencesConstants.STATS_HIGHSCORE_KEY, score);
            sStatistics.flush();
        }

        if (score>getLevelHighScore(levelName)) {
            sStatistics.putInteger(PreferencesConstants.STATS_HIGHSCORE_KEY.concat(levelName), score);
        }
    }

    public static int getHighScore() {
        return sStatistics.getInteger(PreferencesConstants.STATS_HIGHSCORE_KEY, 0);
    }

    public static int getLevelHighScore(String levelName) {
        return sStatistics.getInteger(PreferencesConstants.STATS_HIGHSCORE_KEY.concat(levelName), 0);
    }

    public static int getTime() {
        return sStatistics.getInteger(PreferencesConstants.STATS_TOTALTIME_KEY, 0);
    }

    public static int getBestTime() {
        return sStatistics.getInteger(PreferencesConstants.STATS_BEST_TIME_KEY, 0);
    }

    public static float getAverageTime() {
        if (getDeaths()==0) {
            return 0;
        }
        return ((float)getTime()/(float)getDeaths());
    }

    public static int getKillingRate() {
        if (getTime()==0) {
            return 0;
        }
        return getKilled()*60/getTime();
    }

    public static long getKillPercent() {
        if (getEnemies()==0) {
            return 0;
        }
        return getKilled()*100/getEnemies()>100 ? 100 : getKilled()*100/getEnemies();
    }

    public static int getKilled() {
        return sStatistics.getInteger(PreferencesConstants.STATS_KILLED_KEY, 0);
    }

    public static int getDestroyed() {
        return sStatistics.getInteger(PreferencesConstants.STATS_DESTROYED_KEY, 0);
    }

    private static long getEnemies() {
        return sStatistics.getLong(PreferencesConstants.STATS_ENEMIES_ALL_KEY, 0);
    }

    public static int getDeaths() {
        return sStatistics.getInteger(PreferencesConstants.STATS_DEATHS_KEY, 0);
    }

    public static float getEfficiency() {
        return sStatistics.getFloat(PreferencesConstants.STATS_EFFICIENCY_KEY, 0.0f);
    }


    //геттеры бонусов
    public static int getBuddha() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_BUDDHA_KEY, 0);
    }
    public static int getGhost() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_GHOST_KEY, 0);
    }
    public static int getRetribution() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_RETRIBUTION_KEY, 0);
    }
    public static int getRevival() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_REVIVAL_KEY, 0);
    }
    public static int getThunder() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_THUNDER_KEY, 0);
    }
    public static int getStrong() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_STRONG_KEY, 0);
    }
    public static int getWings() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_WINGS_KEY, 0);
    }
    public static int getDragon() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USE_DRAGON_KEY, 0);
    }

    public static void addTime(int time) {
        sStatistics.putInteger(PreferencesConstants.STATS_TOTALTIME_KEY, getTime() + time);
        if (time>getBestTime()) {
            sStatistics.putInteger(PreferencesConstants.STATS_BEST_TIME_KEY, time);
        }
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

    public static void addEnemies(int enemiesAll) {
        sStatistics.putLong(PreferencesConstants.STATS_ENEMIES_ALL_KEY, getEnemies() + enemiesAll);
        sStatistics.flush();
    }

    public static void addDeath() {
        sStatistics.putInteger(PreferencesConstants.STATS_DEATHS_KEY, getDeaths() + 1);
        sStatistics.flush();
    }

    //эддеры бонусов
    public static void addBuddha(int buddha) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_BUDDHA_KEY, getBuddha() + buddha);
        sStatistics.flush();
    }
    public static void addGhost(int ghost) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_GHOST_KEY, getGhost() + ghost);
        sStatistics.flush();
    }
    public static void addRetribution(int retribution) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_RETRIBUTION_KEY, getRetribution() + retribution);
        sStatistics.flush();
    }
    public static void addRevival(int revival) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_REVIVAL_KEY, getRevival() + revival);
        sStatistics.flush();
    }
    public static void addThunder(int thunder) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_THUNDER_KEY, getThunder() + thunder);
        sStatistics.flush();
    }
    public static void addStrong(int strong) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_STRONG_KEY, getStrong() + strong);
        sStatistics.flush();
    }
    public static void addWings(int wings) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_WINGS_KEY, getWings() + wings);
        sStatistics.flush();
    }

    public static void addDragon(int dragon) {
        sStatistics.putInteger(PreferencesConstants.STATS_USE_DRAGON_KEY, getDragon() + dragon);
        sStatistics.flush();
    }

    //геттеры и сеттеры бонусов по человечески
    public static void addUsingBonus(String keyBonus) {
        sStatistics.putInteger(keyBonus, getBuddha() + 1);
        sStatistics.flush();
    }

    public static int getUsingBonus(String keyBonus) {
        return sStatistics.getInteger(keyBonus, 0);
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



    public static int getReceivedTimeBonuses() {
        return sStatistics.getInteger(PreferencesConstants.STATS_USED_TIME_BONUSES, 0);
    }

    public static void increaseReceivedTimeBonuses() {
        sStatistics.putInteger(PreferencesConstants.STATS_USED_TIME_BONUSES, getReceivedTimeBonuses() + 1);
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

    //СПИСОК ВГРАГОВ*******************************************
    public static Map<String,String> getDangersKeys() {
        return (Map<String, String>) sDangersKeys.get();
    }

    public static void putDangerKey(String key, String enName, String ruName) {
        sDangersKeys.putString(key, key);
        sDangersKeys.flush();

        sDangersNames.putString(key.concat("_EN"), enName);
        sDangersNames.putString(key.concat("_RU"), ruName);
        sDangersNames.flush();
    }

    public static String getEnName(String key) {
        return sDangersNames.getString(key.concat("_EN"),"");
    }

    public static String getRuName(String key) {
        return sDangersNames.getString(key.concat("_RU"), "");
    }

    //геттер для типа смерти
    public static int getDeathCause(String dangersKey) {
        return sStatistics.getInteger(dangersKey, 0);
    }

    public static int getDeathCausePercent(String dangersKey) {
        if (getDeaths()==0) {
            return 0;
        }
        return getDeathCause(dangersKey)*100/getDeaths();
    }

    //сеттер для типа смерти
    public static void setDeathCause(String dangersKey) {
        sStatistics.putInteger(dangersKey, getDeathCause(dangersKey) + 1);
        sStatistics.flush();
    }

}
