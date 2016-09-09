package net.uglukfearless.monk.utils.file;

import net.uglukfearless.monk.constants.PreferencesConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class ScoreCounter {

    private static int score;

    private static int highScore = PreferencesManager.getHighScore();

    private static int time = 0;
    private static int killed = 0;
    private static int destroyed = 0;
    private static int deaths = 0;
    private static float efficiency = 0;

    private static Set<String> currentAchieve = new HashSet<String>();
    private static Set<String> lockAchieve = new HashSet<String>();
    private static Set<String> newAchieve = new HashSet<String>();

    //возможно в будущем избавлюсь от вызова этого метода и буду его реализовывать иначе
    public static void increaseScore(int score) {
        ScoreCounter.score += score;
    }

    public static void checkScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    public static void resetScore() {
        score = 0;
    }

    public static int getScore() {
        return score;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static int getTime() {
        return time;
    }

    public static int getKilled() {
        return killed;
    }

    public static int getDestroyed() {
        return destroyed;
    }

    public static int getDeaths() {
        return deaths;
    }

    public static float getEfficiency() {
        return efficiency;
    }

    public static void setTime(int time) {
        ScoreCounter.time = time;
    }

    public static void increaseKilled() {
        killed++;
    }
    public static void increaseDestroyed() {
        destroyed++;
    }
    public static void death() {
        deaths++;
    }

    public static void saveCalcStats() {
        PreferencesManager.setScore(score);
        PreferencesManager.addTime(time);
        PreferencesManager.addKilled(killed);
        PreferencesManager.addDestroyed(destroyed);
        PreferencesManager.addDeath();
        if (deaths == 0) {
            efficiency = (killed + destroyed)/1f;
        } else {
            efficiency = (float)(killed + destroyed)/deaths;
        }
        PreferencesManager.calcAddEfficiency();

    }

    public static void resetStats() {

        resetScore();
        time = 0;
        killed = 0;
        destroyed = 0;
        deaths = 0;
        efficiency = 0;
    }

    public static void loadAchieve() {
        for (int i=0;i < PreferencesConstants.ALL_ACHIEVE_KEYS.length; i++) {
            if (PreferencesManager.checkAchieve(PreferencesConstants.ALL_ACHIEVE_KEYS[i])) {
                currentAchieve.add(PreferencesConstants.ALL_ACHIEVE_KEYS[i]);
            } else {
                lockAchieve.add(PreferencesConstants.ALL_ACHIEVE_KEYS[i]);
            }
        }
    }

    public static void checkAchieve() {
        if (PreferencesManager.getTime()>=18000&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_MUSHROOM_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_MUSHROOM_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_MUSHROOM_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_MUSHROOM_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_MUSHROOM_KEY);
        }
        if (PreferencesManager.getKilled()>=1&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_HIB_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_HIB_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_HIB_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_HIB_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_HIB_KEY);
        }
        if (PreferencesManager.getKilled()>=100&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_NKF_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_NKF_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_NKF_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_NKF_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_NKF_KEY);
        }
        if (PreferencesManager.getKilled()>=1000&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_AKF_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_AKF_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_AKF_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_AKF_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_AKF_KEY);
        }
        if (PreferencesManager.getKilled()>=10000&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_MKF_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_MKF_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_MKF_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_MKF_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_MKF_KEY);
        }
        if (PreferencesManager.getDestroyed()>=50&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_NT_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_NT_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_NT_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_NT_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_NT_KEY);
        }
        if (PreferencesManager.getDestroyed()>=500&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_AT_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_AT_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_AT_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_AT_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_AT_KEY);
        }
        if (PreferencesManager.getDestroyed()>=5000&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_MT_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_MT_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_MT_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_MT_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_MT_KEY);
        }
        if (PreferencesManager.getDeaths()>=1&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_MORTAL_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_MORTAL_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_MORTAL_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_MORTAL_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_MORTAL_KEY);
        }
        if (PreferencesManager.getDeaths()>=100&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_BIF_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_BIF_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_BIF_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_BIF_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_BIF_KEY);
        }
        if (PreferencesManager.getDeaths()>=1000&&!currentAchieve.contains(PreferencesConstants.ACHIEVE_WOS_KEY)) {
            PreferencesManager.unlockAchieve(PreferencesConstants.ACHIEVE_WOS_KEY);
            currentAchieve.add(PreferencesConstants.ACHIEVE_WOS_KEY);
            newAchieve.add(PreferencesConstants.ACHIEVE_WOS_KEY);
            lockAchieve.remove(PreferencesConstants.ACHIEVE_WOS_KEY);
        }
    }

    public static Set<String> getCurrentAchieve() {
        return currentAchieve;
    }

    public static Set<String> getLockAchieve() {
        return lockAchieve;
    }

    public static Set<String> getNewAchieve() {
        return newAchieve;
    }
}
