package gui;

import core.Main;
import graphics.Graphics2D;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import util.Color4d;
import static util.Color4d.BLACK;

public class UIButton extends UIItem {

    public String text;

    public UIButton(UIItem parent, String text) {
        super(parent);
        this.text = text;
        size = size.setY(-Graphics2D.getTextHeight(text, "Default", (int) size.x));
    }

    @Override
    public void draw() {
        if (selected()) {
            Graphics2D.fillRect(pos, size, new Color4d(0, .3, .6));
        } else {
            Graphics2D.fillRect(pos, size, new Color4d(0, .5, 1));
        }
        GL11.glLineWidth(1);
        Graphics2D.drawRect(pos, size, BLACK);
        Graphics2D.drawText(text, "Default", pos, Color.black, (int) size.x);
    }

    @Override
    public void onClick() {
        Main.gameManager.getComponent(PlayerUIComponent.class).selected = this;
    }
}
