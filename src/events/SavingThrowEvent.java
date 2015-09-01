package events;

import amounts.Die;
import amounts.Stat;
import creature.Creature;
import enums.AbilityScore;
import player.Player;
import util.Log;

public class SavingThrowEvent extends Event {

    public Creature creature;
    public AbilityScore abilityScore;
    public int DC;
    public Stat bonus;
    public boolean advantage;
    public boolean disadvantage;
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
            bonus.set(abilityScore.shortName(), creature.asc.mod(abilityScore));
            if (creature instanceof Player && ((Player) creature).pc.savingThrowProfs.contains(abilityScore)) {
                bonus.set("Proficiency", ((Player) creature).pc.prof);
            }
        }
        super.call();
        roll = new Die(20, advantage, disadvantage).roll();
        bonus.roll();
        new SavingThrowResultEvent(this).call();
        Log.print("Rolled a " + roll + " + " + bonus.get() + " (" + (roll + bonus.get()) + ") against a DC of " + DC);
    }

    public static boolean fail(Creature creature, AbilityScore abilityScore, int DC) {
        SavingThrowEvent ste = new SavingThrowEvent(creature, abilityScore, DC);
        ste.call();
        return !ste.success();
    }

    public boolean success() {
        return roll + bonus.get() >= DC;
    }
}
