package maneuvers;

import static actions.Action.Type.REACTION;
import actions.MonsterAttackAction;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackEvent;
import events.attack.AttackFinishEvent;
import grid.GridUtils;
import items.Weapon;
import java.util.ArrayList;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class Riposte extends Maneuver {

    public boolean active;

    public Riposte(Player player, ManeuversComponent mc) {
        super(player, mc);

        add(AttackDamageRollEvent.class, e -> {
            if (active) {
                if (e.a.attacker == player) {
                    mc.addDieTo(e.a.damage);
                }
            }
        });
        add(AttackFinishEvent.class, e -> {
            if (e.a.target == player) {
                if (!e.a.isRanged) {
                    if (!e.hit) {
                        if (mc.diceUsed < mc.diceCap) {
                            if (player.amc.hasType(REACTION)) {
                                int dist = GridUtils.minDistance(player, e.a.target);

                                ArrayList<Selectable> choices = new ArrayList();
                                player.wc.getAll(Weapon.class).stream().filter(w -> !w.isRanged).forEach(choices::add);
                                player.amc.getActionList(MonsterAttackAction.class).stream().filter(m -> !m.isRanged).forEach(choices::add);
                                choices.add(player.wc.unarmedStrike);

                                choices.removeIf(s -> {
                                    int reach = player.cdc.reach.get();
                                    if (s instanceof Weapon && ((Weapon) s).reach) {
                                        reach += 5;
                                    } else if (s instanceof MonsterAttackAction) {
                                        reach = Math.max(((MonsterAttackAction) s).range, ((MonsterAttackAction) s).rangeLong);
                                    }
                                    return dist <= reach;
                                });

                                if (!choices.isEmpty()) {
                                    if (Query.ask(player, new BooleanQuery("Use the Riposte maneuver?")).response) {
                                        Selectable s = Query.ask(player, new SelectQuery("Choose how to attack", choices)).response;
                                        player.amc.useType(REACTION, this);
                                        mc.diceUsed++;
                                        active = mc.moddingAttack = true;
                                        (s instanceof Weapon ? new AttackEvent(player, e.a.attacker, (Weapon) s, player.cdc.reach.get()
                                                + (((Weapon) s).reach ? 5 : 0), this) : new AttackEvent((MonsterAttackAction) s, e.a.attacker)).call();
                                        active = mc.moddingAttack = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
