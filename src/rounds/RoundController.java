package rounds;

import events.TurnStartEvent;

public class RoundController extends Thread {

    @Override
    public void run() {
        new TurnStartEvent(InitiativeOrder.io.current().creature).call();
        while (true) {
            InitiativeOrder.io.current().controller.turn();
            InitiativeOrder.io.endTurn();
        }
    }
}
