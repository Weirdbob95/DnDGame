/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.barbarian;

import classes.Archetype;

/**
 *
 * @author RLund16
 */
public class Berserker extends Archetype<Barbarian> {

    public Berserker(Barbarian playerClass) {
        super(playerClass);
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 3:

                break;
            case 6:
                break;
            case 10:
                //
                break;
            case 14:
                //Retaliation
                break;
        }
    }

}
