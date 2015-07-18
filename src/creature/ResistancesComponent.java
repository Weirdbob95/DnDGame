package creature;

import core.AbstractComponent;
import enums.DamageType;
import java.util.ArrayList;

public class ResistancesComponent extends AbstractComponent {

    public ArrayList<DamageType> resistances;
    public ArrayList<DamageType> vulnerabilities;
}
