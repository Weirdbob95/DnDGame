package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;
import events.attack.AttackEvent;
import grid.Square;
import items.Weapon;
import java.util.ArrayList;
import queries.Query;
import queries.SelectQuery;
import queries.SquareQuery;
import util.Selectable;
import util.SelectableImpl;

public class AttackAction extends Action {

    public int attacks = 1;

    public AttackAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        singleAttack();
        for (int i = 1; i < attacks;) {
            ArrayList<Selectable> choices = new ArrayList();
            choices.add(new SelectableImpl("Extra Attack", "Make another attack (as part of the Attack Action)"));
            if (creature.amc.getAction(MoveAction.class).isAvailable()) {
                choices.add(creature.amc.getAction(MoveAction.class));
            }
            Selectable next = Query.ask(creature, new SelectQuery("Choose an action", choices, "Use Action", "Cancel")).response;
            if (next == null) {
                break;
            }
            if (next.getName().equals("Extra Attack")) {
                singleAttack();
                i++;
            } else if (next instanceof MoveAction) {
                ((MoveAction) next).use();
            }
        }
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{"Attack"};
    }

    @Override
    public String getDescription() {
        return "temp";
    }

    @Override
    public Type getType() {
        return ACTION;
    }

    private void singleAttack() {
        Weapon w = creature.wc.unarmedStrike;
        ArrayList<Weapon> weaponList = creature.wc.getAll(Weapon.class);
        if (!weaponList.isEmpty()) {
            weaponList.add(w);
            w = Query.ask(creature, new SelectQuery<Weapon>("Choose a weapon to attack with", weaponList)).response;
        }

        int range = Math.max(w.range, w.reach ? 10 : 5);
        Square toAttack = Query.ask(creature, new SquareQuery("Choose a creature to attack", creature.glc.occupied, range, true)).response;
        if (toAttack != null && toAttack.creature != null) {
            new AttackEvent(creature, toAttack.creature, w).call();
        }
    }
}
