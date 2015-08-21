package backgrounds;

import enums.Skill;
import static enums.Skill.Insight;
import static enums.Skill.Persuasion;

public class Guild_Artisan extends Background {

    @Override
    public Skill[] skills() {
        return new Skill[]{Insight, Persuasion};
    }
}
