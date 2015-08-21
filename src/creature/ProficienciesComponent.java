package creature;

import amounts.Amount;
import amounts.Value;
import core.AbstractComponent;
import enums.AbilityScore;
import enums.Skill;
import items.Weapon;
import java.util.HashSet;
import player.Player;

public class ProficienciesComponent extends AbstractComponent {

    public Amount prof = new Amount() {
        @Override
        public Value asValue() {
            return new Value(get());
        }

        @Override
        public int get() {
            return (player.clc.level() + 7) / 4;
        }

        @Override
        public int roll() {
            return get();
        }
    };
    public HashSet<Skill> skillProfs = new HashSet();
    public HashSet<Weapon> weaponProfs = new HashSet();
    public HashSet<AbilityScore> savingThrowProfs = new HashSet();
    public Player player;

    public ProficienciesComponent(Player player) {
        this.player = player;
    }
}
