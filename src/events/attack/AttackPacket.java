package events.attack;

import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import events.DieRollEvent;
import items.Weapon;

public class AttackPacket {

    public Creature attacker;
    public Creature target;
    public boolean isWeapon;
    public Weapon weapon;
    //public Spell spell;
    public AbilityScore abilityScore;
    public Stat toHit;
    public Stat AC;
    public Stat damage;
    public boolean advantage;
    public boolean disadvantage;
    public boolean isCritical;
    public int roll;

    public AttackPacket(Creature attacker, Creature target, Weapon weapon, AbilityScore abilityScore) {
        this.attacker = attacker;
        this.target = target;
        isWeapon = true;
        this.weapon = weapon;
        //spell = null;
        this.abilityScore = abilityScore;
        toHit = new Stat();
        AC = new Stat();
        damage = new Stat();
        advantage = false;
        disadvantage = false;
        isCritical = false;
        roll = 0;
    }

    public void attack() {
        /*
         Calculate attack bonus and advantage and AC
         Roll the die
         See if you hit
         See if it’s a crit
         Get the damage
         Hurt the enemy
         */
        //Calculate attack bonus and advantage and AC
        new AttackRollEvent(this);
        //Roll the die
        roll = new DieRollEvent(attacker, new Die(20, advantage, disadvantage)).die.roll;
        //See if you hit
        int tohit = toHit.get() + roll;
        if (roll != 20 && (roll == 1 || tohit < AC.get())) {
            return;
        }
        //See if it's a critical hit
        new AttackIsCriticalEvent(this);
        //Get the damage
        new AttackDamageEvent(this);
        //Hurt the enemy
        int dmg = damage.roll();
        if (dmg < 0) {
            dmg = 0;
        }
        target.hc.currentHealth.edit("Damage", -dmg);
    }
}
