package net.uglukfearless.monk.utils.gameplay;

import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Created by Ugluk on 03.06.2016.
 */
public class FilterHandler {

    public static Filter getFilter(short category, short mask) {
        Filter f = new Filter();
        f.categoryBits = category;
        f.maskBits = mask;

        return f;
    }
}
