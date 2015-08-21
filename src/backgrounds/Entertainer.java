package backgrounds;

import enums.Skill;
import static enums.Skill.Acrobatics;
import static enums.Skill.Performance;

public class Entertainer extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Acrobatics, Performance};
    }
}
