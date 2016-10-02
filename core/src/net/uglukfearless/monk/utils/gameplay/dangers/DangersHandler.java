package net.uglukfearless.monk.utils.gameplay.dangers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import net.uglukfearless.monk.actors.gameplay.Columns;
import net.uglukfearless.monk.actors.gameplay.Enemy;
import net.uglukfearless.monk.actors.gameplay.Ground;
import net.uglukfearless.monk.actors.gameplay.Obstacle;
import net.uglukfearless.monk.actors.gameplay.Pit;
import net.uglukfearless.monk.actors.gameplay.bonuses.GameBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.RevivalBonus;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PlacingCategory;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

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
    private LevelModel mLevelModel;
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
    private Array<Columns> mColumns1;
    private Array<Columns> mColumns2;
    private Pit mPit;
    private Array<GameBonus> mBonuses;

    public DangersHandler(GameStage stage, Array<GameBonus> bonuses, LevelModel levelModel) {
        this.stage = stage;
        mLevelModel = levelModel;
        this.world = stage.getWorld();
        enemyTypes = EnemyType.values();
        obstacleTypes = ObstacleType.values();
        allEnemies = new ArrayList<EnemyType>();

        allObstacles = new ArrayList<ObstacleType>();

        allDangers = new ArrayList<Danger>();
        resolvedDangers = new ArrayList<Danger>();

        rand = new Random();

        mColumns1 = new Array<Columns>();
        mColumns2 = new Array<Columns>();

        mBonuses = bonuses;
    }

    public void init() {

        allEnemies.clear();
        allObstacles.clear();
        allDangers.clear();

        for (EnemyType enemyType: enemyTypes) {
            if (enemyType.getPriority()>0) {
                    allEnemies.add(enemyType);
            }
        }

        for (ObstacleType obstacleType: obstacleTypes) {
            if (obstacleType.getPriority()>0) {
                    allObstacles.add(obstacleType);
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

        if(pit==true) {
            fillPit();
        } else {
            startX += Constants.GROUND_PIT_INIT *1.8f;
        }

        fillGround();
    }

    private void fillGround() {

        for (int i = 0;i<prohibitionsMap.length-1;i++) {
            for (int j = 0; j < prohibitionsMap[0].length-1; j++) {

                if (fillingCell()&&!setBonus(i,j)) {

                    resolvedDangers.clear();

                    for (Danger danger : allDangers) {

                        if (danger.checkResolve(prohibitionsMap[i][j])) {
                            resolvedDangers.add(danger);
                        }
                    }

                    if (resolvedDangers.size() > 0) {

                        Danger typeDanger = resolvedDangers.get(0);

                        for (Danger danger:resolvedDangers) {
                            danger.calcPriority();
                        }

                        for (Danger danger:resolvedDangers) {
                            if (danger.getCurrentPriority()>typeDanger.getCurrentPriority()) {
                                typeDanger = danger;
                            }
                        }




                        if (typeDanger instanceof EnemyType) {

                            EnemyType enemyType = (EnemyType) typeDanger;

                            createEnemy(enemyType,i,j,false);


                        } else if (typeDanger instanceof ObstacleType) {


                            ObstacleType obstacleType = (ObstacleType) typeDanger;

                            createObstacle(obstacleType, i, j,false);

                        }

                    }
                } else {
                    if (j==0) {
                        prohibitionsMap[i][1] = (short) (prohibitionsMap[i][1]
                                |PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
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

    private boolean setBonus(float i,float j) {

        for (GameBonus bonus : mBonuses) {
            if (bonus.isActive()) {

                return false;
            }
        }

        if (rand.nextInt(100)>94) {

            GameBonus gameBonus = mBonuses.get(rand.nextInt(mBonuses.size));

            while ((gameBonus instanceof RevivalBonus)&&(mBonuses.size>1)&&stage.getRevival()==stage.getLimitRevival()) {
                gameBonus = mBonuses.get(rand.nextInt(mBonuses.size));
            }

            gameBonus.init(startX + Constants.STEP_OF_DANGERS * i,
                    Constants.LAYOUT_Y_ONE + Constants.LAYOUT_Y_STEP * j);

            return true;
        }

        return false;
    }

    private void fillPit() {

        for (int j=0;j<2;j++) {

            if (fillingCell()) {
                resolvedDangers.clear();

                prohibitionsMap[0][j] = (short) (prohibitionsMap[0][j]
                        | PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                        | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND
                        | PlacingCategory.CATEGORY_MAP_PROHIBITION_PIT[j]);

                for (Danger danger: allDangers) {

                    if (danger.checkResolve(prohibitionsMap[0][j])) {
                        resolvedDangers.add(danger);
                    }
                }

                if (resolvedDangers.size()>0) {

                    Danger typeDanger = resolvedDangers.get(0);

                    for (Danger danger:resolvedDangers) {
                        danger.calcPriority();
                    }

                    for (Danger danger:resolvedDangers) {
                        if (danger.getCurrentPriority()>typeDanger.getCurrentPriority()) {
                            typeDanger = danger;
                        }
                    }


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

        startX += Constants.GROUND_PIT_INIT *1.8f;
        prohibitionsMap[0][0] = prohibitionsMap[1][0];
        prohibitionsMap[0][1] = prohibitionsMap[1][1];
    }

    private boolean fillingCell() {
        return rand.nextInt(Constants.DANGERS_PROBABILITY_LIMIT) < mLevelModel.mDangersProbability;
    }

    private void createObstacle(ObstacleType obstacleType, int i, int j, boolean overPit) {

        //это нужно будет переписать!
        if (j==0) {
            offset_y = obstacleType.getY_offset();
        } else {
            offset_y = 0;
        }

        Obstacle obstacle = PoolsHandler.sObstaclePools.get(obstacleType.name()).obtain();

        if (overPit) {
            obstacle.init(stage, startX - Constants.GROUND_PIT_INIT / 2f, Constants.LAYOUT_Y_ONE
                            + Constants.LAYOUT_Y_STEP * j + offset_y);
        } else {
            obstacle.init(stage , startX + obstacleType.getWidth() / 2f + Constants.STEP_OF_DANGERS * i,
                    Constants.LAYOUT_Y_ONE   + Constants.LAYOUT_Y_STEP * j + offset_y);
        }


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

        enemy = PoolsHandler.sEnemiesPools.get(enemyType.name()).obtain();

        if (overPit) {
            enemy.init(stage, startX - Constants.GROUND_PIT_INIT / 2f, Constants.LAYOUT_Y_ONE
                    + Constants.LAYOUT_Y_STEP * j);
        } else {
            enemy.init(stage, startX + enemyType.getWidth() + Constants.STEP_OF_DANGERS * i,
                    Constants.LAYOUT_Y_ONE + Constants.LAYOUT_Y_STEP * j);
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

        stage.getRunner().setGrounds(secondGround, firstGround);

        if (columns==true) {

            mPit = stage.getColumnsPit();
            mPit.setPosition(secondGround.getBody().getPosition().x +
                    Constants.GROUND_WIDTH_INIT /2);
            stage.addActor(mPit);

            if (!mColumns1.get(Constants.COLUMNS_QUANTITY_INIT -1).getBody().isActive()) {
                placingColumns(mColumns1, secondGround);
            } else {
                placingColumns(mColumns2, secondGround);
            }
        } else if (pit==true) {

            mPit = stage.getSimplePit();
            mPit.setPosition(secondGround.getBody().getPosition().x +
                            Constants.GROUND_WIDTH_INIT /2);
            stage.addActor(mPit);
        }

        firstGround.getBody().setTransform(secondGround.getBody().getPosition().x +
                Constants.GROUND_WIDTH_INIT + pitLength, Constants.GROUND_Y, 0f);


        startX = secondGround.getBody().getPosition().x + Constants.GROUND_WIDTH_INIT /2 + pitLength;
    }

    private void placingColumns(Array<Columns> columns, Ground ground) {
        for (int i=0; i<Constants.COLUMNS_QUANTITY_INIT; i++) {
            float x = ground.getBody().getPosition().x + Constants.GROUND_WIDTH_INIT /2
                    + Constants.COLUMNS_PIT_INIT * (i + 1) + Constants.COLUMNS_WIDTH_INIT *i;
            columns.get(i).getBody().setTransform(x + Constants.COLUMNS_WIDTH_INIT / 2, Constants.COLUMNS_Y, 0);
            columns.get(i).getBody().setActive(true);
            stage.addActor(columns.get(i));
        }
    }

    private void selectLandscape() {

        pitLength = 0;

        priorityGround = ((rand.nextInt(mLevelModel.mPriorityGround + 1) + 1)*(int)Math.atan(mLevelModel.mPriorityGround+1));
        priorityPit = (rand.nextInt(mLevelModel.mPriorityPit + 1) +1)*(int)Math.atan(mLevelModel.mPriorityPit+1);
        priorityColumns = (rand.nextInt(mLevelModel.mPriorityColumns + 1) + 1)*(int)Math.atan(mLevelModel.mPriorityColumns+1);

        if (priorityGround>priorityPit&&priorityGround>priorityColumns) {
            pit = false;
            columns = false;
            pitLength = 0;
        } else if (priorityColumns>priorityPit&&priorityColumns>priorityGround) {
            columns = true;
            pit = false;
            pitLength = Constants.COLUMNS_QUANTITY_INIT
                    *(Constants.COLUMNS_WIDTH_INIT + Constants.COLUMNS_PIT_INIT) + Constants.COLUMNS_PIT_INIT;
        } else {
            pit = true;
            columns = false;
            pitLength = Constants.GROUND_PIT_INIT;
        }
    }

    public void setColumns(Array<Columns> columns1, Array<Columns> columns2) {
        mColumns1 = columns1;
        mColumns2 = columns2;
    }

}
