package backgrounds;

import enums.Skill;
import static enums.Skill.Sleight_of_Hand;
import static enums.Skill.Stealth;

public class Urchin extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Sleight_of_Hand, Stealth};
    }
}
