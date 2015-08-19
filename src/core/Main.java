package core;

import grid.World;
import java.io.IOException;
import rounds.RoundController;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            Core.init();

            new World();
            new RoundController().start();

            Core.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Core.destroy();
        }
        System.exit(0);
    }
}
