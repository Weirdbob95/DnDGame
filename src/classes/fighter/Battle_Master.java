package classes.fighter;

import classes.Archetype;
import maneuvers.ManeuversComponent;

public class Battle_Master extends Archetype<Fighter> {

    public ManeuversComponent mc;

    public Battle_Master(Fighter playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:
                mc = player().getComponent(ManeuversComponent.class);
                if (mc == null) {
                    mc = player().add(new ManeuversComponent(player()));
                }
                mc.learn(3);
                mc.diceCap = 4;
                mc.dieSize = 8;
                break;
            case 7:
                mc.optionalReplace(1);
                mc.learn(2);
                mc.diceCap++;
                break;
            case 10:
                mc.optionalReplace(1);
                mc.learn(2);
                mc.dieSize = 10;
                break;
            case 15:
                mc.optionalReplace(1);
                mc.learn(2);
                mc.diceCap++;
                break;
            case 18:
                mc.dieSize = 12;
                break;
        }
    }
}
