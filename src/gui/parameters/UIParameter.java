package gui.parameters;

import core.Main;
import core.MouseInput;
import graphics.Graphics2D;
import gui.PlayerUIComponent;
import gui.UIAction;
import gui.UIItem;
import org.newdawn.slick.Color;
import util.Color4d;

public abstract class UIParameter extends UIItem {

    public boolean selected;
    public String text;

    public UIParameter(UIAction parent, String text) {
        super(parent);
        this.text = text;
        size = size.setY(-Graphics2D.getTextHeight(text, "Default", (int) size.x));
    }

    public abstract void choose();

    @Override
    public void draw() {
        if (selected) {
            if (MouseInput.isPressed(1)) {
                selected = false;
            }
            if (MouseInput.isPressed(0)) {
                if (Main.gameManager.getComponent(PlayerUIComponent.class).mouseOverUI) {
                    if (!selected()) {
                        selected = false;
                    }
                } else {
                    choose();
                    selected = false;
                }
            }
        }
        if (selected) {
            Graphics2D.fillRect(pos, size, new Color4d(0, 1, .4));
        } else {
            if (selected()) {
                Graphics2D.fillRect(pos, size, new Color4d(0, .3, .6));
            } else {
                Graphics2D.fillRect(pos, size, new Color4d(0, .5, 1));
            }
        }
        Graphics2D.drawText(text, "Default", pos, Color.black, (int) size.x);
    }

    @Override
    public void onClick() {
        selected = !selected;
    }

}
