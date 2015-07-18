package events.attack;

import events.Event;

public class AttackIsCriticalEvent extends Event {

    public AttackPacket a;

    public AttackIsCriticalEvent(AttackPacket a) {
        this.a = a;
        a.isCritical = a.roll == 20;
        call();
    }
}
