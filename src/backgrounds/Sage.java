package backgrounds;

import enums.Skill;
import static enums.Skill.Arcana;
import static enums.Skill.History;

public class Sage extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Arcana, History};
    }
}
