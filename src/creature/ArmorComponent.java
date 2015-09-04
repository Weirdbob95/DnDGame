package creature;

import amounts.Amount;
import amounts.Stat;
import core.AbstractComponent;
import static enums.AbilityScore.DEX;

public class ArmorComponent extends AbstractComponent {

    public Amount AC;
    public String armor;

    public ArmorComponent(Creature c) {
        AC = new Stat(10);
        AC.set("Dex", c.asc.mod(DEX));
    }
}
