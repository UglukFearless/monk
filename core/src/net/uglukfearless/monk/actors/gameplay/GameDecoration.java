package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.utils.sort.Sortable;

/**
 * Created by Ugluk on 05.08.2016.
 */
public abstract class GameDecoration extends Actor implements Sortable {

    protected int zLayout = 0;

    @Override
    public void setZLayout(int z) {
        zLayout = z;
    }

    @Override
    public int getZLayout() {
        return zLayout;
    }
}
