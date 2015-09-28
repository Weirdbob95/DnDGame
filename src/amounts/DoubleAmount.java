/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amounts;

import java.io.Serializable;

/**
 *
 * @author RLund16
 */
@FunctionalInterface
public interface DoubleAmount extends Serializable {

    public double get();

}
