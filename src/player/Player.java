package player;

import classes.PlayerClass;
import core.Core;
import creature.Creature;
import creature.HealthbarSystem;
import events.EventHandler;
import graphics.SpriteComponent;
import graphics.SpriteSystem;
import grid.Square;
import items.Weapon;
import movement.PositionComponent;
import movement.RotationComponent;
import rounds.InitiativeOrder;
import util.SerializationUtils;
import util.Util;

public class Player extends Creature {

    public BackgroundComponent bc;
    public ClassComponent clc;
    public ExpertiseComponent ec;
    public FightingStylesComponent fsc;
    public ProficienciesComponent pc;
    public RaceComponent rac;

    public Player(Square square) {
        super(square);

        bc = add(new BackgroundComponent(this));
        clc = add(new ClassComponent(this));
        ec = add(new ExpertiseComponent(this));
        fsc = add(new FightingStylesComponent(this));
        pc = add(new ProficienciesComponent(this));
        rac = add(new RaceComponent(this));

        wc.setHands(2);

        getComponent(SpriteComponent.class).name = "blue";
        wc.held[0] = Weapon.loadWeapon("Longsword"); //Fix pls
    }

    public String characterSheet() {
        String r = "";
        r += "Character Name: " + cdc.name + "\n";
        r += "Class & Level: ";
        for (int i = 0; i < clc.classes.size(); i++) {
            PlayerClass pc = clc.classes.get(i);
            if (pc.archetype != null) {
                r += Util.classToName(pc.archetype) + " ";
            }
            r += Util.classToName(pc) + " " + pc.level;
            r += (i == clc.classes.size() - 1) ? "\n" : " / ";
        }
        r += "Race: " + Util.classToName(rac.race);

        /*
         Character Name: __
         Class & Level: __
         Race: __
         Alignment: __
         Background: __

         Proficiency Bonus: __
         Has Inspiration: __

         Hit Points: __/__
         Armor Class: __
         Initiative: __
         Land Speed: __
         Swim Speed: __

         Strength: __ (+__)
         Dexterity: __ (+__)
         etc.

         Saving Throw Proficiencies:
         Strength
         Constitution

         Skill Proficiencies:
         Athletics
         History
         Perception
         Deception

         Class Features:
         Fighting Style: __

         Feats:
         Actor
         Sentinel
         */
        return r;
    }

    public static Player loadPlayer(String name, Square square) {
        Player p = (Player) SerializationUtils.load("chars/" + name);
        Core.gameManager.elc.add(p);
        p.glc.moveToSquare(square);
        p.glc.updateSpritePos();
        InitiativeOrder.io.add(p.cc);
        SpriteComponent sc = p.getComponent(SpriteComponent.class);
        sc.setSprite(sc.name);
        p.add(new SpriteSystem(p.getComponent(PositionComponent.class), p.getComponent(RotationComponent.class), sc));
        p.add(new HealthbarSystem(p.getComponent(PositionComponent.class), p.hc, p.cdc));
        p.elc.listenerMap.keySet().forEach(el -> EventHandler.addListener(el, p.elc.listenerMap.get(el)));
        return p;
    }
}
