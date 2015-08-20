package events;

import creature.Creature;

public class TakeDamageEvent extends Event {

    public Creature creature;
    public int damage;
    public Object source;

    public TakeDamageEvent(Creature creature, int damage, Object source) {
        this.creature = creature;
        this.damage = damage;
        this.source = source;
    }

    @Override
    public void call() {
        super.call();
        creature.hc.damage(damage);
    }
}
