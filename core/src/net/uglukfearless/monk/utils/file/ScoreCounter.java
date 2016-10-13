package net.uglukfearless.monk.utils.file;

import com.badlogic.gdx.utils.Array;

import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;
import net.uglukfearless.monk.utils.gameplay.achievements.AdeptKunFu;
import net.uglukfearless.monk.utils.gameplay.achievements.AdeptTamesivari;
import net.uglukfearless.monk.utils.gameplay.achievements.BoundInFlush;
import net.uglukfearless.monk.utils.gameplay.achievements.GeniusKunFu;
import net.uglukfearless.monk.utils.gameplay.achievements.GeniusTamesivari;
import net.uglukfearless.monk.utils.gameplay.achievements.HandsInBlood;
import net.uglukfearless.monk.utils.gameplay.achievements.MasterKunFu;
import net.uglukfearless.monk.utils.gameplay.achievements.MasterTamesivari;
import net.uglukfearless.monk.utils.gameplay.achievements.Mortal;
import net.uglukfearless.monk.utils.gameplay.achievements.Mushroom;
import net.uglukfearless.monk.utils.gameplay.achievements.NeophyteKunFu;
import net.uglukfearless.monk.utils.gameplay.achievements.NeophyteTamesivari;
import net.uglukfearless.monk.utils.gameplay.achievements.Reborn;
import net.uglukfearless.monk.utils.gameplay.achievements.Ruthless;
import net.uglukfearless.monk.utils.gameplay.achievements.WheelOfSamsara;

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
    private static int enemiesAll = 0;
    private static int destroyed = 0;
    private static int deaths = 0;
    private static float efficiency = 0;

    private static int useBuddha = 0;
    private static int useGhost = 0;
    private static int useRetribution = 0;
    private static int useRevival = 0;
    private static int useStrongBeat = 0;
    private static int useThunderFist = 0;
    private static int useWings = 0;

    private static Array<Achievement> sAchievementList;

    public static void createAchieveList() {

        sAchievementList = new Array<Achievement>();

        sAchievementList.add(new Mushroom());
        sAchievementList.add(new Mortal());
        sAchievementList.add(new HandsInBlood());
        sAchievementList.add(new NeophyteKunFu());
        sAchievementList.add(new AdeptKunFu());
        sAchievementList.add(new MasterKunFu());
        sAchievementList.add(new GeniusKunFu());
        sAchievementList.add(new NeophyteTamesivari());
        sAchievementList.add(new AdeptTamesivari());
        sAchievementList.add(new MasterTamesivari());
        sAchievementList.add(new GeniusTamesivari());
        sAchievementList.add(new BoundInFlush());
        sAchievementList.add(new Reborn());
        sAchievementList.add(new WheelOfSamsara());
        sAchievementList.add(new Ruthless());
    }


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

    public static int getEnemiesAll() {
        return enemiesAll;
    }

    public static int getKillingSpeed() {
        return (int)(killed*60/time);
    }

    public static float getKillPercent() {
        if (enemiesAll!=0) {
            return (((int)killed*100/enemiesAll)*100)/100f;
        } else {
            return 0;
        }

    }

    public static void setTime(int time) {
        ScoreCounter.time = time;
    }

    public static void increaseEnemies() {
        enemiesAll++;
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

    //подсчет бонусов
    public static void increaseBuddha() {
        useBuddha++;
    }
    public static void increaseGhost() {
        useGhost++;
    }
    public static void increaseRetribution() {
        useRetribution++;
    }
    public static void increaseRevival() {
        useRevival++;
    }
    public static void increaseStrongBeat() {
        useStrongBeat++;
    }
    public static void increaseThunderFist() {
        useThunderFist++;
    }
    public static void increaseWings() {
        useWings++;
    }

    public static void saveCalcStats(String currentKillerKey) {
        saveCalcStats();
        PreferencesManager.setDeathCause(currentKillerKey);
    }
    public static void saveCalcStats() {

        PreferencesManager.setScore(score);
        PreferencesManager.addTime(time);
        PreferencesManager.addKilled(killed);
        PreferencesManager.addDestroyed(destroyed);
        PreferencesManager.addEnemies(enemiesAll);
        if (deaths == 0) {
            efficiency = (killed + destroyed)/1f;
        } else {
            efficiency = (float)(killed + destroyed)/deaths;
        }
        PreferencesManager.calcAddEfficiency();

        //по бонусам
        PreferencesManager.addBuddha(useBuddha);
        PreferencesManager.addGhost(useGhost);
        PreferencesManager.addRetribution(useRetribution);
        PreferencesManager.addRevival(useRevival);
        PreferencesManager.addThunder(useThunderFist);
        PreferencesManager.addStrong(useStrongBeat);
        PreferencesManager.addWings(useWings);
    }

    public static void resetStats() {

        resetScore();
        time = 0;
        killed = 0;
        destroyed = 0;
        deaths = 0;
        efficiency = 0;
        enemiesAll = 0;

        useBuddha = 0;
        useGhost = 0;
        useRetribution = 0;
        useRevival = 0;
        useStrongBeat = 0;
        useThunderFist = 0;
        useWings = 0;
    }

    public static Array<Achievement> getAchieveList() {
        return sAchievementList;
    }
}
