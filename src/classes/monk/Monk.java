package classes.monk;

import amounts.AddedAmount;
import amounts.ConditionalAmount;
import amounts.Value;
import classes.PlayerClass;
import enums.AbilityScore;
import static enums.AbilityScore.*;
import enums.Skill;
import static enums.Skill.*;
import player.Player;

public class Monk extends PlayerClass {

    public Monk(Player player) {
        super(player);
    }

    @Override
    public int archetypeLevel() {
        return 3;
    }

    @Override
    public int hitDie() {
        return 8;
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                player.ac.AC.set("Base", new ConditionalAmount(() -> true, new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(WIS)), player.ac.AC.components.get("Base")));
                break;
        }
    }

    @Override
    public AbilityScore[] savingThrows() {
        return new AbilityScore[]{STR, DEX};
    }

    @Override
    public Skill[] skills() {
        return new Skill[]{Acrobatics, Athletics, History, Insight, Religion, Stealth};
    }
}
