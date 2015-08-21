package backgrounds;

import enums.Skill;
import static enums.Skill.History;
import static enums.Skill.Persuasion;

public class Noble extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{History, Persuasion};
    }
}
