package backgrounds;

import enums.Skill;
import static enums.Skill.Insight;
import static enums.Skill.Religion;

public class Acolyte extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Insight, Religion};
    }
}
