package gui;

import core.AbstractSystem;
import graphics.Graphics2D;
import grid.Tile;
import util.Color4d;
import util.Vec2;

public class PlayerOverlaySystem extends AbstractSystem {

    private PlayerUIComponent puic;

    public PlayerOverlaySystem(PlayerUIComponent puic) {
        this.puic = puic;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        for (Tile t : puic.coloredTiles) {
            Graphics2D.fillRect(t.LL(), new Vec2(1, 1).multiply(Tile.SIZE), Color4d.GREEN.setA(.5));
        }
        puic.coloredTiles.clear();
    }
}
