package net.uglukfearless.monk.utils;

/**
 * Created by Ugluk on 13.06.2016.
 */
public class ScoreCounter {

    private static int score;

    private static int highScore = 0;

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
}
