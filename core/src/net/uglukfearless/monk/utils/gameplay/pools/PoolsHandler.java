package net.uglukfearless.monk.utils.gameplay.pools;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.actors.gameplay.Enemy;
import net.uglukfearless.monk.actors.gameplay.Lump;
import net.uglukfearless.monk.actors.gameplay.Obstacle;
import net.uglukfearless.monk.actors.gameplay.RunnerShell;
import net.uglukfearless.monk.actors.gameplay.Shell;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;

import java.util.HashMap;

/**
 * Created by Ugluk on 29.08.2016.
 */
public class PoolsHandler {

    private static World sWorld;

    public static Pool<Lump> sLumpsPool;
    public static Pool<Shell> sShellPool;
    public static Pool<RunnerShell> sRunnerShellPool;
    public static HashMap<String, Pool<Enemy>> sEnemiesPools;
    public static HashMap<String, Pool<Obstacle>> sObstaclePools;

    public static void initPools(final World world) {

        sWorld = world;

        sEnemiesPools = new HashMap<String, Pool<Enemy>>();
        sObstaclePools = new HashMap<String, Pool<Obstacle>>();

        sLumpsPool = new Pool<Lump>(16, 40) {
            @Override
            protected Lump newObject() {
                return new Lump(sWorld);
            }
        };

        sShellPool = new Pool<Shell>(5,10) {
            @Override
            protected Shell newObject() {
                return new Shell(sWorld);
            }
        };

        sRunnerShellPool = new Pool<RunnerShell>(8,10) {
            @Override
            protected RunnerShell newObject() {
                return new RunnerShell(sWorld);
            }
        };

        for (final EnemyType enemyType : EnemyType.values()) {

            if (enemyType.getPriority()>0) {
                Pool<Enemy> pool = new Pool<Enemy>() {
                    @Override
                    protected Enemy newObject() {
                        return new Enemy(sWorld, enemyType);
                    }
                };
                sEnemiesPools.put(enemyType.name(), pool);

                Array<Enemy> enemies = new Array<Enemy>(6);
                for (int i=0;i<6;i++) {
                    enemies.add(pool.obtain());
                }
                pool.freeAll(enemies);

            }
        }

        for (final ObstacleType obstacleType: ObstacleType.values()) {

            if (obstacleType.getPriority()>0) {
                Pool<Obstacle> pool = new Pool<Obstacle>() {
                    @Override
                    protected Obstacle newObject() {
                        return new Obstacle(sWorld, obstacleType);
                    }
                };
                sObstaclePools.put(obstacleType.name(), pool);

                Array<Obstacle> obstacles = new Array<Obstacle>(6);
                for (int i=0;i<6;i++) {
                    obstacles.add(pool.obtain());
                }
                pool.freeAll(obstacles);
            }
        }

    }
}
