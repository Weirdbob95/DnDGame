package player;

import static actions.Action.Type.REACTION;
import amounts.ConditionalAmount;
import amounts.Die;
import amounts.Value;
import core.AbstractComponent;
import creature.Creature;
import enums.FightingStyle;
import events.AbstractEventListener;
import events.Event;
import events.attack.AttackDamageResultEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackRollEvent;
import grid.GridUtils;
import items.Weapon;
import java.util.ArrayList;
import queries.BooleanQuery;
import queries.Query;

public class FightingStylesComponent extends AbstractComponent {

    public ArrayList<FightingStyle> fightingStyles;
    public Creature creature;

    public FightingStylesComponent(Creature creature) {
        fightingStyles = new ArrayList();
        this.creature = creature;
    }

    public boolean addFightingStyle(FightingStyle style) {
        if (fightingStyles.contains(style)) {
            return false;
        }
        fightingStyles.add(style);
        switch (style) {
            case Archery:
                new AbstractEventListener(creature) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackRollEvent are = (AttackRollEvent) e;
                        if (are.a.attacker == creature && are.a.isWeapon && are.a.weapon.isRanged) {
                            are.a.toHit.set("Archery Fighting Style", 2);
                        }
                    }
                };
                break;
            case Defense:
                creature.ac.AC.set("Defense Fighting Stlyle", new ConditionalAmount(() -> {
                    return creature.ac.armor != null;
                }, new Value(1)));
                break;
            case Dueling:
                new AbstractEventListener(creature) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackDamageRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackDamageRollEvent adre = (AttackDamageRollEvent) e;
                        if (adre.a.attacker == creature && adre.a.isWeapon && !adre.a.weapon.isRanged) {
                            if (creature.wc.getAll(Weapon.class).size() == 1 && creature.wc.getAll(Weapon.class).get(0) == adre.a.weapon) {
                                adre.a.damage.set("Dueling Fighting Style", 2);
                            }
                        }
                    }
                };
                break;
            case Great_Weapon_Fighting:
                new AbstractEventListener(creature) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackDamageResultEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackDamageResultEvent adre = (AttackDamageResultEvent) e;
                        if (adre.a.attacker == creature && adre.a.isWeapon && !adre.a.weapon.isRanged) {
                            if (creature.wc.countHands(adre.a.weapon) == 2 && (adre.a.weapon.two_handed || adre.a.weapon.versatile != null)) {
                                for (Die d : adre.a.damage.components.get("Base").asValue().dice) {
                                    if (d.roll <= 2) {
                                        d.roll();
                                    }
                                }
                            }
                        }
                    }
                };
                break;
            case Protection:
                new AbstractEventListener(creature) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackRollEvent are = (AttackRollEvent) e;
                        if (creature.amc.available.contains(REACTION)) {
                            if (are.a.attacker != creature && are.a.target != creature) {
                                if (GridUtils.minDistance(creature.glc.occupied, are.a.target.glc.occupied) <= 5) {
                                    if (Query.ask(creature, new BooleanQuery("Do you want to use the Protection fighting style?")).response) {
                                        creature.amc.available.remove(REACTION);
                                        are.a.disadvantage = true;
                                    }
                                }
                            }
                        }
                    }
                };
                break;
        }
        return true;
    }
}
