package creature;

import controllers.CreatureController;
import core.AbstractComponent;
import rounds.InitiativeOrder;

public class CreatureComponent extends AbstractComponent {

    public Creature creature;
    public CreatureController controller;
    public int initiative;
    public boolean canAct;

    public CreatureComponent(Creature creature, CreatureController controller, int initiative, boolean canAct) {
        this.creature = creature;
        this.controller = controller;
        this.initiative = initiative;
        this.canAct = canAct;
        InitiativeOrder.io.add(this);
    }
}
