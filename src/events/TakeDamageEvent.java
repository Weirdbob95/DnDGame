package events;

import creature.Creature;

public class TakeDamageEvent extends Event {

    public Creature target;
    public Creature attacker;
    public int damage;
    public Object source;

    public TakeDamageEvent(Creature target, Creature attacker, int damage, Object source) {
        this.target = target;
        this.attacker = attacker;
        this.damage = damage;
        this.source = source;
    }

    @Override
    public void call() {
        super.call();
        target.hc.damage(damage);
    }
}
