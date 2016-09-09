package net.uglukfearless.monk.utils.gameplay;

/**
 * Created by Ugluk on 06.09.2016.
 */
public class Situation {

    public boolean stop = false;
    public boolean stopFly = false;
    public boolean shoot = false;
    public boolean strike = false;
    public boolean jump = false;

    public void reset() {
        stop = false;
        stopFly = false;
        shoot = false;
        strike = false;
        jump = false;
    }
}
