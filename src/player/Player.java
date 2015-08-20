package player;

import creature.Creature;
import creature.HealthbarSystem;
import events.EventHandler;
import events.EventListener;
import graphics.SpriteComponent;
import graphics.SpriteSystem;
import grid.Square;
import items.Weapon;
import movement.PositionComponent;
import movement.RotationComponent;
import rounds.InitiativeOrder;
import util.SerializationUtils;

public class Player extends Creature {

    public ClassComponent clc;
    public RaceComponent rac;

    public Player(Square square) {
        super(square);

        clc = add(new ClassComponent(this));
        rac = add(new RaceComponent());

        add(new FightingStylesComponent(this));
        wc.setHands(2);

        wc.held[0] = Weapon.loadWeapon("Longsword"); //Fix pls
    }

    public static Player loadPlayer(String name, Square square) {
        Player p = (Player) SerializationUtils.load(name);
        p.glc.moveToSquare(square.center(), false);
        InitiativeOrder.io.add(p.cc);
        SpriteComponent sc = p.getComponent(SpriteComponent.class);
        sc.setSprite(sc.name);
        p.add(new SpriteSystem(p.getComponent(PositionComponent.class), p.getComponent(RotationComponent.class), sc));
        p.add(new HealthbarSystem(p.getComponent(PositionComponent.class), p.hc, p.cdc));
        for (EventListener el : p.elc.listenerList) {
            EventHandler.addListener(el);
        }
        return p;
    }
}
