package events;

import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import player.Player;

public class SavingThrowEvent extends Event {

    public Creature creature;
    public AbilityScore abilityScore;
    public int DC;
    public Stat bonus;
    public int roll;

    public SavingThrowEvent(Creature creature, AbilityScore abilityScore, int DC) {
        this.creature = creature;
        this.abilityScore = abilityScore;
        this.DC = DC;
        bonus = new Stat();
    }

    @Override
    public void call() {
        if (abilityScore != null) {
            bonus.set(abilityScore.shortName(), creature.asc.get(abilityScore));
            if (creature instanceof Player && ((Player) creature).pc.savingThrowProfs.contains(abilityScore)) {
                bonus.set("Proficiency", ((Player) creature).pc.prof);
            }
        }
        super.call();
        roll = new Die(20).roll();
        bonus.roll();
        new SavingThrowResultEvent(this).call();
    }

    public boolean success() {
        return roll + bonus.get() >= DC;
    }
}
