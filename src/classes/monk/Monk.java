/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.monk;

import amounts.AddedAmount;
import amounts.ConditionalAmount;
import amounts.Value;
import classes.PlayerClass;
import enums.AbilityScore;
import static enums.AbilityScore.DEX;
import static enums.AbilityScore.STR;
import static enums.AbilityScore.WIS;
import enums.Skill;
import static enums.Skill.Acrobatics;
import static enums.Skill.Athletics;
import static enums.Skill.History;
import static enums.Skill.Insight;
import static enums.Skill.Religion;
import static enums.Skill.Stealth;
import player.Player;

/**
 *
 * @author RLund16
 */
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
                player.ac.AC = new ConditionalAmount(() -> true, new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(WIS)), player.ac.AC);
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
