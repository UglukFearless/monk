package net.uglukfearless.monk.utils.gameplay;

import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.ObstacleType;

import java.util.Random;

/**
 * Created by Ugluk on 20.05.2016.
 */
public class RandomUtils {

    public static EnemyType getRandomEnemyType() {
        RandomEnum<EnemyType> randomEnum = new RandomEnum<EnemyType>(EnemyType.class);
        return randomEnum.random();
    }

    public static ObstacleType getRandomObstacleType() {
        RandomEnum<ObstacleType> randomEnum = new RandomEnum<ObstacleType>(ObstacleType.class);
        return randomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {
        public static final Random RND = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[RND.nextInt(values.length)];
        }
    }
}
