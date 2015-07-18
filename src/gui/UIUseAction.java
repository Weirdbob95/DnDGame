package gui;

import actions.Action;
import core.Main;

public class UIUseAction extends UIButton {

    public Action a;

    public UIUseAction(UIAction parent, Action a) {
        super(parent, "Use Action");
        this.a = a;
    }

    @Override
    public void onClick() {
        if (a.validUIParameters()) {
            a.use();
            Main.gameManager.getComponent(PlayerUIComponent.class).selected = null;
        }
    }
}
