package events.attack;

import actions.MonsterAttackAction;
import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import events.Event;
import events.TakeDamageEvent;
import items.Weapon;
import java.util.ArrayList;
import queries.Query;
import queries.SelectQuery;
import util.Log;
import util.SelectableImpl;

public class AttackEvent extends Event {

    public Creature attacker;
    public Creature target;
    public Object source;
    public boolean isWeapon;
    public Weapon weapon;
    public boolean isRanged;
    public int range;
    public AbilityScore abilityScore;
    public ArrayList<AbilityScore> allowedAbilityScores;
    public Stat toHit;
    public Stat damage;
    public boolean advantage;
    public boolean disadvantage;
    public boolean isCritical;
    public int roll;
    public boolean isMonsterAttack;
    public boolean isOpportunityAttack;

    public AttackEvent(Creature attacker, Creature target, Weapon weapon, int range, Object source) {
        this.attacker = attacker;
        this.target = target;
        isWeapon = true;
        this.weapon = weapon;
        this.range = range;
        this.source = source;
        allowedAbilityScores = new ArrayList();
        if (!weapon.isRanged || weapon.thrown || weapon.finesse) {
            allowedAbilityScores.add(AbilityScore.STR);
        }
        if ((weapon.isRanged && !weapon.thrown) || weapon.finesse) {
            allowedAbilityScores.add(AbilityScore.DEX);
        }
        toHit = new Stat();
        damage = new Stat();
        isRanged = weapon.isRanged;
    }

    public AttackEvent(MonsterAttackAction maa, Creature target) {
        this.attacker = maa.creature;
        this.target = target;
        source = maa;
        isWeapon = maa.isWeapon;
        isRanged = maa.isRanged;
        range = maa.range;
        toHit = new Stat(maa.toHit);
        damage = new Stat("Base", maa.damage);
        isMonsterAttack = true;
    }

    @Override
    public void call() {
        super.call();
        if (!isMonsterAttack) {
            ArrayList<SelectableImpl> options = new ArrayList();
            allowedAbilityScores.stream().forEach(as
                    -> options.add(new SelectableImpl(as.longName(), "Value: " + attacker.asc.get(as))));
            abilityScore = AbilityScore.valueOfLongName(Query.ask(attacker, new SelectQuery("Choose an ability score to attack with", options)).response.getName());
        }
        //Get the attack roll info
        new AttackRollEvent(this).call();
        //Roll the dice
        roll = new Die(20, advantage, disadvantage).roll;
        toHit.roll();
        //Check the attack roll results
        new AttackResultEvent(this).call();
        Log.print("Rolled a " + roll + " + " + toHit.get() + " (" + (roll + toHit.get()) + ") against an AC of " + target.ac.AC.get());
        //See if you hit
        if (!hit()) {
            new AttackFinishEvent(this, false).call();
            return;
        }
        //Get the damage roll info
        new AttackDamageRollEvent(this).call();
        //Roll the damage
        damage.roll();
        //Check the damage roll results
        new AttackDamageResultEvent(this).call();
        Log.print("Dealt " + damage.get() + " damage");
        //Deal damage
        new TakeDamageEvent(target, damage.get(), this).call();
        new AttackFinishEvent(this, true).call();
    }

    public boolean hit() {
        return isCritical || (roll != 1 && roll + toHit.get() >= target.ac.AC.get());
    }
}
