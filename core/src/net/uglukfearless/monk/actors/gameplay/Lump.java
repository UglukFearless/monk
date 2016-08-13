package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.box2d.LumpUserData;
import net.uglukfearless.monk.box2d.UserData;

/**
 * Created by Ugluk on 02.06.2016.
 */
public class Lump extends net.uglukfearless.monk.actors.gameplay.GameActor {

    private float runTime;

    public Lump(Body body) {
        super(body);
    }

    @Override
    public void act(float delta) {

        super.act(delta);

        runTime += delta;

        if (runTime>0.5f&&body.getLinearVelocity().x>-10) {
                body.setLinearVelocity(body.getLinearVelocity().add(-0.5f, 0));
        }

        if (body.getPosition().y<-5f||body.getPosition().x<-5f) {
            ((UserData)body.getUserData()).setDestroy(true);
            this.remove();
        }

    }

    @Override
    public LumpUserData getUserData() {
        return (LumpUserData)body.getUserData();
    }
}
