package ui;

import core.Main;

public class UIBackButton extends UIButton {

    public UIBackButton(UIItem parent) {
        super(parent, "Back");
    }

    @Override
    public void onClick() {
        Main.gameManager.getComponent(PlayerUIComponent.class).root = parent.parent;
    }
}
