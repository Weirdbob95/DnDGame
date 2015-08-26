package maneuvers;

import amounts.*;
import core.AbstractComponent;
import static enums.AbilityScore.DEX;
import static enums.AbilityScore.STR;
import events.AbstractEventListener;
import events.Event;
import events.attack.AttackDamageRollEvent;
import java.util.ArrayList;
import java.util.Arrays;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Log;
import util.Selectable;

public class ManeuversComponent extends AbstractComponent {

    public Player player;
    public ArrayList<Maneuver> maneuvers;
    public int diceUsed;
    public int diceCap;
    public int dieSize;
    public Amount DC;
    public boolean moddingAttack;

    public ManeuversComponent(Player player) {
        this.player = player;
        maneuvers = new ArrayList();
        this.DC = new AddedAmount(new Value(8), player.pc.prof, new MaxAmount(player.asc.mod(STR), player.asc.mod(DEX)));

        new AbstractEventListener(player) {
            @Override
            public Class<? extends Event>[] callOn() {
                return new Class[]{AttackDamageRollEvent.class};
            }

            @Override
            public void onEvent(Event e) {
                AttackDamageRollEvent adre = (AttackDamageRollEvent) e;
                if (adre.a.attacker == player) {
                    if (!moddingAttack) {
                        if (diceUsed < diceCap) {
                            if (hasAttackManeuvers()) {
                                if (Query.ask(player, new BooleanQuery("Use a maneuver?")).response) {
                                    AttackManeuver m = Query.ask(player, new SelectQuery<AttackManeuver>("Choose which maneuver to use", Arrays.asList(getAttackManeuvers()), "Choose", "Cancel")).response;
                                    if (m != null) {
                                        diceUsed++;
                                        m.use(adre);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public void addDieTo(Stat s) {
        s.set("Superiority Die", new Die(dieSize));
    }

    public void forget(int num) {
        for (int i = 0; i < num; i++) {
            Maneuver m = Query.ask(player, new SelectQuery<Maneuver>("Choose a maneuver to forget", maneuvers)).response;
            maneuvers.remove(m);
            m.forget();
        }
    }

    private AttackManeuver[] getAttackManeuvers() {
        return maneuvers.stream().filter(m -> m instanceof AttackManeuver).map(m -> (AttackManeuver) m).toArray(l -> new AttackManeuver[l]);
    }

    private boolean hasAttackManeuvers() {
        return maneuvers.stream().anyMatch(m -> m instanceof AttackManeuver);
    }

    public void learn(int num) {
        for (int i = 0; i < num; i++) {
            Selectable s = Query.ask(player, new SelectQuery("Choose a maneuver to learn", remainingManeuvers())).response;
            try {
                Maneuver m = (Maneuver) Class.forName("maneuvers." + s.getName().replaceAll("'", "__").replaceAll(" ", "_"))
                        .getConstructor(Player.class, ManeuversComponent.class).newInstance(player, this);
                maneuvers.add(m);
                m.description = s.getDescription();
                m.learn();
            } catch (Exception ex) {
                Log.print(ex);
            }
        }
    }

    public void optionalReplace(int num) {
        for (int i = 0; i < num; i++) {
            if (Query.ask(player, new BooleanQuery("Forget a maneuver and learn a different one?")).response) {
                forget(1);
                learn(1);
            }
        }
    }

    public ArrayList<Selectable> remainingManeuvers() {
        ArrayList<Selectable> options = Selectable.load("fighter/maneuvers.txt");
        //options.removeAll(maneuvers);
        options.removeIf(s -> maneuvers.stream().anyMatch(m -> m.equiv(s)));
        /*
         for (int i = 0; i < options.size(); i++) {
         for (Maneuver m : maneuvers) {
         if (options.get(i).getName().equals(m.getName())) {
         options.remove(i);
         i--;
         break;
         }
         }
         }
         */
        return options;
    }
}
