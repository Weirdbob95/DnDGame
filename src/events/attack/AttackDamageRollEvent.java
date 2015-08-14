package events.attack;

import amounts.Die;
import events.Event;

public class AttackDamageRollEvent extends Event {

    public AttackEvent a;

    public AttackDamageRollEvent(AttackEvent a) {
        this.a = a;
        if (!a.isMonsterAttack) {
            a.damage.set("Weapon", a.weapon.damage);
            a.damage.set("Ability Score", a.attacker.asc.mod(a.abilityScore));
        }
    }

    @Override
    public void call() {
        super.call();
        if (a.isCritical) {
            for (Die d : a.damage.asValue().dice) {
                d.roll = d.sides;
            }
        }
    }
}
