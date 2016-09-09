package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.utils.sort.Sortable;

/**
 * Created by Ugluk on 18.05.2016.
 */
public abstract class GameActor extends Actor {

    protected Body body;
    protected UserData userData;


    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getUserData()==null) {
            remove();
        }
    }


    public abstract UserData getUserData();

    public Body getBody() {
        return body;
    }
}
