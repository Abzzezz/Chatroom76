/*
 * @author Roman
 * Last modified: 04.01.21, 20:11 by kursc
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

public class Cubic extends Animation {

    @Override
    public float easeIn(float t, float b, float c, float d) {
        return c * (t /= d) * t * t + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeOut(float t, float b, float c, float d) {
        return c * ((t = t / d - 1) * t * t + 1) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        if ((t /= d / 2) < 1) return c / 2 * t * t * t + b;
        return c / 2 * ((t -= 2) * t * t + 2) + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

}
