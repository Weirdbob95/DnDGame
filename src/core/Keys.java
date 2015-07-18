package core;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;

public abstract class Keys {

    private static ArrayList<Integer> down = new ArrayList();
    private static ArrayList<Integer> pressed = new ArrayList();
    private static ArrayList<Integer> released = new ArrayList();
    private static HashMap<Integer, Integer> time = new HashMap();

    public static boolean anyPressed() {
        return !pressed.isEmpty();
    }

    public static int getTime(int button) {
        if (!time.containsKey(button)) {
            return 0;
        }
        return time.get(button);
    }

    public static boolean isDown(int key) {
        return down.contains(key);
    }

    public static boolean isPressed(int key) {
        return pressed.contains(key);
    }

    public static boolean isReleased(int key) {
        return released.contains(key);
    }

    public static void update() {
        pressed.clear();
        released.clear();
        while (Keyboard.next()) {
            Integer key = Keyboard.getEventKey();
            if (Keyboard.getEventKeyState()) {
                down.add(key);
                pressed.add(key);
                time.put(key, 0);
            } else {
                down.remove(key);
                released.add(key);
            }
        }
        for (Integer i : down) {
            time.put(i, time.get(i) + 1);
        }
    }
}
