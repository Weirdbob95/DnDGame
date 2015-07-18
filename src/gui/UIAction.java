package gui;

import actions.Action;

public class UIAction extends UIButton {

    public Action a;

    public UIAction(UIItem tab, Action a) {
        super(tab, a.toString());
        this.a = a;
        new UIText(this, a.toString(), "Medium");
        new UIText(this, a.description(), "Small");
        a.setUIParameters(this);
        new UIUseAction(this, a);
    }
}
