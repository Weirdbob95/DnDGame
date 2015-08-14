package core;

import graphics.loading.FontContainer;
import grid.World;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import rounds.RoundController;
import util.Log;

public abstract class Main {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        Log.init();
        try {
            init();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
            Log.close();
        }
        System.exit(0);
    }

    public static final int speed = 60;
    public static final int LAYERS = 4;
    public static ArrayList<ArrayList<AbstractSystem>> systems;
    public static GameManager gameManager;
    public static boolean paused = false;

    public static void destroy() {
        Controllers.destroy();
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    public static void init() throws LWJGLException {
        systems = new ArrayList();
        for (int i = 0; i < LAYERS; i++) {
            systems.add(new ArrayList());
        }
        gameManager = new GameManager();
        Keyboard.create();
        Mouse.create();
        FontContainer.init();

        new World();
        new RoundController().start();
    }

    public static void run() {
        while (!Display.isCloseRequested() && !Keys.isPressed(Keyboard.KEY_ESCAPE)) {
            //Input
            Keys.update();
            MouseInput.update();
            //Logic
            for (ArrayList<AbstractSystem> list : systems) {
                for (int i = 0; i < list.size(); i++) {
                    if (!paused || !list.get(i).pauseable()) {
                        list.get(i).update();
                    }
                }
            }
            //Graphics
            Display.update();
            Display.sync(speed);
        }
    }
}
