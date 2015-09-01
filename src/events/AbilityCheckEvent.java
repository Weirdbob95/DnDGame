package events;

import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import enums.Skill;
import player.Player;
import util.Log;

public class AbilityCheckEvent extends Event {

    public Creature creature;
    public AbilityScore abilityScore;
    public Skill skill;
    public Stat bonus;
    public boolean advantage;
    public boolean disadvantage;
    public int roll;

    public AbilityCheckEvent(Creature creature, AbilityScore abilityScore, Skill skill) {
        this.creature = creature;
        this.abilityScore = abilityScore;
        this.skill = skill;
        bonus = new Stat();
    }

    @Override
    public void call() {
        if (abilityScore != null) {
            bonus.set(abilityScore.shortName(), creature.asc.mod(abilityScore));
        }
        if (skill != null && creature instanceof Player && ((Player) creature).pc.skillProfs.contains(skill)) {
            bonus.set("Proficiency", ((Player) creature).pc.prof);
        }
        super.call();
        roll = new Die(20, advantage, disadvantage).roll();
        bonus.roll();
        new AbilityCheckResultEvent(this).call();
        Log.print("Rolled a " + roll + " + " + bonus.get() + " (" + (roll + bonus.get()) + ")");
    }

    public int get() {
        return roll + bonus.get();
    }
}
