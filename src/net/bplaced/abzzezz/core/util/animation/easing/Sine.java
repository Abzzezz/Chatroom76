/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.animation.easing;

import net.bplaced.abzzezz.core.util.animation.Animation;

public class Sine extends Animation {
    @Override

    public float easeIn(float t, float b, float c, float d) {
        return -c * (float) Math.cos(t / d * (Math.PI / 2)) + c + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override

    public float easeOut(float t, float b, float c, float d) {
        return c * (float) Math.sin(t / d * (Math.PI / 2)) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override

    public float easeInOut(float t, float b, float c, float d) {
        return -c / 2 * ((float) Math.cos(Math.PI * t / d) - 1) + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

}
