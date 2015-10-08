package creature;

import amounts.AddedAmount;
import amounts.Stat;
import amounts.Value;
import core.AbstractComponent;
import static enums.AbilityScore.DEX;

public class ArmorComponent extends AbstractComponent {

    public Stat AC;
    public String armor;

    public ArmorComponent(Creature c) {
        AC = new Stat("Base", new AddedAmount(new Value(10), c.asc.mod(DEX)));
    }
}
