package queries;

import core.MouseInput;
import grid.GridUtils;
import grid.Square;
import java.util.ArrayList;
import java.util.function.BiPredicate;
import overlay.OverlayCircle;
import overlay.OverlayLine;
import ui.UIChooseButton;
import ui.UIText;
import util.Color4d;
import util.Vec2;

public class PathQuery extends Query {

    public String desc;
    public Square start;
    public int range;
    public int width;
    public BiPredicate<Square, Square> allowFunc;
    public ArrayList<Square> path;

    public PathQuery(String desc, Square start, int range, int width, BiPredicate<Square, Square> allowFunc) {
        this.desc = desc;
        this.start = start;
        this.range = range;
        this.width = width;
        this.allowFunc = allowFunc;
    }

    @Override
    public boolean createUI() {
        if (range == 0) {
            return false;
        }
        path = new ArrayList();
        updateUI();
        return true;
    }

    private ArrayList<Square> options() {
        ArrayList<Square> r = new ArrayList();
        GridUtils.all().forEach(s -> {
            Square end = start;
            if (!path.isEmpty()) {
                end = path.get(path.size() - 1);
            }
            if (GridUtils.distance(s, end) == 5) {

                if (allowFunc.test(end, s)) {

                    path.add(s);
                    if (GridUtils.distance(start, path) <= range) {
                        r.add(s);
                    }
                    path.remove(path.size() - 1);
                }
            }
        });
        return r;
    }

    private void updateUI() {
        puic.root.children.clear();
        new UIText(puic.root, desc);
        new UIChooseButton(puic.root, path.isEmpty() ? "Cancel" : "Choose", this);
        puic.overlayItems.clear();
        final PathQuery thus = this;
        final Vec2 offset = new Vec2(width * Square.SIZE / 2., width * Square.SIZE / 2.);
        for (int i = 0; i < path.size(); i++) {
            Square prev = (i == 0 ? start : path.get(i - 1));
            Square now = path.get(i);
            new OverlayLine(prev.LL().add(offset), now.LL().add(offset), new Color4d(1, 1, 0), 2);
        }
        for (Square s : options()) {
            new OverlayCircle(s.LL().add(offset), new Vec2(Square.SIZE / 4, Square.SIZE / 4), Color4d.GREEN, true) {
                @Override
                public void onMouseOver() {
                    if (MouseInput.isDown(0)) {
                        thus.path.add(Square.tileAt(pos.subtract(offset)));
                        updateUI();
                    }
                }
            };
        }
        if (!path.isEmpty()) {
            new OverlayCircle(path.get(path.size() - 1).LL().add(offset), new Vec2(Square.SIZE / 4, Square.SIZE / 4), new Color4d(1, 1, 0), true) {
                @Override
                public void onMouseOver() {
                    if (MouseInput.isDown(1)) {
                        thus.path.remove(path.get(path.size() - 1));
                        updateUI();
                    }
                }
            };
        }
    }
}
