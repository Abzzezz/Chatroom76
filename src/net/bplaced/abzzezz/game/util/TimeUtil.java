/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.util;

public class TimeUtil {

    private long time;

    public boolean isTimeOver(long timeOver) {
        return getSystemTime() - time >= timeOver;
    }

    public void reset() {
        time = getSystemTime();
    }

    public long getSystemTime() {
        return System.currentTimeMillis();
    }
}
