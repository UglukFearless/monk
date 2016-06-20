package net.uglukfearless.monk.listeners;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.BodyUtils;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.utils.ScoreCounter;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class GameContactListener implements ContactListener {

    private GameStage stage;

    public GameContactListener(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a)&&(BodyUtils.bodyIsEnemy(b)&&!((EnemyUserData)b.getUserData()).isDead()))) {
            //Обработка столкновения монаха с противником
            if (((RunnerUserData) a.getUserData()).isDead()) {
                b.setFixedRotation(false);
                ((EnemyUserData) b.getUserData()).setDoll(true);
            }
            stage.getRunner().hit();
        } else if (((BodyUtils.bodyIsEnemy(a)&&!((EnemyUserData)a.getUserData()).isDead())&&BodyUtils.bodyIsRunner(b))) {
            if (((RunnerUserData) b.getUserData()).isDead()) {
                a.setFixedRotation(false);
                ((EnemyUserData) a.getUserData()).setDoll(true);
            }
            stage.getRunner().hit();
        } else if ((BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsGround(b))
                ||(BodyUtils.bodyIsGround(a)&&BodyUtils.bodyIsRunner(b))) {
            //Обработка столкновений монаха и земли
            stage.getRunner().landed();
        } else if (BodyUtils.bodyIsRunnerStrike(a)&&BodyUtils.bodyIsEnemy(b)) {
            //Обработка столкновений противников с ударом монаха

            if (!((EnemyUserData)b.getUserData()).isDead())
                ScoreCounter.increaseScore(1);

            ((EnemyUserData)b.getUserData()).setDead(true);
            b.setFixedRotation(false);
            b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_DEAD);
            if (b.getGravityScale()==0) {
                b.setGravityScale(10);
            }
        } else if (BodyUtils.bodyIsRunnerStrike(b)&&BodyUtils.bodyIsEnemy(a)) {

            if (!((EnemyUserData)a.getUserData()).isDead())
                ScoreCounter.increaseScore(1);

            ((EnemyUserData)a.getUserData()).setDead(true);
            a.setFixedRotation(false);
            a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_DEAD);
            if (a.getGravityScale()==0) {
                a.setGravityScale(10);
            }


        } else if (BodyUtils.bodyIsLump(a)&&BodyUtils.bodyIsGround(b)) {
            //Обработка столкновений ошмётков со статическим фоном
            a.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
        } else if (BodyUtils.bodyIsLump(b)&&BodyUtils.bodyIsGround(a)) {
            b.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);

        } else if (BodyUtils.bodyIsObstacle(a)&&BodyUtils.bodyIsRunner(b)) {
            //Обработка столкновений монаха со статическим препятствием
                if (((b.getPosition().y - ((UserData)b.getUserData()).getHeight()/2)
                        >(a.getPosition().y + ((UserData)a.getUserData()).getHeight()/2) - 0.1f)
                        &&!((ObstacleUserData)a.getUserData()).isTrap()) {
                    stage.getRunner().landed();
            } else {
                    stage.getRunner().hit();
                    if (((ObstacleUserData)a.getUserData()).isBlades()) {
                        b.setLinearVelocity(a.getLinearVelocity().add(40,10));
                    }
                }
        } else if (BodyUtils.bodyIsRunner(a)&&BodyUtils.bodyIsObstacle(b)) {
            if ((a.getPosition().y -((UserData)a.getUserData()).getHeight()/2
                    >(b.getPosition().y + ((UserData)b.getUserData()).getHeight()/2)  - 0.1f)
                    &&!((ObstacleUserData)b.getUserData()).isTrap()){
                stage.getRunner().landed();
            } else {
                stage.getRunner().hit();
                if (((ObstacleUserData)b.getUserData()).isBlades()) {
                    a.setLinearVelocity(a.getLinearVelocity().add(40, 10));
                }
            }
        } else if (BodyUtils.bodyIsRunnerStrike(a)&&BodyUtils.bodyIsObstacle(b)) {
            //Обработка столкновений препятствий с ударом монаха
            if (!((ObstacleUserData)b.getUserData()).isArmour()) {

                if (!((ObstacleUserData)b.getUserData()).isDead())
                    ScoreCounter.increaseScore(1);

                ((ObstacleUserData)b.getUserData()).setDead(true);
            }
        } else if (BodyUtils.bodyIsRunnerStrike(b)&&BodyUtils.bodyIsObstacle(a)) {
            if (!((ObstacleUserData)a.getUserData()).isArmour()) {
                if (!((ObstacleUserData)a.getUserData()).isDead())
                    ScoreCounter.increaseScore(1);

                ((ObstacleUserData)a.getUserData()).setDead(true);

            }

        } else if (BodyUtils.bodyIsEnemy(a)&&BodyUtils.bodyIsObstacle(b)) {
            //Обработка столкновения противника с ловушками
            if (((ObstacleUserData)b.getUserData()).isTrap()) {
                ((EnemyUserData)a.getUserData()).setDead(true);
                a.setFixedRotation(false);
                if (((ObstacleUserData)b.getUserData()).isBlades()) {
                    a.setLinearVelocity(a.getLinearVelocity().add(-40, 10));
                }
            }
        } else if (BodyUtils.bodyIsObstacle(a)&&BodyUtils.bodyIsEnemy(b)) {
            if (((ObstacleUserData)a.getUserData()).isTrap()) {
                ((EnemyUserData)b.getUserData()).setDead(true);
                b.setFixedRotation(false);
                if (((ObstacleUserData)a.getUserData()).isBlades()) {
                    b.setLinearVelocity(b.getLinearVelocity().add(-40, 10));
                }
            }


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
