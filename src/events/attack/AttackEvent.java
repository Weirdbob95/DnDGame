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
    public boolean isWeapon;
    public Weapon weapon;
    public AbilityScore abilityScore;
    public ArrayList<AbilityScore> allowedAbilityScores;
    public Stat toHit;
    public Stat damage;
    public boolean advantage;
    public boolean disadvantage;
    public boolean isCritical;
    public int roll;
    public boolean isMonsterAttack;

    public AttackEvent(Creature attacker, Creature target, Weapon weapon) {
        this.attacker = attacker;
        this.target = target;
        isWeapon = true;
        this.weapon = weapon;
        allowedAbilityScores = new ArrayList();
        if (!weapon.isRanged || weapon.thrown || weapon.finesse) {
            allowedAbilityScores.add(AbilityScore.STR);
        }
        if ((weapon.isRanged && !weapon.thrown) || weapon.finesse) {
            allowedAbilityScores.add(AbilityScore.DEX);
        }
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
        if (!isMonsterAttack) {
            ArrayList<SelectableImpl> options = new ArrayList();
            for (AbilityScore as : allowedAbilityScores) {
                options.add(new SelectableImpl(as.longName(), "Value: " + attacker.asc.get(as)));
            }
            abilityScore = AbilityScore.valueOfLongName(Query.ask(attacker, new SelectQuery("Choose an ability score to attack with", options)).response.getName());
        }
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
}
