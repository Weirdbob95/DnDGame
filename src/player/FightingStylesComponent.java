package player;

import static actions.Action.Type.REACTION;
import amounts.ConditionalAmount;
import amounts.Die;
import amounts.Value;
import core.AbstractComponent;
import enums.FightingStyle;
import events.AbstractEventListener;
import events.Event;
import events.attack.AttackDamageResultEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackRollEvent;
import grid.GridUtils;
import items.Weapon;
import java.util.ArrayList;
import java.util.Arrays;
import queries.BooleanQuery;
import queries.Query;
import queries.SelectQuery;

public class FightingStylesComponent extends AbstractComponent {

    public ArrayList<FightingStyle> fightingStyles;
    public Player player;

    public FightingStylesComponent(Player player) {
        fightingStyles = new ArrayList();
        this.player = player;
    }

    private void addFightingStyle(FightingStyle style) {
        if (fightingStyles.contains(style)) {
            return;
        }
        fightingStyles.add(style);
        switch (style) {
            case Archery:
                new AbstractEventListener(player) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackRollEvent are = (AttackRollEvent) e;
                        if (are.a.attacker == player && are.a.isWeapon && are.a.weapon.isRanged) {
                            are.a.toHit.set("Archery Fighting Style", 2);
                        }
                    }
                };
                break;
            case Defense:
                player.ac.AC.set("Defense Fighting Stlyle", new ConditionalAmount(() -> {
                    return player.ac.armor != null;
                }, new Value(1)));
                break;
            case Dueling:
                new AbstractEventListener(player) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackDamageRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackDamageRollEvent adre = (AttackDamageRollEvent) e;
                        if (adre.a.attacker == player && adre.a.isWeapon && !adre.a.weapon.isRanged) {
                            if (player.wc.getAll(Weapon.class).size() == 1 && player.wc.getAll(Weapon.class).get(0) == adre.a.weapon) {
                                adre.a.damage.set("Dueling Fighting Style", 2);
                            }
                        }
                    }
                };
                break;
            case Great_Weapon_Fighting:
                new AbstractEventListener(player) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackDamageResultEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackDamageResultEvent adre = (AttackDamageResultEvent) e;
                        if (adre.a.attacker == player && adre.a.isWeapon && !adre.a.weapon.isRanged) {
                            if (player.wc.countHands(adre.a.weapon) == 2 && (adre.a.weapon.two_handed || adre.a.weapon.versatile != null)) {
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
                new AbstractEventListener(player) {
                    @Override
                    public Class<? extends Event>[] callOn() {
                        return new Class[]{AttackRollEvent.class};
                    }

                    @Override
                    public void onEvent(Event e) {
                        AttackRollEvent are = (AttackRollEvent) e;
                        if (player.amc.available.contains(REACTION)) {
                            if (are.a.attacker != player && are.a.target != player) {
                                if (GridUtils.minDistance(player.glc.occupied, are.a.target.glc.occupied) <= 5) {
                                    if (Query.ask(player, new BooleanQuery("Do you want to use the Protection fighting style?")).response) {
                                        player.amc.available.remove(REACTION);
                                        are.a.disadvantage = true;
                                    }
                                }
                            }
                        }
                    }
                };
                break;
        }
    }

    public void chooseFightingStyle(FightingStyle[] styles) {
        ArrayList<FightingStyle> styleList = new ArrayList(Arrays.asList(styles));
        styleList.removeAll(fightingStyles);
        FightingStyle fightingStyle = Query.ask(player, new SelectQuery<FightingStyle>("Choose a fighting style", styleList)).response;
        addFightingStyle(fightingStyle);
    }
}
