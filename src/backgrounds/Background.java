package backgrounds;

import enums.Skill;
import java.io.Serializable;

public abstract class Background implements Serializable {

    public abstract Skill[] skills();
}
