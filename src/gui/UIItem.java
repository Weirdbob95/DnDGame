package gui;

import core.MouseInput;
import java.util.ArrayList;
import util.Vec2;

public abstract class UIItem {

    public Vec2 pos;
    public Vec2 size = new Vec2(300, -50);
    public UIItem parent;
    public ArrayList<UIItem> children = new ArrayList();

    public UIItem(UIItem parent) {
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
            if (!(this instanceof UIBackButton)) {
                new UIBackButton(this);
            }
        }
    }

    public abstract void draw();

    public abstract void onClick();

    public boolean selected() {
        return MouseInput.mouseScreen().containedBy(pos, pos.add(size));
    }
}
