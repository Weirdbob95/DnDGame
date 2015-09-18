package classes.monk;

import classes.Archetype;
import classes.monk.Monk.Flurry_of_Blows;
import conditions.Prone;
import static enums.AbilityScore.DEX;
import static enums.AbilityScore.STR;
import events.SavingThrowEvent;
import events.attack.AttackDamageRollEvent;
import events.move.TeleportEvent;
import java.util.Arrays;
import java.util.List;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;
import util.SelectableImpl;

public class Way_of_the_Open_Hand extends Archetype<Monk> {

    public Way_of_the_Open_Hand(Monk playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                add(AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player()) {
                        if (e.a.source instanceof Flurry_of_Blows) {
                            List<Selectable> choices = Arrays.asList(
                                    new SelectableImpl("Trip", "Your target must succeed on a Dexterity saving throw or be knocked prone."),
                                    new SelectableImpl("Push", "Your target must succeed on a Strength saving throw or be pushed up to 15 feet away from you."),
                                    new SelectableImpl("Disable", "Your target can't take reactions until the end of your next turn."));
                            Selectable choice = Query.ask(player(), new SelectQuery("Choose an effect", choices, "Choose", "Cancel")).response;
                            switch (choice.getName()) {
                                case "Trip":
                                    if (SavingThrowEvent.fail(e.a.target, DEX, playerClass.kiSaveDC.get())) {
                                        new Prone(e.a.target).add();
                                    }
                                    break;
                                case "Push":
                                    if (SavingThrowEvent.fail(e.a.target, STR, playerClass.kiSaveDC.get())) {
                                        TeleportEvent.pushAway(player(), e.a.target, 15);
                                    }
                                    break;
                                case "Disable":

                                    break;
                            }
                        }
                    }
                });
                break;
        }
    }
}
