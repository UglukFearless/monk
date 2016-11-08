package net.uglukfearless.monk.listeners;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import net.uglukfearless.monk.actors.gameplay.Enemy;
import net.uglukfearless.monk.box2d.ArmourUserData;
import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.box2d.RunnerStrikeUserData;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.ShellUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.utils.file.ScoreCounter;

import java.util.Random;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class GameContactListener implements ContactListener {

    private GameStage stage;
    private Random mRandom;

    public GameContactListener(GameStage stage) {
        this.stage = stage;
        mRandom = new Random();
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (((BodyUtils.bodyIsObstacle(a)||BodyUtils.bodyIsObstacle(b))
                ||(BodyUtils.bodyIsEnemy(a)||BodyUtils.bodyIsEnemy(b)))
                &&BodyUtils.bodyIsGround(a)||BodyUtils.bodyIsGround(b)) {
            return;
        } else if ((BodyUtils.bodyIsRunner(a)&&(BodyUtils.bodyIsEnemy(b)&&!((EnemyUserData)b.getUserData()).isDead()))) {
            //Обработка столкновения монаха с противником
            if (((RunnerUserData) a.getUserData()).isDead()) {
                b.setFixedRotation(false);
                ((EnemyUserData) b.getUserData()).setDoll(true);
            } else {
                stage.getRunner().hit(((EnemyUserData) b.getUserData()).getKEY());
            }

        } else if (((BodyUtils.bodyIsEnemy(a)&&!((EnemyUserData)a.getUserData()).isDead())&&BodyUtils.bodyIsRunner(b))) {
            if (((RunnerUserData) b.getUserData()).isDead()) {
                a.setFixedRotation(false);
                ((EnemyUserData) a.getUserData()).setDoll(true);
            } else {
                stage.getRunner().hit(((EnemyUserData) a.getUserData()).getKEY());
            }

        } else if ((BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsGround(b))
                ||(BodyUtils.bodyIsGround(a)&&BodyUtils.bodyIsRunner(b))
                ||(BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsColumns(b))
                ||(BodyUtils.bodyIsColumns(a)&&BodyUtils.bodyIsRunner(b))) {
            //Обработка столкновений монаха и земли|столбов
            stage.getRunner().landed();
        } else if (BodyUtils.bodyIsRunnerStrike(a)&&BodyUtils.bodyIsEnemy(b)) {
            //Обработка столкновений противников с ударом монаха

            if (!((EnemyUserData)b.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)b.getUserData()).setDead(true);
            }

            stage.getRunner().beatBody(b, contact);

            if (((RunnerStrikeUserData)a.getUserData()).isShell()) {
                ((RunnerStrikeUserData)a.getUserData()).setDead(true);
                a.setGravityScale(3);
                a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }
        } else if (BodyUtils.bodyIsRunnerStrike(b)&&BodyUtils.bodyIsEnemy(a)) {

            if (!((EnemyUserData)a.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)a.getUserData()).setDead(true);
            }

            stage.getRunner().beatBody(a, contact);

            if (((RunnerStrikeUserData)b.getUserData()).isShell()) {
                ((RunnerStrikeUserData)b.getUserData()).setDead(true);
                b.setGravityScale(3);
                b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }


        } else if (BodyUtils.bodyIsLump(a)&&BodyUtils.bodyIsGround(b)) {
            //Обработка столкновений ошмётков со статическим фоном
            a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
        } else if (BodyUtils.bodyIsLump(b)&&BodyUtils.bodyIsGround(a)) {
            b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);

        } else if(BodyUtils.bodyIsShell(a)){
            //обработка столкновения снаряда с другими телами
            shellContact(a, b, contact);

        } else if(BodyUtils.bodyIsShell(b)) {

            shellContact(b,a, contact);

        } else if (BodyUtils.bodyIsObstacle(a)&&BodyUtils.bodyIsRunner(b)) {
            //Обработка столкновений монаха со статическим препятствием
                if (((b.getPosition().y - ((UserData)b.getUserData()).getHeight()/2)
                        >(a.getPosition().y + ((UserData)a.getUserData()).getHeight()/2) - 0.1f)
                        &&!((ObstacleUserData)a.getUserData()).isTrap()) {
                    stage.getRunner().landed();
            } else if (!((ObstacleUserData)a.getUserData()).isDead()) {
                    stage.getRunner().hit(((ObstacleUserData)a.getUserData()).getKEY());

                    ((ObstacleUserData) a.getUserData()).hitExecution(b,false);
                }
        } else if (BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsObstacle(b)) {
            if ((a.getPosition().y -((UserData)a.getUserData()).getHeight()/2
                    >(b.getPosition().y + ((UserData)b.getUserData()).getHeight()/2)  - 0.1f)
                    &&!((ObstacleUserData)b.getUserData()).isTrap()){
                stage.getRunner().landed();
            } else if (!((ObstacleUserData)b.getUserData()).isDead()){
                stage.getRunner().hit(((ObstacleUserData)b.getUserData()).getKEY());

                ((ObstacleUserData) b.getUserData()).hitExecution(a,false);
            }
        } else if (BodyUtils.bodyIsRunnerStrike(a)&&BodyUtils.bodyIsObstacle(b)) {
            //Обработка столкновений препятствий с ударом монаха

                if (!((ObstacleUserData)b.getUserData()).isDead()
                        &&((!((ObstacleUserData)b.getUserData()).isTrap()
                        &&((RunnerStrikeUserData)a.getUserData()).isPiercing1())
                        ||((RunnerStrikeUserData)a.getUserData()).isPiercing2()
                        ||!((ObstacleUserData)b.getUserData()).isArmour())) {
                    ScoreCounter.increaseScore(1);
                    ScoreCounter.increaseDestroyed();
                    ((ObstacleUserData)b.getUserData()).setDead(true);
                    stage.getRunner().beatBody(b, contact);
                }



            if (((RunnerStrikeUserData)a.getUserData()).isShell()) {
                ((RunnerStrikeUserData)a.getUserData()).setDead(true);
                a.setGravityScale(3);
                a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }

        } else if (BodyUtils.bodyIsRunnerStrike(b)&&BodyUtils.bodyIsObstacle(a)) {

            if (!((ObstacleUserData)a.getUserData()).isDead()
                    &&(((!((ObstacleUserData)a.getUserData()).isTrap()
                    &&((RunnerStrikeUserData)b.getUserData()).isPiercing1())
                    ||((RunnerStrikeUserData)b.getUserData()).isPiercing2())
                    ||!((ObstacleUserData)a.getUserData()).isArmour())) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseDestroyed();
                ((ObstacleUserData)a.getUserData()).setDead(true);
                stage.getRunner().beatBody(a, contact);
            }


            if (((RunnerStrikeUserData)b.getUserData()).isShell()) {
                ((RunnerStrikeUserData)b.getUserData()).setDead(true);
                b.setGravityScale(3);
                b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }

        } else if (BodyUtils.bodyIsEnemy(a)&&BodyUtils.bodyIsObstacle(b)) {
            //Обработка столкновения противника с препятствиями
            if (((ObstacleUserData)b.getUserData()).isTrap()) {
                ((EnemyUserData)a.getUserData()).setDead(true);
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                a.setFixedRotation(false);
                ((ObstacleUserData) b.getUserData()).hitExecution(a, true);

            } else if (((UserData)a.getUserData()).isLaunched()
                    &&!((ObstacleUserData)b.getUserData()).isDead()
                    &&!((ObstacleUserData)b.getUserData()).isArmour()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseDestroyed();
                ((ObstacleUserData)b.getUserData()).setDead(true);
            } else if (((UserData)b.getUserData()).isLaunched()
                    &&!((EnemyUserData)a.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)a.getUserData()).setDead(true);
            }

        } else if (BodyUtils.bodyIsObstacle(a)&&BodyUtils.bodyIsEnemy(b)) {
            if (((ObstacleUserData)a.getUserData()).isTrap()) {
                ((EnemyUserData)b.getUserData()).setDead(true);
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                b.setFixedRotation(false);
                ((ObstacleUserData) a.getUserData()).hitExecution(b, true);

            } else if (((UserData)b.getUserData()).isLaunched()
                    &&!((ObstacleUserData)a.getUserData()).isDead()
                    &&!((ObstacleUserData)a.getUserData()).isArmour()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseDestroyed();
                ((ObstacleUserData)a.getUserData()).setDead(true);
            } else if (((UserData)a.getUserData()).isLaunched()
                    &&!((EnemyUserData)b.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)b.getUserData()).setDead(true);
            }

        } else if (BodyUtils.bodyIsEnemy(a)&&BodyUtils.bodyIsEnemy(b)) {
            //Обработка столкновения противника с противником
            if (((UserData)a.getUserData()).isLaunched()
                    &&!((EnemyUserData)b.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)b.getUserData()).setDead(true);
            } else if (((UserData)b.getUserData()).isLaunched()
                    &&!((EnemyUserData)a.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled();
                ((EnemyUserData)a.getUserData()).setDead(true);
            }

        } else if (BodyUtils.bodyIsObstacle(a)&&BodyUtils.bodyIsObstacle(b)) {
            //Обработка столкновения препятствия с препятствием
            if (((UserData)a.getUserData()).isLaunched()
                    &&!((ObstacleUserData)b.getUserData()).isTrap()
//                    &&!((ObstacleUserData)b.getUserData()).isArmour()
                    &&!((ObstacleUserData)b.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseDestroyed();
                ((ObstacleUserData)b.getUserData()).setDead(true);
            } else if (((UserData)b.getUserData()).isLaunched()
                    &&!((ObstacleUserData)a.getUserData()).isTrap()
//                    &&!((ObstacleUserData)a.getUserData()).isArmour()
                    &&!((ObstacleUserData)a.getUserData()).isDead()) {
                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseDestroyed();
                ((ObstacleUserData)a.getUserData()).setDead(true);
            }
        } else if (BodyUtils.bodyIsArmour(a)){
            armourContact(a,b, contact);
        } else if (BodyUtils.bodyIsArmour(b)){
            armourContact(b,a, contact);
        } else if (BodyUtils.bodyIsRunnerStrike(a)) {
            if (((RunnerStrikeUserData)a.getUserData()).isShell()) {
                ((RunnerStrikeUserData)a.getUserData()).setDead(true);
                a.setGravityScale(3);
                a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }
        } else if (BodyUtils.bodyIsRunnerStrike(b)) {
            if (((RunnerStrikeUserData)b.getUserData()).isShell()) {
                ((RunnerStrikeUserData)b.getUserData()).setDead(true);
                b.setGravityScale(3);
                b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            }
        } else if (BodyUtils.bodyIsBuddha(a)) {
            buddhaContact(b);
        } else if (BodyUtils.bodyIsBuddha(b)) {
            buddhaContact(a);
        }
    }

    private void armourContact(Body armour, Body another, Contact contact) {
        switch (((UserData)another.getUserData()).getUserDataType()) {
            case GROUND:
            case COLUMNS:
                break;
            case OBSTACLE:
                if (((armour.getPosition().y - ((UserData)armour.getUserData()).getHeight()/2f)
                        >(another.getPosition().y + ((UserData)another.getUserData()).getHeight()/2f) - 0.4f)
                        &&!((ObstacleUserData)another.getUserData()).isTrap()) {
                    stage.getRunner().landed();
                    stage.getRunner().getBody().setLinearVelocity(0,6f);
                } else {
                    if (!((ObstacleUserData)another.getUserData()).isDead()&&!((ArmourUserData)armour.getUserData()).isHit()) {
                        ScoreCounter.increaseScore(1);
                        ScoreCounter.increaseDestroyed();
                        ((ObstacleUserData)another.getUserData()).setDead(true);
                        another.setLinearVelocity(another.getLinearVelocity().add(mRandom.nextInt(10) + 20
                                , mRandom.nextInt(10)));
                    }
                    if (!((ArmourUserData)armour.getUserData()).isHit()) {
                        ((ArmourUserData)armour.getUserData()).setHit(true);
                    }
                }

                break;
            case ENEMY:
                if (!((ArmourUserData)armour.getUserData()).isHit()) {
                    ((ArmourUserData)armour.getUserData()).setHit(true);
                }
                break;
            case SHELL:
                if (!((ArmourUserData)armour.getUserData()).isHit()) {
                    another.setGravityScale(1);
                    if (stage.getRunner().isStrongBeat()) {
                        stage.getRunner().beatBody(another, contact);
                    }
                    another.setAngularVelocity(-1f*another.getAngularVelocity());
                    another.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE_FLIP);
                }
                if (!((ArmourUserData)armour.getUserData()).isHit()) {
                    ((ArmourUserData)armour.getUserData()).setHit(true);
                }
                break;
        }
    }

    private void buddhaContact(Body another) {

        switch (((UserData)another.getUserData()).getUserDataType()) {
            case GROUND:
            case COLUMNS:
            case RUNNER_STRIKE:
            case RUNNER:
                break;
            case OBSTACLE:
                if (!((ObstacleUserData)another.getUserData()).isDead()) {
                    ScoreCounter.increaseScore(1);
                    ScoreCounter.increaseDestroyed();
                    ((ObstacleUserData)another.getUserData()).setDead(true);
                    another.setLinearVelocity(another.getLinearVelocity().add(mRandom.nextInt(10) + 20
                            , mRandom.nextInt(10)));
                }
                break;
            case ENEMY:
                if (!((EnemyUserData)another.getUserData()).isDead()) {
                    ScoreCounter.increaseScore(1);
                    ScoreCounter.increaseKilled();
                    ((EnemyUserData)another.getUserData()).setDead(true);
                    another.setLinearVelocity(another.getLinearVelocity().add(mRandom.nextInt(10) + 20
                            , mRandom.nextInt(10)));
                }
                break;
        }
    }

    private void shellContact(Body shell, Body another, Contact contact) {

        switch (((UserData)another.getUserData()).getUserDataType()) {
            case GROUND:
            case COLUMNS:
                if (shell.getGravityScale()==3) {
                    shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_LUMP);
                    shell.setGravityScale(5);
                } else if (shell.getGravityScale()==5) {
                    shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
                } else {
                    shell.setGravityScale(3);
                }
                break;
            case OBSTACLE:
                if (shell.getGravityScale()==5) {
                    shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
                } else {
                    shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_LUMP);
                    shell.setGravityScale(5);
                }
                break;
            case SHELL:
                shell.setGravityScale(1);
                break;
            case RUNNER_STRIKE:
                shell.setGravityScale(1);
                if (stage.getRunner().isStrongBeat()) {
                    stage.getRunner().beatBody(shell, contact);
                }
                shell.setAngularVelocity(-1f*shell.getAngularVelocity());
                shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE_FLIP);
                break;
            case ENEMY:
                ((ShellUserData)shell.getUserData()).setDead(true);

                if (!((EnemyUserData)another.getUserData()).isDead()) {
                    ScoreCounter.increaseScore(1);
                    ScoreCounter.increaseKilled();
                }

                ((EnemyUserData)another.getUserData()).setDead(true);
                another.setFixedRotation(false);
                another.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_DEAD);
                if (another.getGravityScale()==0) {
                    another.setGravityScale(10);
                }
                break;
            case RUNNER:
                ((ShellUserData)shell.getUserData()).setDead(true);
                shell.setGravityScale(1);
                stage.getRunner().hit(((ShellUserData)shell.getUserData()).getKEY());
                break;
            case BUDDHA:
                shell.setGravityScale(1);
                shell.setAngularVelocity(-1f * shell.getAngularVelocity());
                shell.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE_FLIP);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (BodyUtils.bodyIsRunner(a)&&(BodyUtils.bodyIsEnemy(b)
                &&!((EnemyUserData)b.getUserData()).isDoll()
                &&!((EnemyUserData)b.getUserData()).isDead())) {
            //Обработка после столкновением монаха с противником
            b.setLinearVelocity(((EnemyUserData) b.getUserData()).getLinearVelocity());
            b.setFixedRotation(true);
        } else if (((BodyUtils.bodyIsEnemy(a)&&!((EnemyUserData)a.getUserData()).isDead())
                &&BodyUtils.bodyIsRunner(b))&&!((EnemyUserData)a.getUserData()).isDoll()) {
            a.setLinearVelocity(((EnemyUserData) a.getUserData()).getLinearVelocity());
            a.setFixedRotation(true);
        }
    }
}
