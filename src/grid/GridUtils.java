package grid;

import core.Core;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class GridUtils {

    public static ArrayList<Square> all() {
        ArrayList<Square> r = new ArrayList();
        for (Square[] sa : Core.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class).tileGrid) {
            r.addAll(Arrays.asList(sa));
        }
        return r;
    }

    public static int distance(Square s1, Square s2) {
        int dx = Math.abs(s1.x - s2.x);
        int dy = Math.abs(s1.y - s2.y);
        int diag = Math.min(dx, dy);
        int straight = Math.max(dx, dy) - diag;
        if (diag % 2 == 0) {
            return diag / 2 * 15 + straight * 5;
        } else {
            return diag / 2 * 15 + 5 + straight * 5;
        }
    }

    public static int minDistance(Square s, ArrayList<Square> a) {
        int min = Integer.MAX_VALUE;
        for (Square test : a) {
            int d = distance(s, test);
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    public static int minDistance(ArrayList<Square> a1, ArrayList<Square> a2) {
        int min = Integer.MAX_VALUE;
        for (Square test : a1) {
            int d = minDistance(test, a2);
            if (d < min) {
                min = d;
            }
        }
        return min;
    }
}
