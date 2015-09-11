package classes.monk;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
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
import player.KiComponent;
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
                player.ac.AC.set("Base", new ConditionalAmount(() -> player.ac.armor == null,
                        new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(WIS)), player.ac.AC.components.get("Base")));
                break;
            case 2:
                player.add(new KiComponent());
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

    public class FlurryOfBlows extends Action {

        public FlurryOfBlows(Creature creature) {

        }

        @Override
        protected void act() {

        }

        @Override
        public String[] defaultTabs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        public String getName() {
            return "Flurry of Blows";
        }

        @Override
        public String getDescription() {
            return "Immediately after you take the Attack action on your turn, you can spend 1 ki point to make two unarmed strikes as a bonus action";
        }

        public boolean isAvailable() {
            return true; //add code to check for KiComponent

        }
    }
}
