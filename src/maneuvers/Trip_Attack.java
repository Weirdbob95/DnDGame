package maneuvers;

import conditions.Prone;
import creature.Creature;
import static enums.AbilityScore.STR;
import static enums.Size.LARGE;
import events.SavingThrowEvent;
import events.attack.AttackDamageRollEvent;
import player.Player;

public class Trip_Attack extends AttackManeuver {

    public Creature target;

    public Trip_Attack(Player player, ManeuversComponent mc) {
        super(player, mc);
    }

    @Override
    public void use(AttackDamageRollEvent e) {
        mc.addDieTo(e.a.damage);
        if (e.a.target.cdc.size.squares <= LARGE.squares) {
            if (SavingThrowEvent.fail(e.a.target, STR, mc.DC.get())) {
                new Prone(e.a.target).add();
            }
        }
    }
}
