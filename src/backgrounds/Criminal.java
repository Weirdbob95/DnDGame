package backgrounds;

import enums.Skill;
import static enums.Skill.Deception;
import static enums.Skill.Stealth;

public class Criminal extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Deception, Stealth};
    }
}
