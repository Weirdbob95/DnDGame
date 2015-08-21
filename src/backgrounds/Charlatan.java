package backgrounds;

import enums.Skill;
import static enums.Skill.Deception;
import static enums.Skill.Sleight_of_Hand;

public class Charlatan extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Deception, Sleight_of_Hand};
    }
}
