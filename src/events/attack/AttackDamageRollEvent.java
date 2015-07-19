package events.attack;

import amounts.Value;
import events.Event;

public class AttackDamageRollEvent extends Event {

    public AttackEvent a;

    public AttackDamageRollEvent(AttackEvent a) {
        this.a = a;
        if (a.isWeapon) {
            addDamage("Weapon", a.weapon.damage);
            a.damage.set("Ability Score", a.attacker.asc.mod(a.abilityScore));
        }
    }

    public void addDamage(String name, Value d) {
        if (!a.isCritical) {
            a.damage.set(name, d);
        } else {
            d.add(d);
            d.flat /= 2;
            a.damage.set(name, d);
        }
    }

}
