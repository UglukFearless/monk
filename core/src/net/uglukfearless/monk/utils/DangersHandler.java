package net.uglukfearless.monk.utils;

import com.badlogic.gdx.physics.box2d.World;

import net.uglukfearless.monk.actors.gameplay.Columns;
import net.uglukfearless.monk.actors.gameplay.Enemy;
import net.uglukfearless.monk.actors.gameplay.Ground;
import net.uglukfearless.monk.actors.gameplay.Obstacle;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PlacingCategory;
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
    private float offset_y;

    private GameStage stage;
    private World world;
    private EnemyType [] enemyTypes;
    private ObstacleType [] obstacleTypes;

    private List<EnemyType> allEnemies;

    private List<ObstacleType> allObstacles;

    private List<Danger> allDangers;
    private List<Danger> resolvedDangers;

    public short[][] prohibitionsMap = new short[3][3];

    private boolean pit;
    private float pitLength = 0;
    private float startX;
    private boolean columns = false;

    private int priorityGround;
    private int priorityPit;
    private int priorityColumns;

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
            if (enemyType.getPriority()>0) {
                for (int i=0; i<enemyType.getPriority();i++) {
                    allEnemies.add(enemyType);
                }
            }
        }

        for (ObstacleType obstacleType: obstacleTypes) {
            if (obstacleType.getPriority()>0) {
                for (int i=0; i<obstacleType.getPriority();i++) {
                    allObstacles.add(obstacleType);
                }
            }
        }

        allDangers.addAll(allEnemies);
        allDangers.addAll(allObstacles);
    }

    public void createDangers(Ground ground1, Ground ground2) {

        selectLandscape();

        if (ground1.getBody().getPosition().x < ground2.getBody().getPosition().x) {
            repositionGround(ground1, ground2);
        } else {
            repositionGround(ground2, ground1);
        }
//        ObstaclesMap.leaf();

        if(pit==true) {
            fillPit();
        } else {
            startX += Constants.GROUND_PIT;
        }

        fillGround();
    }

    private void fillGround() {

        for (int i = 0;i<prohibitionsMap.length-1;i++) {
            for (int j = 0; j < prohibitionsMap[0].length-1; j++) {

                if (fillingCell()) {
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

                            createEnemy(enemyType,i,j,false);


                        } else if (typeDanger instanceof ObstacleType) {


                            ObstacleType obstacleType = (ObstacleType) typeDanger;

                            createObstacle(obstacleType, i, j,false);

                        }

                    }
                }

            }
        }


        prohibitionsMap[0][0] = prohibitionsMap[prohibitionsMap.length-1][0];
        prohibitionsMap[0][1] = prohibitionsMap[prohibitionsMap.length-1][1];

        for (int ii=1;ii<prohibitionsMap.length;ii++) {
            for (int jj=1;jj<prohibitionsMap[ii].length;jj++) {
                prohibitionsMap[ii][jj] = 0;
            }
        }
    }

    private void fillPit() {

        for (int j=0;j<2;j++) {

            if (fillingCell()) {
                resolvedDangers.clear();

                prohibitionsMap[0][j] = (short) (prohibitionsMap[0][j]
                        | PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                        | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);

                for (Danger danger: allDangers) {

                    if (danger.checkResolve(prohibitionsMap[0][j])) {
                        resolvedDangers.add(danger);
                    }
                }

                if (resolvedDangers.size()>0) {
                    Danger typeDanger = resolvedDangers.get(rand.nextInt(resolvedDangers.size()));

                    if (typeDanger instanceof EnemyType) {

                        EnemyType enemyType = (EnemyType)typeDanger;

                        createEnemy(enemyType, 0, j, true);

                    } else if (typeDanger instanceof ObstacleType) {

                        ObstacleType obstacleType = (ObstacleType)typeDanger;

                        createObstacle(obstacleType, 0, j, true);

                    }

                }
            }

        }

        startX += Constants.GROUND_PIT*1.5f;
        prohibitionsMap[0][0] = prohibitionsMap[1][0];
        prohibitionsMap[0][1] = prohibitionsMap[1][1];
    }

    private boolean fillingCell() {
        return rand.nextInt(Constants.DANGERS_PROBABILITY_LIMIT) < Constants.DANGERS_PROBABILITY;
    }

    private void createObstacle(ObstacleType obstacleType, int i, int j, boolean overPit) {

        //это нужно будет переписать!
        if (j==1&&obstacleType.getY()<0) {
            offset_y = obstacleType.getY()*(-1);
        } else {
            offset_y = 0;
        }

        Obstacle obstacle;

        if (overPit) {
            obstacle = new Obstacle(WorldUtils.createObstacle(world,
                    startX - Constants.GROUND_PIT/2, Constants.LAYOUT_Y_ONE
                            + Constants.LAYOUT_Y_STEP*j + offset_y,obstacleType));
        } else {
            obstacle = new Obstacle(WorldUtils.createObstacle(world,
                    startX + obstacleType.getWidth() / 2 + Constants.STEP_OF_DANGERS * i,
                    Constants.LAYOUT_Y_ONE   + Constants.LAYOUT_Y_STEP * j + offset_y, obstacleType));
        }

//                        ObstaclesMap.setObstacle(0,j, obstacle.getBody()
//                                .getFixtureList().get(0).getFilterData().categoryBits, true);

        prohibitionsMap[i][j+1] = (short) (prohibitionsMap[i][j+1]
                |obstacleType.getProhibitionsMap()[0][1]);
        prohibitionsMap[i+1][j+1] = (short) (prohibitionsMap[i+1][j+1]
                |obstacleType.getProhibitionsMap()[1][1]);
        prohibitionsMap[i+1][j] = (short) (prohibitionsMap[i+1][j]
                |obstacleType.getProhibitionsMap()[1][0]);

        stage.addActor(obstacle);
    }

    private void createEnemy(EnemyType enemyType,int i, int j, boolean overPit) {

        Enemy enemy;

        if (overPit) {
            enemy = new Enemy(WorldUtils.createEnemy(world,
                    startX - Constants.GROUND_PIT/2, Constants.LAYOUT_Y_ONE
                            + Constants.LAYOUT_Y_STEP*j, enemyType));
        } else {
            enemy = new Enemy(WorldUtils.createEnemy(world,
                    startX + enemyType.getWidth() + Constants.STEP_OF_DANGERS * i,
                    Constants.LAYOUT_Y_ONE + Constants.LAYOUT_Y_STEP*j, enemyType));
        }


        prohibitionsMap[i][j+1] = (short) (prohibitionsMap[i][j+1]
                |enemyType.getProhibitionsMap()[0][1]);
        prohibitionsMap[i+1][j+1] = (short) (prohibitionsMap[i+1][j+1]
                |enemyType.getProhibitionsMap()[1][1]);
        prohibitionsMap[i+1][j] = (short) (prohibitionsMap[i+1][j]
                |enemyType.getProhibitionsMap()[1][0]);

        stage.addActor(enemy);
    }

    private void repositionGround(Ground firstGround, Ground secondGround) {

        if (columns==true) {
            for (int i=0; i<Constants.COLUMNS_QUANTITY; i++) {
                float x = secondGround.getBody().getPosition().x + Constants.GROUND_WIDTH/2
                        + Constants.COLUMNS_PIT * (i + 1) + Constants.COLUMNS_WIDTH*i;
                Columns columns1 = new Columns(WorldUtils.createColumns(world, x));
                stage.addActor(columns1);
            }
        }

        firstGround.getBody().setTransform(secondGround.getBody().getPosition().x +
                Constants.GROUND_WIDTH + pitLength, Constants.GROUND_Y, 0f);


        startX = secondGround.getBody().getPosition().x + Constants.GROUND_WIDTH/2 + pitLength;
    }

    private void selectLandscape() {

        pitLength = 0;

        priorityGround = rand.nextInt(Constants.PRIORITY_GROUND) - 1;
        priorityPit = rand.nextInt(Constants.PRIORITY_PIT) - 1;
        priorityColumns = rand.nextInt(Constants.PRIORITY_COLUMNS) - 1;

        if (priorityGround>priorityPit&&priorityGround>priorityColumns) {
            pit = false;
            columns = false;
            pitLength = 0;
        } else if (priorityColumns>priorityPit&&priorityColumns>priorityGround) {
            columns = true;
            pit = false;
            pitLength = Constants.COLUMNS_QUANTITY
                    *(Constants.COLUMNS_WIDTH + Constants.COLUMNS_PIT) + Constants.COLUMNS_PIT;
        } else {
            pit = true;
            columns = false;
            pitLength = Constants.GROUND_PIT;
        }
    }
}
