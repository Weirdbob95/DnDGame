package util;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public abstract class Util {

    public static FloatBuffer floatBuffer(double... vals) {
        FloatBuffer r = BufferUtils.createFloatBuffer(vals.length);
        for (double d : vals) {
            r.put((float) d);
        }
        r.flip();
        return r;
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
