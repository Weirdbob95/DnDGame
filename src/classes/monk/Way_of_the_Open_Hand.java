package classes.monk;

import actions.Action;
import static actions.Action.Type.ACTION;
import static actions.Action.Type.REACTION;
import amounts.Value;
import classes.Archetype;
import classes.monk.Monk.Flurry_of_Blows;
import conditions.Prone;
import creature.Creature;
import static enums.AbilityScore.*;
import events.*;
import events.attack.AttackDamageRollEvent;
import events.move.TeleportEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Mutable;
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
                                    Mutable<Boolean> isNextTurn = new Mutable(false);
                                    ArrayList<EventListener> list = new ArrayList();
                                    list.add(add(HasActionTypeEvent.class, e2 -> {
                                        if (e2.creature == e.a.target) {
                                            if (e2.type == REACTION) {
                                                e2.available = false;
                                            }
                                        }
                                    }));
                                    list.add(add(TurnEndEvent.class, e2 -> {
                                        if (isNextTurn.o) {
                                            remove(list);
                                        }
                                        isNextTurn.o = true;
                                    }));
                                    break;
                            }
                        }
                    }
                });
                break;
            case 17:
                End_Quivering_Palm_Vibrations eqpv = player().amc.addAction(new End_Quivering_Palm_Vibrations(player()));
                add(AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player()) {
                        if (e.a.isWeapon) {
                            if (e.a.weapon == player().wc.unarmedStrike) {
                                if (playerClass.kc.getKi() >= 3) {
                                    if (Query.ask(player(), new BooleanQuery("Use the Quivering Palm ability?")).response) {
                                        playerClass.kc.useKi(3);
                                        eqpv.target = e.a.target;
                                    }
                                }
                            }
                        }
                    }
                });
                break;
        }
    }

    public class End_Quivering_Palm_Vibrations extends Action {

        public Creature target;
        public Creature attacker;

        public End_Quivering_Palm_Vibrations(Creature creature) {
            super(creature);
        }

        @Override
        protected void act() {
            if (SavingThrowEvent.fail(target, CON, playerClass.kiSaveDC.get())) {
                target.hc.damage(target.hc.currentHealth.get());
            } else {
                new TakeDamageEvent(target, attacker, Value.parseValue("10d10").get(), this).call();
            }
            target = null;
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "You end the vibrations from your Quivering Palm ability, dealing massive damage.";
        }

        @Override
        public Type getType() {
            return ACTION;
        }

        @Override
        public boolean isAvailable() {
            return target != null;
        }
    }

    public class Wholeness_of_Body extends Action {

        public boolean available = true;

        public Wholeness_of_Body(Creature creature) {
            super(creature);

            add(LongRestEvent.class, e -> {
                available = true;
            });
        }

        @Override
        protected void act() {
            available = false;
            creature.hc.heal(level() * 3);
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "You regain hit points equal to three times your monk level.";
        }

        @Override
        public Type getType() {
            return ACTION;
        }
    }
}
