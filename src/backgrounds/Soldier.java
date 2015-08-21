package backgrounds;

import enums.Skill;
import static enums.Skill.Athletics;
import static enums.Skill.Intimidation;

public class Soldier extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Athletics, Intimidation};
    }
}
