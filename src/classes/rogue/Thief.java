package classes.rogue;

import classes.Archetype;
import classes.rogue.Rogue.Cunning_Action;
import creature.SpeedComponent;

public class Thief extends Archetype<Rogue> {

    public Thief(Rogue playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                Cunning_Action a = player().amc.getAction(Cunning_Action.class);
                //a.choices.add(   ...   );
                player().spc.climbSpeed.multComponents.remove(SpeedComponent.EXTRA_COST);
                break;
        }
    }
}
