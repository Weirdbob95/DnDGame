package creature;

import amounts.Stat;
import core.AbstractComponent;

public class HealthComponent extends AbstractComponent {

    public Stat maxHealth = new Stat();
    public Stat currentHealth = new Stat("Max", maxHealth);

    public void damage(int amt) {
        currentHealth.edit("Damage", -Math.min(amt, currentHealth.get()));
    }

    public void heal(int amt) {
        currentHealth.edit("Damage", Math.min(amt, -currentHealth.components.get("Damage").get()));
    }
}
