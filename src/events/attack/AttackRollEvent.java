package events.attack;

import events.Event;

public class AttackRollEvent extends Event {

    public AttackPacket a;

    public AttackRollEvent(AttackPacket a) {
        this.a = a;
        a.toHit.set("Proficiency", a.attacker.pc.prof);
        a.toHit.set("Ability Score", a.attacker.asc.mod(a.abilityScore));
        a.AC.set("AC", a.target.ac.AC);
        call();
    }

}
