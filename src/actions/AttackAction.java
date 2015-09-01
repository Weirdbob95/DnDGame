package actions;

import static actions.Action.Type.ACTION;
import creature.Creature;
import events.attack.AttackTargetEvent;
import items.Weapon;
import java.util.ArrayList;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;
import util.SelectableImpl;

public class AttackAction extends Action {

    public int extraAttacks = 0;
    public int attacks = 1;

    public AttackAction(Creature creature) {
        super(creature);
    }

    @Override
    protected void act() {
        if (attacks > 0) {
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
        attacks = extraAttacks + 1;
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

    public void setExtraAttacks(int extraAttacks) {
        if (extraAttacks > this.extraAttacks) {
            this.extraAttacks = extraAttacks;
            attacks = extraAttacks + 1;
        }
    }

    public void singleAttack() {
        Weapon w = creature.wc.unarmedStrike;
        ArrayList<Weapon> weaponList = creature.wc.getAll(Weapon.class);
        if (!weaponList.isEmpty()) {
            weaponList.add(w);
            w = Query.ask(creature, new SelectQuery<Weapon>("Choose a weapon to attack with", weaponList)).response;
        }
        int range = Math.max(w.range, creature.cdc.reach.get() + (w.reach ? 5 : 0));
        new AttackTargetEvent(creature, w, range).call();
    }
}
