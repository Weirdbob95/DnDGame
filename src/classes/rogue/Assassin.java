package classes.rogue;

import classes.Archetype;

public class Assassin extends Archetype<Rogue> {

    public Assassin(Rogue playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                break;
        }
    }
}
