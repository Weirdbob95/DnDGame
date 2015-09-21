package events.attack;

import actions.MonsterAttackAction;
import creature.Creature;
import events.Event;
import grid.Square;
import items.Weapon;
import queries.Query;
import queries.SquareQuery;

public class AttackTargetEvent extends Event {

    public Creature creature;
    public Weapon w;
    public int range;
    public Object source;
    public AttackEvent a;
    public MonsterAttackAction maa;

    public AttackTargetEvent(Creature creature, Weapon w, int range, Object source) {
        this.creature = creature;
        this.w = w;
        this.range = range;
        this.source = source;
    }

    public AttackTargetEvent(MonsterAttackAction maa) {
        creature = maa.creature;
        range = maa.rangeLong;
        source = maa;
    }

    @Override
    public void call() {
        super.call();
        Square toAttack = Query.ask(creature, new SquareQuery("Choose a creature to attack", creature.glc.occupied, range, true)).response;
        if (toAttack != null && toAttack.creature != null) {
            if (maa == null) {
                a = new AttackEvent(creature, toAttack.creature, w, range, source);
            } else {
                a = new AttackEvent(maa, toAttack.creature);
            }
            a.call();
        }
    }
}
