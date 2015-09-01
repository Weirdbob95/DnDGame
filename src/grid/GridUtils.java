package grid;

import creature.Creature;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class GridUtils {

    public static GridComponent grid;

    public static ArrayList<Square> all() {
        ArrayList<Square> r = new ArrayList();
        for (Square[] sa : grid.tileGrid) {
            r.addAll(Arrays.asList(sa));
        }
        return r;
    }

    public static ArrayList<Square> area(Square pos, int size) {
        ArrayList<Square> r = new ArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square s = grid.tileAt(pos.x + i, pos.y + j);
                if (s != null) {
                    r.add(s);
                }
            }
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

    public static int distance(Square start, ArrayList<Square> path) {
        int r = 0;
        boolean extraDiag = false;
        for (int i = 0; i < path.size(); i++) {
            Square prev = (i == 0 ? start : path.get(i - 1));
            Square now = path.get(i);
            r += 5;
            if (prev.x != now.x && prev.y != now.y) {
                if (extraDiag) {
                    r += 5;
                }
                extraDiag = !extraDiag;
            }
        }
        return r;
    }

    public static boolean isOpen(Square pos, int size, Creature ignore) {
        for (Square test : area(pos, size)) {
            if (test.isWall) {
                return false;
            }
            if (test.creature != null && test.creature != ignore) {
                return false;
            }
        }
        return true;
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

    public static int minDistance(Creature c1, Creature c2) {
        return minDistance(c1.glc.occupied, c2.glc.occupied);
    }
}
