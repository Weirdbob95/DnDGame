package actions;

import static actions.Action.Type.ACTION;
import amounts.Value;
import creature.Creature;
import enums.DamageType;
import events.attack.AttackEvent;
import grid.Square;
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
        Square toAttack = Query.ask(creature, new SquareQuery("Choose a creature to attack", creature.glc.occupied, range, true)).response;
        if (toAttack != null && toAttack.creature != null) {
            new AttackEvent(this, toAttack.creature).call();
        }
    }

    @Override
    public String[] defaultTabs() {
        return new String[]{"Attack"};
    }

    @Override
    public String getDescription() {
        return "The monster attacks its target.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return ACTION;
    }
}
