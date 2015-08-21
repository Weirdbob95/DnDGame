package backgrounds;

import enums.Skill;
import static enums.Skill.Medicine;
import static enums.Skill.Religion;

public class Hermit extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Medicine, Religion};
    }
}
