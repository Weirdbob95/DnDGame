package queries;

import core.Core;
import core.MouseInput;
import creature.Creature;
import grid.GridComponent;
import grid.GridUtils;
import grid.Square;
import grid.World;
import java.util.ArrayList;
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
    public boolean penetratesWalls;
    public Creature creature;
    public ArrayList<Square> path;
    //public ArrayList<OverlayCircle> circles;

    public PathQuery(String desc, Square start, int range, int width, boolean penetratesWalls, Creature creature) {
        this.desc = desc;
        this.start = start;
        this.range = range;
        this.width = width;
        this.penetratesWalls = penetratesWalls;
        this.creature = creature;
    }

    @Override
    public void createUI() {
        path = new ArrayList();
        //circles = new ArrayList();
        updateUI();
    }

    public int distance() {
        int r = 0;
        boolean extraDiag = false;
        for (int i = 0; i < path.size(); i++) {
            Square prev;
            if (i == 0) {
                prev = start;
            } else {
                prev = path.get(i - 1);
            }
            Square now = path.get(i);
            r += 5;
            if ((prev.x - now.x) * (prev.y - now.y) != 0) {
                if (extraDiag) {
                    r += 5;
                }
                extraDiag = !extraDiag;
            }
        }
        return r;
    }

    private boolean isOpen(Square s) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Square test = Core.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class).tileAt(s.x + i, s.y + j);
                if (test == null) {
                    return false;
                }
                if (test.isWall && !penetratesWalls) {
                    return false;
                }
                if (test.creature != null && test.creature != creature && !penetratesWalls) {
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<Square> options() {
        ArrayList<Square> r = new ArrayList();
        for (Square s : GridUtils.all()) {
            Square end = start;
            if (!path.isEmpty()) {
                end = path.get(path.size() - 1);
            }
            if (GridUtils.distance(s, end) == 5) {
                if (isOpen(s)) {
                    path.add(s);
                    if (distance() <= range) {
                        r.add(s);
                    }
                    path.remove(path.size() - 1);
                }
            }

        }
        return r;
    }

    private void updateUI() {
        puic.root.children.clear();
        new UIText(puic.root, desc);
        if (path.isEmpty()) {
            new UIChooseButton(puic.root, "Cancel", this);
        } else {
            new UIChooseButton(puic.root, "Choose", this);
        }
        puic.overlayItems.clear();
        final PathQuery thus = this;
        final Vec2 offset = new Vec2(width * Square.SIZE / 2., width * Square.SIZE / 2.);
        for (int i = 0; i < path.size(); i++) {
            Square prev;
            if (i == 0) {
                prev = start;
            } else {
                prev = path.get(i - 1);
            }
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
