package backgrounds;

import enums.Skill;
import static enums.Skill.Animal_Handling;
import static enums.Skill.Survival;

public class Folk_Hero extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Animal_Handling, Survival};
    }
}
