package creature;

import amounts.Stat;
import core.AbstractComponent;

public class HealthComponent extends AbstractComponent {

    public Stat maxHealth = new Stat();
    public Stat currentHealth = new Stat("Max", maxHealth);

}
