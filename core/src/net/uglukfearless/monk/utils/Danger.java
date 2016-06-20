package net.uglukfearless.monk.utils;

import net.uglukfearless.monk.stages.GameStage;

/**
 * Created by Ugluk on 11.06.2016.
 */
public interface Danger {

    public short getCategoryBit();

    public short[][] getProhibitionsMap();

    public boolean checkResolve(short codeOfForbidden);


}
