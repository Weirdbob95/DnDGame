package util;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public abstract class Util {

    public static String classToName(Object o) {
        return o.getClass().getSimpleName().replaceAll("___", "'").replaceAll("__", "-").replaceAll("_", " ");
    }

    public static FloatBuffer floatBuffer(double... vals) {
        FloatBuffer r = BufferUtils.createFloatBuffer(vals.length);
        for (double d : vals) {
            r.put((float) d);
        }
        r.flip();
        return r;
    }

    public static Class nameToClass(String name) {
        try {
            return Class.forName(name.replaceAll(" ", "_").replaceAll("-", "__").replaceAll("'", "___"));
        } catch (ClassNotFoundException ex) {
            Log.error(ex);
            return null;
        }
    }

    public static Class nameToClass(String pckg, String name) {
        try {
            return Class.forName(pckg + "." + name.replaceAll(" ", "_").replaceAll("-", "__").replaceAll("'", "___"));
        } catch (ClassNotFoundException ex) {
            Log.error(ex);
            return null;
        }
    }

    public static int sign(double d) {
        if (d == 0) {
            return 0;
        }
        if (d > 0) {
            return 1;
        }
        return -1;
    }
}
