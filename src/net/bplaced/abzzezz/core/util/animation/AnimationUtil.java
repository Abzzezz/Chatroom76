/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.animation;

import net.bplaced.abzzezz.core.util.DeltaTime;
import net.bplaced.abzzezz.core.util.math.MathUtil;

/**
 * @author: trey & Abzzezz
 * @created on: 22.07.2018 16:25:46
 */

/**
 * Used to calculate animations
 */

public class AnimationUtil {

    private final Animation animation;
    public float velocity, oppositeVelocity, min, max, step;
    public float time;
    public boolean reversed;
    public String type;

    public AnimationUtil(final Animation animation, final float velocity, final float min, final float max, final float step, final boolean reversed) {
        this.velocity = velocity;
        this.oppositeVelocity = (MathUtil.nabs(velocity));
        this.min = min;
        this.max = max;
        this.reversed = reversed;
        this.animation = animation;
        this.step = step;
        if (reversed)
            time = max;
        else
            time = min;
    }

    public void animate() {
        if (reversed) {
            if (time > min) {
                time -= step * (DeltaTime.deltaTime / 10F);
            }
        } else {
            if (time < max) {
                time += step * (DeltaTime.deltaTime / 10F);
            }
        }

        this.velocity = animation.easeInOut(time, min, max, max);
        this.oppositeVelocity = MathUtil.nabs(velocity);
    }

    public void reset(boolean animated) {
        if (animated) {
            if (reversed) {
                if (time < max) {
                    time += step * (DeltaTime.deltaTime / 10F);
                }
            } else {
                if (time > min) {
                    time -= step * (DeltaTime.deltaTime / 10F);
                }
            }
            velocity = animation.easeInOut(time, min, max, max);
            oppositeVelocity = (MathUtil.nabs(velocity));
        } else {
            if (reversed) time = max;
            else time = min;

            velocity = min;
            oppositeVelocity = (MathUtil.nabs(velocity));
        }
    }

    public int getInt() {
        return (int) velocity;
    }

    public float getFloat() {
        return velocity;
    }

    public double getDouble() {
        return velocity;
    }
}
