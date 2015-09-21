package events.move;

import static actions.Action.Type.REACTION;
import actions.MonsterAttackAction;
import core.Core;
import creature.Creature;
import events.Event;
import events.attack.AttackEvent;
import events.attack.OpportunityAttackEvent;
import grid.GridUtils;
import grid.Square;
import items.Weapon;
import java.util.ArrayList;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;
import util.Selectable;

public class PreSegmentMoveEvent extends Event {

    public PathMoveEvent pme;
    public Square from;
    public Square to;

    public PreSegmentMoveEvent(PathMoveEvent pme, Square from, Square to) {
        this.pme = pme;
        this.from = from;
        this.to = to;
    }

    @Override
    public void call() {
        super.call();
        Core.gameManager.elc.getEntityList(Creature.class).forEach(creature -> {
            if (creature == pme.creature) {
                return;
            }
            int prevDist = GridUtils.minDistance(pme.creature, creature);
            int newDist = GridUtils.minDistance(pme.creature.glc.occupiedAt(to), creature.glc.occupied);

            ArrayList<Selectable> choices = new ArrayList();
            creature.wc.getAll(Weapon.class).stream().filter(w -> !w.isRanged).forEach(choices::add);
            creature.amc.getActionList(MonsterAttackAction.class).stream().filter(m -> !m.isRanged).forEach(choices::add);
            choices.add(creature.wc.unarmedStrike);

            choices.removeIf(s -> {
                int reach = creature.cdc.reach.get();
                if (s instanceof Weapon && ((Weapon) s).reach) {
                    reach += 5;
                } else if (s instanceof MonsterAttackAction) {
                    reach = Math.max(((MonsterAttackAction) s).range, ((MonsterAttackAction) s).rangeLong);
                }
                return prevDist > reach || newDist <= reach;
            });

            if (!choices.isEmpty()) {
                if (creature.amc.hasType(REACTION)) {
                    if (Query.ask(creature, new BooleanQuery("Make an opportunity attack?")).response) {
                        Selectable s = Query.ask(creature, new SelectQuery("Choose how to attack", choices)).response;
                        OpportunityAttackEvent e = new OpportunityAttackEvent(s instanceof Weapon ? new AttackEvent(creature, pme.creature, (Weapon) s, creature.cdc.reach.get()
                                + (((Weapon) s).reach ? 5 : 0), "Opportunity Attack") : new AttackEvent((MonsterAttackAction) s, pme.creature), OpportunityAttackEvent.LEAVING_REACH);
                        creature.amc.useType(REACTION, e);
                        e.call();
                    }
                }
            }
        });
    }
}
