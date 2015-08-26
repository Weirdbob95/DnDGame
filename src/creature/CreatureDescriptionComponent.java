package creature;

import amounts.Stat;
import core.AbstractComponent;
import enums.CreatureType;
import enums.Size;
import static enums.Size.MEDIUM;

public class CreatureDescriptionComponent extends AbstractComponent {

    public String name;
    public String alignment;
    public Size size = MEDIUM;
    public CreatureType type;
    public Stat reach = new Stat(5);
}
