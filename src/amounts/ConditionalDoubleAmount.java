/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amounts;

/**
 *
 * @author RLund16
 */
public class ConditionalDoubleAmount implements DoubleAmount {

    public Conditional condition;
    public DoubleAmount a1;
    public DoubleAmount a2;

    public ConditionalDoubleAmount(Conditional condition, DoubleAmount a1, DoubleAmount a2) {
        this.condition = condition;
        this.a1 = a1;
        this.a2 = a2;
    }

    @Override
    public double get() {
        return (condition.check() ? a1.get() : a2.get());
    }

}
