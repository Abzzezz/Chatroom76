/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.engine.utils.math;

import java.util.Random;

/**
 * Util for simple math tasks
 */
public class MathUtil {

    public static float abs(final float num) {
        return (num < 0) ? 0 - num : num;
    }

    public static double abs(final double num) {
        return (num < 0) ? 0 - num : num;
    }

    public static float nabs(final float num) {
        return (abs(num) * -1.0F);
    }

    public static double nabs(final double num) {
        return (abs(num) * -1.0);
    }

    public static double sinForCircle(float num, int radius) {
        return Math.sin(num * Math.PI / 180) * radius;
    }

    public static double cosForCircle(float num, int radius) {
        return Math.cos(num * Math.PI / 180) * radius;
    }

    public static int getBiggest(int num1, int num2) {
        return Math.max(num1, num2);
    }

    public static int getLowest(int num1, int num2) {
        return Math.min(num1, num2);
    }

    public static int getDifference(int num1, int num2) {
        return getBiggest(num1, num2) - getLowest(num1, num2);
    }

    public static int getRandomInt(int bound) {
        return new Random().nextInt(bound);
    }

}
