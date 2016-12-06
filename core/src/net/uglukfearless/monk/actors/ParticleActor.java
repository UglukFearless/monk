package net.uglukfearless.monk.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Ugluk on 03.12.2016.
 */

public class ParticleActor extends Actor {

    private ParticleEffect mParticle;

    public ParticleActor(ParticleEffect particleEffect) {
        this.mParticle = particleEffect;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        mParticle.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        mParticle.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        mParticle.setPosition(x,y);
    }

    public void start() {
        mParticle.start();
    }

    public void setNewBounds(float viewport_x, float viewport_y) {
        mParticle.getEmitters().get(0).getSpawnWidth().setHigh(viewport_x, viewport_x);
    }
}
