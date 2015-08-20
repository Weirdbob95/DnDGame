package queries;

import controllers.ManualController;
import core.Core;
import creature.Creature;
import creature.CreatureComponent;
import ui.PlayerUIComponent;
import ui.UIItem;

public abstract class Query {

    public PlayerUIComponent puic;
    public ManualController toNotify;

    public boolean addToUI(ManualController toNotify) {
        this.toNotify = toNotify;
        puic = Core.gameManager.getComponent(PlayerUIComponent.class);
        puic.root = new UIItem(null) {
            @Override
            public void draw() {
            }

            @Override
            public void onClick() {
            }
        };
        return createUI();
    }

    public static <Q extends Query> Q ask(Creature creature, Q query) {
        creature.getComponent(CreatureComponent.class).controller.handleQuery(query);
        return query;
    }

    public abstract boolean createUI();

    public void removeFromUI() {
        puic.root = null;
        puic.overlayItems.clear();
        toNotify.synNotify();
    }
}
