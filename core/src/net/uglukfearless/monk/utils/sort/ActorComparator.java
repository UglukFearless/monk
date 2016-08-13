package net.uglukfearless.monk.utils.sort;

import java.util.Comparator;

/**
 * Created by Ugluk on 05.08.2016.
 */
public class ActorComparator implements Comparator<Sortable> {
    @Override
    public int compare(Sortable actor1, Sortable actor2) {
        return (actor2.getZLayout() - actor1.getZLayout()) > 0 ? 1 : -1;
    }
}
