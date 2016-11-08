package net.uglukfearless.monk.utils.gameplay;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Ugluk on 17.10.2016.
 */
public class CircularList<T>  extends Array<T> {

    private int currentIndex;


    public void resetIndex() {
        currentIndex = 0;
    }

    public void setIndex(int index) {
        if (index>=size) {
            index = size -1;
        }
        currentIndex = index;
    }

    public T getNext() {

        if (currentIndex==this.size) {
            currentIndex=0;
        }

        T object = this.get(currentIndex);
        currentIndex++;

        return object;
    }

}
