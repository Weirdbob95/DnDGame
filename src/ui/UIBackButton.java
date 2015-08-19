package ui;

import core.Core;

public class UIBackButton extends UIButton {

    public UIBackButton(UIItem parent) {
        super(parent, "Back");
    }

    @Override
    public void onClick() {
        Core.gameManager.getComponent(PlayerUIComponent.class).root = parent.parent;
    }
}
