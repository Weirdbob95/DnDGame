package enums;

import static enums.AbilityScore.*;
import util.Selectable;

public enum Skill implements Selectable {

    Athletics(STR, "Athletics covers difficult situations you encounter while climbing, jumping, or swimming."),
    Acrobatics(DEX, "Acrobatics covers your attempt to stay on your feet in a tricky situation."),
    Sleight_of_Hand(DEX, "Sleight of Hand lets you perform acts of legerdemain or manual trickery."),
    Stealth(DEX, "Stealth lets you conceal yourself from enemies or avoid notice."),
    Arcana(INT, "Arcana measures your ability to recall lore about magic or the planes."),
    History(INT, "History measures your ability to recall lore about historical events."),
    Investigation(INT, "Investigation covers looking around for clues and making deductions based on them."),
    Nature(INT, "Nature measures your ability to recall lore about terrain, plants and animals, and nature."),
    Religion(INT, "Religion measures your ability to recall lore about deities and religion."),
    Animal_Handling(WIS, "Animal Handling covers attempts to control or understand animals."),
    Insight(WIS, "Insight decdies whether you can determine the true intentions of a creature."),
    Medicine(WIS, "Medicine lets you try to stabilize a dying companion or diagnose an illness."),
    Perception(WIS, "Perception lets you spot, hear, or otherwise detect the presence of something."),
    Survival(WIS, "Survival allows you to follow tracks or travel safely through the wilderness."),
    Deception(CHA, "Deception determines whether you can convincingly hide the truth."),
    Intimidation(CHA, "Indimidation lets you influence someone through threats or violence."),
    Performance(CHA, "Performance determines how well you can delight an audience."),
    Persuasion(CHA, "Persuasion lets you attempt to influence someone or a group of people.");

    public final AbilityScore as;
    private final String desc;

    private Skill(AbilityScore as, String desc) {
        this.as = as;
        this.desc = desc;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getName() {
        return name().replace('_', ' ');
    }
}
