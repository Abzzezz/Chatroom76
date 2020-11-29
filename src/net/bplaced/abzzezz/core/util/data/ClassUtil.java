/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.data;

import java.lang.reflect.Method;

/**
 * Util to invoke get methods for now
 *
 * @version 1.0
 */
public class ClassUtil {

    /**
     * Get method
     *
     * @param c
     * @param method
     * @param params
     * @return
     */
    public static Method getMethod(Class c, String method, Class... params) throws NoSuchMethodException {
        return c.getDeclaredMethod(method, params);

    }
}