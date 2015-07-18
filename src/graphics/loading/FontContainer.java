package graphics.loading;

import graphics.data.GLFont;
import java.awt.Font;
import java.util.HashMap;

public class FontContainer {

    private static HashMap<String, GLFont> fontMap = new HashMap();

    static {
        add("Default", "Times New Roman", Font.PLAIN, 24);
        add("Small", "Calibri", Font.PLAIN, 16);
        add("Medium", "Cambria", Font.PLAIN, 24);
    }

    public static void add(String gameName, String name, int style, int size) {
        Font awtFont = new Font(name, style, size);
        GLFont glFont = new GLFont(awtFont, false);
        fontMap.put(gameName, glFont);
    }

    public static GLFont get(String name) {
        return fontMap.get(name);
    }
}
