package grid;

import core.AbstractComponent;
import creature.Creature;
import graphics.Graphics2D;
import static graphics.Graphics2D.drawSpriteFast;
import graphics.data.Texture;
import graphics.loading.SpriteContainer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;
import static util.Color4d.WHITE;
import util.Vec2;

public class GridComponent extends AbstractComponent {

    public String fileName;
    public Square[][] tileGrid;
    public int width;
    public int height;
    public int list;
    private static String path = "levels/";
    private static String type = ".png";

    private Square createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Square(x, y, true);
            case 0xFFFF0000: //255 0 0
                Square s = new Square(x, y, false);
                //new Player(s);
                Creature.loadCreature("Lion", s);
                return s;
            default:
                return new Square(x, y, false);
        }
    }

    public void load(String fileName) {
        this.fileName = fileName;
        //Load image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path + fileName + type));
        } catch (IOException ex) {
            throw new RuntimeException("Level " + fileName + " doesn't exist");
        }
        //Init tile grid
        width = image.getWidth();
        height = image.getHeight();
        tileGrid = new Square[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileGrid[x][y] = createTile(x, y, image.getRGB(x, height - y - 1));
            }
        }
        //List
        list = glGenLists(1);
        glNewList(list, GL_COMPILE);
        glEnable(GL_TEXTURE_2D);
//        Texture[] texList = {loadSprite("floor"), loadSprite("wall")};
        WHITE.glColor();
        //Draw
        for (Texture tex : SpriteContainer.all()) {
            tex.bind();
            glBegin(GL_QUADS);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Square t = tileGrid[i][j];
                    if (t.tex != tex) {
                        continue;
                    }
                    drawSpriteFast(tex, t.LL(), t.LR(), t.UR(), t.UL());
                }
            }
            glEnd();
        }
        //Grid
        for (int i = 0; i < width; i++) {
            Graphics2D.drawLine(new Vec2(i * Square.SIZE, 0), new Vec2(i * Square.SIZE, height * Square.SIZE));
        }
        for (int i = 0; i < height; i++) {
            Graphics2D.drawLine(new Vec2(0, i * Square.SIZE), new Vec2(width * Square.SIZE, i * Square.SIZE));
        }
        glEndList();
    }

    public Square tileAt(Vec2 pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x < width * Square.SIZE && pos.y < height * Square.SIZE) {
            return tileGrid[(int) pos.x / Square.SIZE][(int) pos.y / Square.SIZE];
        }
        return null;
    }

    public boolean wallAt(Vec2 pos) {
        return tileAt(pos) == null || tileAt(pos).isWall;
    }
}
