package creature;

import amounts.Amount;
import amounts.Stat;
import core.AbstractComponent;

public class HealthComponent extends AbstractComponent {

    public Stat maxHealth = new Stat();
    public Stat currentHealth = new Stat("Max", maxHealth);

    public void damage(int amt) {
        if (amt > getTempHP()) {
            currentHealth.edit("Damage", -Math.min(amt - getTempHP(), currentHealth.get() - getTempHP()));
            currentHealth.components.remove("Temp HP");
        } else {
            currentHealth.set("Temp HP", getTempHP() - amt);
        }
    }

    public int getTempHP() {
        Amount tempHP = currentHealth.components.get("Temp HP");
        return tempHP == null ? 0 : tempHP.get();
    }

    public void giveTempHP(int amt) {
        if (getTempHP() < amt) {
            currentHealth.set("Temp HP", amt);
        }
    }

    public void heal(int amt) {
        currentHealth.edit("Damage", Math.min(amt, -currentHealth.components.get("Damage").get()));
    }
}
