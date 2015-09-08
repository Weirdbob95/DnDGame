/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package player;

import amounts.Stat;

/**
 *
 * @author RLund16
 */
public class KiComponent {
    
    
    public Stat MaximumKi = new Stat();
    public Stat CurrentKi = new Stat("MaxKi", MaximumKi);
    
    public void useKi(int kiUsed){
        CurrentKi.edit("kiUsed", -kiUsed);
    }
    
    public int getKi(){return CurrentKi.get();}
            
    public int getMaxKi(){ return MaximumKi.get();}
    
    public void addMax(int addMax){MaximumKi.edit("MaxKi",addMax);}


}
