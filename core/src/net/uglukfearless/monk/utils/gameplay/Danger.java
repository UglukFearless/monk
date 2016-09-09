package net.uglukfearless.monk.utils.gameplay;

import net.uglukfearless.monk.stages.GameStage;

/**
 * Created by Ugluk on 11.06.2016.
 */
public interface Danger {

    short getCategoryBit();

    short[][] getProhibitionsMap();

    boolean checkResolve(short codeOfForbidden);

    int getPriority();

    void calcPriority();

    int getCurrentPriority();

}
