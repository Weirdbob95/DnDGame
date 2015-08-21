package backgrounds;

import enums.Skill;
import static enums.Skill.Athletics;
import static enums.Skill.Perception;

public class Sailor extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Athletics, Perception};
    }
}
