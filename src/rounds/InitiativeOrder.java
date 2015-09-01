package rounds;

import creature.Creature;
import creature.CreatureComponent;
import events.TurnEndEvent;
import events.TurnStartEvent;
import java.util.ArrayList;

public class InitiativeOrder {

    public static InitiativeOrder io = new InitiativeOrder();
    private ArrayList<CreatureComponent> initiativeOrder = new ArrayList();

    public void add(CreatureComponent toAdd, Creature other) {
        initiativeOrder.add(initiativeOrder.indexOf(other) + 1, toAdd);
    }

    public void add(CreatureComponent toAdd) {
        int pos = 0;
        while (pos < initiativeOrder.size() && initiativeOrder.get(pos).initiative >= toAdd.initiative) {
            pos++;
        }
        initiativeOrder.add(pos, toAdd);
    }

    public CreatureComponent current() {
        for (CreatureComponent cc : initiativeOrder) {
            if (cc.canAct) {
                return cc;
            }
        }
        return null;
    }

    public void endTurn() {
        new TurnEndEvent(current().creature).call();
        current().canAct = false;
        if (current() == null) {
            newRound();
        }
        new TurnStartEvent(current().creature).call();
    }

    public void newRound() {
        for (CreatureComponent cc : initiativeOrder) {
            cc.canAct = true;
        }
    }
}
