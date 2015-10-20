package classes.fighter;

import amounts.MultipliedAmount;
import classes.Archetype;
import static enums.AbilityScore.CON;
import enums.FightingStyle;
import events.AbilityCheckEvent;
import events.TurnStartEvent;
import events.attack.AttackResultEvent;
import player.FightingStylesComponent;

public class Champion extends Archetype<Fighter> {

    public Champion(Fighter playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                add(AttackResultEvent.class, are -> {
                    if (are.a.attacker == player()) {
                        if (are.a.isWeapon && !are.a.isCritical) {
                            if (are.a.roll == 19 || (are.a.roll == 18 && level() >= 15)) {
                                are.a.isCritical = true;
                            }
                        }
                    }
                });
                break;
            case 7:
                add(AbilityCheckEvent.class, ace -> {
                    if (ace.creature == player()) {
                        switch (ace.abilityScore) {
                            case STR:
                            case DEX:
                            case CON:
                                if (!ace.bonus.components.containsKey("Proficiency")) {
                                    ace.bonus.set("Proficiency", new MultipliedAmount(player().pc.prof, .5));
                                }
                        }
                    }
                });
                break;
            case 10:
                player().getComponent(FightingStylesComponent.class).chooseFightingStyle(FightingStyle.values());
                break;
            case 18:
                add(TurnStartEvent.class, e -> {
                    if (e.creature == player()) {
                        if (player().hc.currentHealth.get() > 0 && player().hc.currentHealth.get() <= player().hc.maxHealth.get() / 2) {
                            player().hc.heal(5 + player().asc.mod(CON).get());
                        }
                    }
                });
                break;
        }
    }
}
