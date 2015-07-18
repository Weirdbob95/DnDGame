package enums;

import static enums.AbilityScore.*;

public enum Skill {

    Athletics(STR),
    Acrobatics(DEX),
    Sleight_of_Hand(DEX),
    Stealth(DEX),
    Arcana(INT),
    History(INT),
    Nature(INT),
    Religion(INT),
    Investigation(INT),
    Animal_Handling(INT),
    Insight(WIS),
    Medicine(WIS),
    Perception(WIS),
    Survival(WIS),
    Deception(CHA),
    Intimidation(CHA),
    Performance(CHA),
    Persuasion(CHA);

    public final AbilityScore as;

    private Skill(AbilityScore as) {
        this.as = as;
    }

    @Override
    public String toString() {
        return name().replace('_', ' ');
    }
}
