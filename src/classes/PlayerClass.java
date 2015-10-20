package classes;

import enums.*;
import events.EventListenerContainer;
import java.io.Serializable;
import java.util.Arrays;
import player.Player;
import queries.*;
import util.*;

public abstract class PlayerClass extends EventListenerContainer implements Printable, Serializable {

    public Player player;
    public int level;
    public Archetype archetype;

    public PlayerClass(Player player) {
        super(player);
        this.player = player;
    }

    public void abilityScoreImprovement() {
        player.asc.setAll(Query.ask(player, new PointBuyQuery(2, player.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);
    }

    public abstract int archetypeLevel();

    public void chooseArchetype() {
        try {
            String chosen = Query.ask(player, new SelectQuery("Choose your character's archetype",
                    Selectable.load(getClass().getSimpleName().toLowerCase() + "/archetypes.txt"))).response.getName();
            archetype = (Archetype) Util.nameToClass("classes." + getClass().getSimpleName().toLowerCase(), chosen).getConstructor(getClass()).newInstance(this);
        } catch (Exception ex) {
            Log.print(ex);
        }
    }

    public CastingType getCastingType() {
        return CastingType.NONE;
    }

    public abstract int hitDie();

    public void levelTo(int newLevel) {
        for (int i = level + 1; i <= newLevel; i++) {
            Query.ask(player, new NotificationQuery("You leveled up!\n" + "You are now level " + i));
            level = i;
            //Choose archetype (if needed)
            if (level == archetypeLevel()) {
                chooseArchetype();
            }
            //Health
            if (player.clc.level() == 1) {
                player.hc.maxHealth.set("Level 1 Hit Die", hitDie());
            } else {
                player.hc.maxHealth.set("Level " + player.clc.level() + " Hit Die", hitDie() / 2 + 1);
            }
            player.hc.maxHealth.set("Level " + player.clc.level() + " Constitution", player.asc.mod(AbilityScore.CON));
            //Choose proficiencies
            if (player.clc.level() == 1) {
                player.pc.savingThrowProfs.addAll(Arrays.asList(savingThrows()));
                for (int j = 0; j < skillAmount(); j++) {
                    player.pc.chooseSkill(skills());
                }
            }
            //Ability score improvement
            switch (level) {
                case 4:
                case 8:
                case 12:
                case 16:
                case 19:
                    abilityScoreImprovement();
            }
            //Features
            levelUp(level);
            if (archetype != null) {
                archetype.levelUp(level);
            }
        }
    }

    public abstract void levelUp(int newLevel);

    @Override
    public String print() {
        return "";
    }

    public abstract AbilityScore[] savingThrows();

    public int skillAmount() {
        return 2;
    }

    public abstract Skill[] skills();
}
