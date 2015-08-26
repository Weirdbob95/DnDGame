package queries;

import grid.GridUtils;
import grid.Square;
import java.util.ArrayList;
import overlay.OverlaySquare;
import ui.UIChooseButton;
import ui.UIText;
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
    public boolean createUI() {
        for (Square s : GridUtils.all()) {
            if (range < 0 || GridUtils.minDistance(s, sources) <= range) {
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
            }
        }
        new UIText(puic.root, desc);
        new UIChooseButton(puic.root, "Cancel", this);
        return true;
    }
}
