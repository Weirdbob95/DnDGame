package player;

import static actions.Action.Type.REACTION;
import amounts.ConditionalAmount;
import amounts.Die;
import amounts.Value;
import core.AbstractComponent;
import enums.FightingStyle;
import events.EventListener;
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
                EventListener.createListener(player, AttackRollEvent.class, e -> {
                    if (e.a.attacker == player && e.a.isWeapon && e.a.weapon.isRanged) {
                        e.a.toHit.set("Archery Fighting Style", 2);
                    }
                });
                break;
            case Defense:
                player.ac.AC.set("Defense Fighting Stlyle", new ConditionalAmount(() -> player.ac.armor != null, new Value(1)));
                break;
            case Dueling:
                EventListener.createListener(player, AttackDamageRollEvent.class, e -> {
                    if (e.a.attacker == player && e.a.isWeapon && !e.a.weapon.isRanged) {
                        if (player.wc.getAll(Weapon.class).size() == 1 && player.wc.getAll(Weapon.class).get(0) == e.a.weapon) {
                            e.a.damage.set("Dueling Fighting Style", 2);
                        }
                    }
                });
                break;
            case Great_Weapon_Fighting:
                EventListener.createListener(player, AttackDamageResultEvent.class, e -> {
                    if (e.a.attacker == player && e.a.isWeapon && !e.a.weapon.isRanged) {
                        if (player.wc.countHands(e.a.weapon) == 2 && (e.a.weapon.two_handed || e.a.weapon.versatile != null)) {
                            for (Die d : e.a.damage.components.get("Base").asValue().dice) {
                                if (d.roll <= 2) {
                                    d.roll();
                                }
                            }
                        }
                    }
                });
                break;
            case Protection:
                EventListener.createListener(player, AttackRollEvent.class, e -> {
                    if (player.amc.hasType(REACTION)) {
                        if (e.a.attacker != player && e.a.target != player) {
                            if (GridUtils.minDistance(player, e.a.target) <= 5) {
                                if (Query.ask(player, new BooleanQuery("Do you want to use the Protection fighting style?")).response) {
                                    player.amc.useType(REACTION, this);
                                    e.a.disadvantage = true;
                                }
                            }
                        }
                    }
                });
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
