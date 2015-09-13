package player;

import amounts.Amount;
import core.AbstractComponent;
import enums.AbilityScore;
import enums.Skill;
import items.Weapon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import queries.Query;
import queries.SelectQuery;

public class ProficienciesComponent extends AbstractComponent {

    public HashSet<Skill> skillProfs = new HashSet();
    public HashSet<Weapon> weaponProfs = new HashSet();
    public HashSet<AbilityScore> savingThrowProfs = new HashSet();
    public Player player;
    public Amount prof = new Amount() { //Can't be a lambda expression because of serialization bugs
        @Override
        public int get() {
            return (player.clc.level() + 7) / 4;
        }
    };

    public ProficienciesComponent(Player player) {
        this.player = player;
    }

    public void chooseSkill(Skill[] skills) {
        ArrayList<Skill> skillList = new ArrayList(Arrays.asList(skills));
        skillList.removeAll(skillProfs);
        Skill skill = Query.ask(player, new SelectQuery<Skill>("Choose a skill", skillList)).response;
        skillProfs.add(skill);
    }
}
