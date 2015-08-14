package events.attack;

import actions.MonsterAttackAction;
import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import events.Event;
import events.TakeDamageEvent;
import items.Weapon;
import util.Log;

public class AttackEvent extends Event {

    public Creature attacker;
    public Creature target;
    public boolean isWeapon;
    public Weapon weapon;
    public AbilityScore abilityScore;
    public Stat toHit;
    public Stat damage;
    public boolean advantage;
    public boolean disadvantage;
    public boolean isCritical;
    public int roll;
    public boolean isMonsterAttack;

    public AttackEvent(Creature attacker, Creature target, Weapon weapon, AbilityScore abilityScore) {
        this.attacker = attacker;
        this.target = target;
        isWeapon = true;
        this.weapon = weapon;
        this.abilityScore = abilityScore;
        toHit = new Stat();
        damage = new Stat();
    }

    public AttackEvent(MonsterAttackAction maa, Creature target) {
        this.attacker = maa.creature;
        this.target = target;
        isWeapon = maa.isWeapon;
        toHit = new Stat(maa.toHit);
        damage = new Stat("Base", maa.damage);
        isMonsterAttack = true;
    }

    @Override
    public void call() {
        super.call();
        //Get the attack roll info
        new AttackRollEvent(this).call();
        //Roll the dice
        roll = new Die(20).roll;
        toHit.roll();
        //Check the attack roll results
        new AttackResultEvent(this).call();
        Log.print("Rolled a " + roll + " + " + toHit.get() + " against an AC of " + target.ac.AC.get());
        //See if you hit
        if (!isCritical && (roll == 1 || roll + toHit.get() < target.ac.AC.get())) {
            return;
        }
        //Get the damage roll info
        new AttackDamageRollEvent(this).call();
        //Roll the damage
        damage.roll();
        //Check the damage roll results
        new AttackDamageResultEvent(this).call();
        //Deal damage
        new TakeDamageEvent(target, damage.get(), this).call();
    }
}
