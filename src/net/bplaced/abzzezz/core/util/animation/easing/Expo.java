/*
 * @author Roman
 * Last modified: 04.01.21, 20:17 by kursc
 *
 *
 *
 */

/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.animation.easing;

import net.bplaced.abzzezz.core.util.animation.Animation;

public class Expo extends Animation {
    @Override

    public float easeIn(float t, float b, float c, float d) {
        return (t == 0) ? b : c * (float) Math.pow(2, 10 * (t / d - 1)) + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override

    public float easeOut(float t, float b, float c, float d) {
        return (t == d) ? b + c : c * (-(float) Math.pow(2, -10 * t / d) + 1) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        if (t == 0) return b;
        if (t == d) return b + c;
        if ((t /= d / 2) < 1) return c / 2 * (float) Math.pow(2, 10 * (t - 1)) + b;
        return c / 2 * (-(float) Math.pow(2, -10 * --t) + 2) + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

}
