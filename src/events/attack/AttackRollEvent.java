package events.attack;

import events.Event;
import player.Player;

public class AttackRollEvent extends Event {

    public AttackEvent a;

    public AttackRollEvent(AttackEvent a) {
        this.a = a;
        if (!a.isMonsterAttack) {
            a.toHit.set("Proficiency", ((Player) a.attacker).pc.prof);
            a.toHit.set("Ability Score", a.attacker.asc.mod(a.abilityScore));
        }
    }
}
