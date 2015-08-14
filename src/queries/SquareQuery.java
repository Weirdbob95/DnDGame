package queries;

import core.Main;
import grid.GridComponent;
import grid.Square;
import grid.World;
import ui.UIChooseButton;
import overlay.OverlaySquare;
import ui.UIText;
import java.util.ArrayList;
import util.Color4d;

public class SquareQuery extends Query {

    public String desc;
    public ArrayList<Square> sources;
    public int range;
    public boolean los;
    public Square response;
    public OverlaySquare selected;

    public SquareQuery(String desc, Square source, int range, boolean los) {
        this.desc = desc;
        sources = new ArrayList();
        sources.add(source);
        this.range = range;
        this.los = los;
    }

    public SquareQuery(String desc, ArrayList<Square> sources, int range, boolean los) {
        this.desc = desc;
        this.sources = sources;
        this.range = range;
        this.los = los;
    }

    @Override
    public void createUI() {
        //Vec2 offset = new Vec2(Square.SIZE * range / 5., Square.SIZE * range / 5.);
        //for (int i = Square.tileAt(source.subtract(offset)).x; i <= Square.tileAt(source.add(offset)).x; i++) {
        //    for (int j = Square.tileAt(source.subtract(offset)).y; j <= Square.tileAt(source.add(offset)).y; j++) {
        //        Square s = Main.gameManager.getComponent(GridComponent.class).tileAt(i, j);
        for (Square[] sa : Main.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class).tileGrid) {
            for (Square s : sa) {
                for (Square start : sources) {
                    //Log.print("Testing path from " + s + " to " + start);
                    //if (s != null) {
                    if (s.distanceTo(start) <= range) {
                        //Log.print("Path success");
                        //if (s.center().subtract(source).length() < Square.SIZE * range / 5.) {
                        final SquareQuery thus = this;
                        puic.overlayItems.add(new OverlaySquare(s, Color4d.GREEN.setA(.5)) {
                            @Override
                            public void onClick() {
                                if (selected != null) {
                                    selected.color = Color4d.GREEN.setA(.5);
                                } else {
                                    new UIChooseButton(puic.root, "Choose Square", thus) {
                                        @Override
                                        public void act() {
                                            response = selected.square;
                                        }
                                    };
                                }
                                selected = this;
                                color = new Color4d(1, .5, 0, .5);
                            }
                        });
                        //}
                        break;
                    }
                }
            }
        }
        new UIText(puic.root, desc);
        new UIChooseButton(puic.root, "Cancel", this);
    }
}
