package gui;

import core.AbstractSystem;
import core.Keys;
import core.Main;
import core.MouseInput;
import graphics.RenderManagerComponent2D;
import grid.GridComponent;
import grid.Tile;
import grid.World;
import org.lwjgl.input.Keyboard;
import rounds.RoundSystem;
import util.Vec2;

public class PlayerUISystem extends AbstractSystem {

    private PlayerUIComponent puic;

    public PlayerUISystem(PlayerUIComponent puic) {
        this.puic = puic;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void update() {
        RenderManagerComponent2D rmc = Main.gameManager.rmc;
        //Move view
        if (Main.gameManager.elc.getEntity(World.class).getSystem(RoundSystem.class).anim == null) {
            double buffer = 50;
            double speed = 10;
            if (Keys.isDown(Keyboard.KEY_W) || !puic.mouseOverUI && MouseInput.mouseScreen().y > rmc.viewSize.y - buffer) {
                rmc.viewPos = rmc.viewPos.setY(rmc.viewPos.y + speed);
            }
            if (Keys.isDown(Keyboard.KEY_A) || !puic.mouseOverUI && MouseInput.mouseScreen().x < buffer) {
                rmc.viewPos = rmc.viewPos.setX(rmc.viewPos.x - speed);
            }
            if (Keys.isDown(Keyboard.KEY_S) || !puic.mouseOverUI && MouseInput.mouseScreen().y < buffer) {
                rmc.viewPos = rmc.viewPos.setY(rmc.viewPos.y - speed);
            }
            if (Keys.isDown(Keyboard.KEY_D) || !puic.mouseOverUI && MouseInput.mouseScreen().x > rmc.viewSize.x - buffer) {
                rmc.viewPos = rmc.viewPos.setX(rmc.viewPos.x + speed);
            }
        }
        if (rmc.viewPos.x < 0) {
            rmc.viewPos = rmc.viewPos.setX(0);
        }
        if (rmc.viewPos.y < 0) {
            rmc.viewPos = rmc.viewPos.setY(0);
        }
        GridComponent gc = Main.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class);
        if (rmc.viewPos.x > gc.width * Tile.SIZE) {
            rmc.viewPos = rmc.viewPos.setX(gc.width * Tile.SIZE);
        }
        if (rmc.viewPos.y > gc.height * Tile.SIZE) {
            rmc.viewPos = rmc.viewPos.setY(gc.height * Tile.SIZE);
        }
        //UI
        puic.mouseOverUI = false;
        if (puic.selected == null) {
            return;
        }
        Vec2 pos = new Vec2(0, rmc.viewSize.y);
        for (UIItem i : puic.selected.children) {
            i.pos = pos;
            i.draw();
            if (i.selected()) {
                puic.mouseOverUI = true;
                if (MouseInput.isPressed(0)) {
                    i.onClick();
                }
            }
            pos = pos.add(i.size.setX(0));
        }
    }
}
