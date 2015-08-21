package backgrounds;

import enums.Skill;
import static enums.Skill.Athletics;
import static enums.Skill.Survival;

public class Outlander extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Athletics, Survival};
    }
}
