package ui;

import graphics.Graphics2D;
import org.newdawn.slick.Color;
import util.Color4d;

public class UIText extends UIItem {

    public String text;
    public String font;

    public UIText(UIItem parent, String text) {
        this(parent, text, "Default");
    }

    public UIText(UIItem parent, String text, String font) {
        super(parent);
        this.text = text;
        this.font = font;
        size = size.setY(-Graphics2D.getTextHeight(text, font, (int) size.x) - 6);
    }

    @Override
    public void draw() {
        Graphics2D.fillRect(pos, size, new Color4d(.1, .5, 1));
        Graphics2D.drawText(text, font, pos.setY(pos.y - 3), Color.black, (int) size.x);
    }

    @Override
    public void onClick() {
    }
}
