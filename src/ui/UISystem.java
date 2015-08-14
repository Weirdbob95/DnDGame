package ui;

import animations.Animation;
import animations.ViewMoveAnimation;
import core.AbstractSystem;
import core.Keys;
import core.Main;
import core.MouseInput;
import graphics.Graphics2D;
import graphics.RenderManagerComponent2D;
import grid.GridComponent;
import grid.Square;
import grid.World;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import util.Color4d;
import util.Vec2;

public class UISystem extends AbstractSystem {

    private PlayerUIComponent puic;

    public UISystem(PlayerUIComponent puic) {
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
        if (!(Animation.current instanceof ViewMoveAnimation)) {
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
        if (rmc.viewPos.x > gc.width * Square.SIZE) {
            rmc.viewPos = rmc.viewPos.setX(gc.width * Square.SIZE);
        }
        if (rmc.viewPos.y > gc.height * Square.SIZE) {
            rmc.viewPos = rmc.viewPos.setY(gc.height * Square.SIZE);
        }
        //UI
        puic.mouseOverUI = false;
        if (puic.root == null) {
            return;
        }
        UIItem root = puic.root;
        int border = 8;
        Vec2 pos = new Vec2(border, rmc.viewSize.y - border);
        for (UIItem i : root.children) {
            i.pos = pos;
            pos = pos.add(i.size.setX(0));
        }
        Graphics2D.fillRect(new Vec2(0, rmc.viewSize.y), pos.add(new Vec2(300 + border, -rmc.viewSize.y - border)), new Color4d(.1, .5, 1));
        for (UIItem i : (ArrayList<UIItem>) root.children.clone()) {
            i.draw();
            if (i.selected()) {
                puic.mouseOverUI = true;
                i.onMouseOver();
                if (MouseInput.isPressed(0)) {
                    i.onClick();
                }
            }
        }
    }
}
