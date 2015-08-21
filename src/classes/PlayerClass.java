package classes;

import enums.AbilityScore;
import enums.CastingType;
import enums.Skill;
import java.io.Serializable;
import player.Player;
import queries.NotificationQuery;
import queries.PointBuyQuery;
import queries.Query;

public abstract class PlayerClass implements Serializable {

    public Player player;
    public int level;

    public PlayerClass(Player player) {
        this.player = player;
    }

    public void abilityScoreImprovement() {
        player.asc.setAll(Query.ask(player, new PointBuyQuery(2, player.asc.getAll(), new int[]{20, 20, 20, 20, 20, 20})).response);
    }

    public CastingType getCastingType() {
        return CastingType.NONE;
    }

    public abstract int hitDie();

    public void levelTo(int newLevel) {
        for (int i = level + 1; i <= newLevel; i++) {
            Query.ask(player, new NotificationQuery("You leveled up!"));
            level = i;
            if (player.clc.level() == 1) {
                player.hc.maxHealth.set("Level 1 Hit Die", hitDie());
            } else {
                player.hc.maxHealth.set("Level " + player.clc.level() + " Hit Die", hitDie() / 2 + 1);
            }
            player.hc.maxHealth.set("Level " + player.clc.level() + " Constitution", player.asc.mod(AbilityScore.CON));
            levelUp(level);
        }
    }

    public abstract void levelUp(int newLevel);

    public abstract AbilityScore[] savingThrows();

    public int skillAmount() {
        return 2;
    }

    public abstract Skill[] skills();
}
