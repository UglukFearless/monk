package net.uglukfearless.monk.utils;

import com.badlogic.gdx.physics.box2d.World;

import net.uglukfearless.monk.actors.Enemy;
import net.uglukfearless.monk.actors.Obstacle;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ugluk on 10.06.2016.
 */
public class DangersHandler {

    private Random rand;

    //лажа
    private float offset_y_blades;

    private GameStage stage;
    private World world;
    private EnemyType [] enemyTypes;
    private ObstacleType [] obstacleTypes;

    private List<EnemyType> allEnemies;

    private List<ObstacleType> allObstacles;

    private List<Danger> allDangers;
    private List<Danger> resolvedDangers;

    public short[][] prohibitionsMap = new short[4][3];

    public DangersHandler(GameStage stage) {
        this.stage = stage;
        this.world = stage.getWorld();
        enemyTypes = EnemyType.values();
        obstacleTypes = ObstacleType.values();
        allEnemies = new ArrayList<EnemyType>();

        allObstacles = new ArrayList<ObstacleType>();

        allDangers = new ArrayList<Danger>();
        resolvedDangers = new ArrayList<Danger>();

        rand = new Random();
    }

    public void init() {

        for (EnemyType enemyType: enemyTypes) {
            allEnemies.add(enemyType);
        }

        for (ObstacleType obstacleType: obstacleTypes) {
            allObstacles.add(obstacleType);
        }

        allDangers.addAll(allEnemies);
        allDangers.addAll(allObstacles);
    }

    public void createDangers(boolean pit, float startX) {

//        ObstaclesMap.leaf();

        if(pit==true) {

//            ObstaclesMap.pit();

            for (int j=0;j<2;j++) {
                resolvedDangers.clear();

                prohibitionsMap[0][j] = (short) (prohibitionsMap[0][j]
                        | net.uglukfearless.monk.constants.PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                        | net.uglukfearless.monk.constants.PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);

                for (Danger danger: allDangers) {

                    if (danger.checkResolve(prohibitionsMap[0][j])) {
                        resolvedDangers.add(danger);
                    }
                }

                if (resolvedDangers.size()>0) {
                   Danger typeDanger = resolvedDangers.get(rand.nextInt(resolvedDangers.size()));

                    if (typeDanger instanceof EnemyType) {

                        EnemyType enemyType = (EnemyType)typeDanger;
                        Enemy enemy = new Enemy(WorldUtils.createEnemy(world,
                                startX + net.uglukfearless.monk.constants.Constants.GROUND_PIT/2, 0
                                        + net.uglukfearless.monk.constants.Constants.LAYOUT_Y_STEP*j, enemyType));

                        prohibitionsMap[0][j+1] = (short) (prohibitionsMap[0][j+1]
                                |enemyType.getProhibitionsMap()[0][1]);
                        prohibitionsMap[1][j+1] = (short) (prohibitionsMap[1][j+1]
                                |enemyType.getProhibitionsMap()[1][1]);
                        prohibitionsMap[1][j] = (short) (prohibitionsMap[1][j]
                                |enemyType.getProhibitionsMap()[1][0]);


                        stage.addActor(enemy);

                    } else if (typeDanger instanceof ObstacleType) {

                        ObstacleType obstacleType = (ObstacleType)typeDanger;

                        //это нужно будет переписать!
                        if (j==1&&obstacleType.isTrap()) {
                            offset_y_blades = 2;
                        } else {
                            offset_y_blades = 0;
                        }

                        Obstacle obstacle = new Obstacle(WorldUtils.createObstacle(world,
                                startX + net.uglukfearless.monk.constants.Constants.GROUND_PIT/2, 0
                                        + net.uglukfearless.monk.constants.Constants.LAYOUT_Y_STEP*j + offset_y_blades,obstacleType));

//                        ObstaclesMap.setObstacle(0,j, obstacle.getBody()
//                                .getFixtureList().get(0).getFilterData().categoryBits, true);

                        prohibitionsMap[0][j+1] = (short) (prohibitionsMap[0][j+1]
                                |obstacleType.getProhibitionsMap()[0][1]);
                        prohibitionsMap[1][j+1] = (short) (prohibitionsMap[1][j+1]
                                |obstacleType.getProhibitionsMap()[1][1]);
                        prohibitionsMap[1][j] = (short) (prohibitionsMap[1][j]
                                |obstacleType.getProhibitionsMap()[1][0]);

                        stage.addActor(obstacle);
                    }

                }
            }

            startX += net.uglukfearless.monk.constants.Constants.GROUND_PIT*1.5f;
            prohibitionsMap[0][0] = prohibitionsMap[1][0];
            prohibitionsMap[0][1] = prohibitionsMap[1][1];
        }

        for (int i = 0;i<3;i++) {
            for (int j = 0; j < 2; j++) {
                resolvedDangers.clear();

                for (Danger danger : allDangers) {

                    if (danger.checkResolve(prohibitionsMap[i][j])) {
                        resolvedDangers.add(danger);
                    }
                }

                if (resolvedDangers.size() > 0) {

                    Danger typeDanger = resolvedDangers.get(rand.nextInt(resolvedDangers.size()));

                    if (typeDanger instanceof EnemyType) {

                        EnemyType enemyType = (EnemyType) typeDanger;
                        Enemy enemy = new Enemy(WorldUtils.createEnemy(world,
                                startX + enemyType.getWidth() + net.uglukfearless.monk.constants.Constants.STEP_OF_DANGERS * i, 0
                                        + net.uglukfearless.monk.constants.Constants.LAYOUT_Y_STEP * j, enemyType));

                        prohibitionsMap[i][j + 1] = (short) (prohibitionsMap[i][j + 1]
                                | enemyType.getProhibitionsMap()[0][1]);
                        prohibitionsMap[i + 1][j + 1] = (short) (prohibitionsMap[i + 1][j + 1]
                                | enemyType.getProhibitionsMap()[1][1]);
                        prohibitionsMap[i + 1][j] = (short) (prohibitionsMap[i + 1][j]
                                | enemyType.getProhibitionsMap()[1][0]);


                        stage.addActor(enemy);
                    } else if (typeDanger instanceof ObstacleType) {


                        ObstacleType obstacleType = (ObstacleType) typeDanger;

                        //это нужно будет переписать!
                        if (j == 1 && obstacleType.isTrap()) {
                            offset_y_blades = 2;
                        } else {
                            offset_y_blades = 0;
                        }

                        Obstacle obstacle = new Obstacle(WorldUtils.createObstacle(world,
                                startX + obstacleType.getWidth() / 2 + net.uglukfearless.monk.constants.Constants.STEP_OF_DANGERS * i, 0
                                        + net.uglukfearless.monk.constants.Constants.LAYOUT_Y_STEP * j + offset_y_blades, obstacleType));

//                        ObstaclesMap.setObstacle(i,j, obstacle.getBody()
//                                .getFixtureList().get(0).getFilterData().categoryBits, false);

                        prohibitionsMap[i][j + 1] = (short) (prohibitionsMap[i][j + 1]
                                | obstacleType.getProhibitionsMap()[0][1]);
                        prohibitionsMap[i + 1][j + 1] = (short) (prohibitionsMap[i + 1][j + 1]
                                | obstacleType.getProhibitionsMap()[1][1]);
                        prohibitionsMap[i + 1][j] = (short) (prohibitionsMap[i + 1][j]
                                | obstacleType.getProhibitionsMap()[1][0]);

                        stage.addActor(obstacle);
                    }

                }
            }
        }

        prohibitionsMap[0][0] = prohibitionsMap[3][0];
        prohibitionsMap[0][1] = prohibitionsMap[3][1];

        prohibitionsMap[1][0] = 0;
        prohibitionsMap[1][1] = 0;
        prohibitionsMap[2][0] = 0;
        prohibitionsMap[2][1] = 0;
        prohibitionsMap[3][0] = 0;
        prohibitionsMap[3][1] = 0;

//        System.out.println("[0][0] - " + ObstaclesMap.map[0][0] + " | " + "[1][0] - " + ObstaclesMap.map[1][0] + " | " +
//                "[2][0] - " + ObstaclesMap.map[2][0] + " | " + "[3][0] - " + ObstaclesMap.map[3][0] + " | " +
//                "[4][0] - " + ObstaclesMap.map[4][0] + " | "+ "[5][0] - " + ObstaclesMap.map[5][0] + " | " +
//                "[6][0] - " + ObstaclesMap.map[6][0] + " | " + "[7][0] - " + ObstaclesMap.map[7][0] + " | ");
//        System.out.println("[0][1] - " + ObstaclesMap.map[0][1] + " | " + "[1][1] - " + ObstaclesMap.map[1][1] + " | " +
//                "[2][1] - " + ObstaclesMap.map[2][1] + " | " + "[3][1] - " + ObstaclesMap.map[3][1] + " | " +
//                "[4][1] - " + ObstaclesMap.map[4][1] + " | "+ "[5][0] - " + ObstaclesMap.map[5][1] + " | " +
//                "[6][1] - " + ObstaclesMap.map[6][1] + " | " + "[7][1] - " + ObstaclesMap.map[7][1] + " | ");

    }
}
