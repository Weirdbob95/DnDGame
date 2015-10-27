package player;

import core.Core;
import creature.Creature;
import creature.HealthbarSystem;
import enums.AbilityScore;
import events.EventHandler;
import graphics.SpriteComponent;
import graphics.SpriteSystem;
import grid.Square;
import items.Weapon;
import movement.PositionComponent;
import movement.RotationComponent;
import rounds.InitiativeOrder;
import util.Printable;
import util.SerializationUtils;
import util.Util;

public class Player extends Creature {

    public BackgroundComponent bc;
    public ClassComponent clc;
    public ProficienciesComponent pc;
    public RaceComponent rac;

    public Player(Square square) {
        super(square);

        bc = add(new BackgroundComponent(this));
        clc = add(new ClassComponent(this));
        pc = add(new ProficienciesComponent(this));
        rac = add(new RaceComponent(this));

        wc.setHands(2);

        getComponent(SpriteComponent.class).name = "blue";
        wc.held[0] = Weapon.loadWeapon("Longsword"); //Fix pls
    }

    public String characterSheet() {
        String r = "\n";
        r += "Character Name: " + cdc.name + "\n";
        r += "Class & Level: " + clc.classes.stream().map(c -> (c.archetype != null ? Util.classToName(c.archetype) + " " : "")
                + Util.classToName(c) + " " + c.level).reduce((a, b) -> a + " / " + b).get() + "\n";
        r += "Race: " + (rac.race.getSubrace() == null ? Util.classToName(rac.race) : rac.race.getSubrace().getName()) + "\n";
        r += "Alignment: " + cdc.alignment + "\n";
        r += "Background: " + Util.classToName(bc.background) + "\n";
        r += "\n";
        r += "Proficiency Bonus: +" + pc.prof.get() + "\n";
        //Has inspiration
        r += "\n";
        r += "Hit Points: " + hc.currentHealth.get() + "/" + hc.maxHealth.get() + "\n";
        r += "Armor Class: " + ac.AC.get() + "\n";
        //Initiative
        r += "Speed: " + spc.landSpeed.get() + "\n";
        r += "\n";
        for (AbilityScore as : AbilityScore.values()) {
            r += as.longName() + ": " + asc.get(as).get() + " (" + (asc.mod(as).get() >= 0 ? "+" : "") + asc.mod(as).get() + ")\n";
        }
        r += "\n";
        r += "Saving Throw Proficiencies:\n";
        r += pc.savingThrowProfs.stream().map(as -> as.longName() + "\n").reduce("", String::concat);
        r += "\n";
        r += "Skill Proficiencies:\n";
        r += pc.skillProfs.stream().map(s -> s.getName() + "\n").reduce("", String::concat);
        r += "\n";
        r += "Miscellaneous:\n";
        r += componentList.stream().filter(c -> c instanceof Printable).map(c -> ((Printable) c).print() + "\n").reduce("", String::concat);
        r += clc.classes.stream().map(c -> c.print()).reduce("", String::concat);

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
