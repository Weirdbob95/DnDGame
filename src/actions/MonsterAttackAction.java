package actions;

import static actions.Action.Type.ACTION;
import amounts.Value;
import creature.Creature;
import enums.DamageType;
import events.attack.AttackEvent;
import queries.Query;
import queries.SquareQuery;

public class MonsterAttackAction extends Action {

    public String name;
    public boolean isRanged;
    public boolean isWeapon;

    public int range;
    public int rangeLong;

    public int toHit;
    public Value damage;
    public DamageType damageType;

    public MonsterAttackAction(Creature creature) {
        super(creature);
        tabs.add("Attack");
    }

    @Override
    public void act() {
        new AttackEvent(creature, Query.ask(creature, new SquareQuery("Choose a square to move to.", this, range, true)).response.creature, null, null).call();
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{"Attack"};
    }

    @Override
    public String description() {
        return "The monster attacks its target.";
    }

    @Override
    public Type getType() {
        return ACTION;
    }

    @Override
    public String toString() {
        return name;
    }
}
