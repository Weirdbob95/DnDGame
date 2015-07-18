package creature;

import amounts.Stat;
import core.AbstractComponent;
import enums.AbilityScore;
import enums.Skill;
import items.Weapon;
import java.util.HashSet;

public class ProficienciesComponent extends AbstractComponent {

    public Stat prof = new Stat(2);
    public HashSet<Skill> skillProfs = new HashSet();
    public HashSet<Weapon> weaponProfs = new HashSet();
    public HashSet<AbilityScore> savingThrowProfs = new HashSet();
}
