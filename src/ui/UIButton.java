package ui;

import core.Core;
import graphics.Graphics2D;
import org.newdawn.slick.Color;
import util.Color4d;

public class UIButton extends UIItem {

    public String text;
    public boolean disabled;

    public UIButton(UIItem parent, String text) {
        super(parent);
        this.text = text;
        size = size.setY(-Graphics2D.getTextHeight(text, "Default", (int) size.x) - 10);
    }

    @Override
    public void draw() {
        if (disabled) {
            Graphics2D.fillRect(pos, size, new Color4d(.5, .15, .15));
        } else if (selected()) {
            Graphics2D.fillRect(pos, size, new Color4d(.3, .8, 1));
        } else {
            Graphics2D.fillRect(pos, size, new Color4d(.1, .6, 1));
        }
        if (parent != null) {
            if (parent.children.indexOf(this) > 0) {
                Graphics2D.drawLine(pos, pos.add(size.setY(0)), new Color4d(.2, .2, .2), 1);
            }
            if (parent.children.indexOf(this) < parent.children.size() - 1) {
                Graphics2D.drawLine(pos.add(size.setX(0)), pos.add(size), new Color4d(.2, .2, .2), 1);
            }
        }
        //Graphics2D.drawRect(pos, size, BLACK);
        Graphics2D.drawText(text, "Default", pos.setY(pos.y - 5), Color.black, (int) size.x);
    }

    @Override
    public void onClick() {
        Core.gameManager.getComponent(PlayerUIComponent.class).root = this;
    }

    @Override
    public boolean selected() {
        return super.selected() && !disabled;
    }
}
