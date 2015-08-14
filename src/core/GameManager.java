package core;

import graphics.RenderManagerComponent2D;
import graphics.RenderManagerSystem2D;
import graphics.GUISystem;
import overlay.OverlaySystem;
import ui.PlayerUIComponent;
import ui.UISystem;

public class GameManager extends AbstractEntity {

    public EntityListComponent elc;
    public RenderManagerComponent2D rmc;

    public GameManager() {
        elc = add(new EntityListComponent());

        rmc = add(new RenderManagerComponent2D());
        add(new RenderManagerSystem2D(rmc));

        FPSManagerComponent fmc = add(new FPSManagerComponent());
        add(new FPSManagerSystem(fmc));

        add(new GUISystem());
        PlayerUIComponent puic = add(new PlayerUIComponent());
        add(new OverlaySystem(puic));
        add(new UISystem(puic));
    }
}
