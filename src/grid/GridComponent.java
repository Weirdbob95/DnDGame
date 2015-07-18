package grid;

import core.AbstractComponent;
import graphics.Graphics2D;
import static graphics.Graphics2D.drawSpriteFast;
import graphics.data.Texture;
import graphics.loading.SpriteContainer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;
import player.Player;
import static util.Color4d.WHITE;
import util.Vec2;

public class GridComponent extends AbstractComponent {

    public String fileName;
    public Tile[][] tileGrid;
    public int width;
    public int height;
    public int list;
    private static String path = "levels/";
    private static String type = ".png";

    public GridComponent(String fileName) {
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
        tileGrid = new Tile[width][height];
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
                    Tile t = tileGrid[i][j];
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
            Graphics2D.drawLine(new Vec2(i * Tile.SIZE, 0), new Vec2(i * Tile.SIZE, height * Tile.SIZE));
        }
        for (int i = 0; i < height; i++) {
            Graphics2D.drawLine(new Vec2(0, i * Tile.SIZE), new Vec2(width * Tile.SIZE, i * Tile.SIZE));
        }
        glEndList();
    }

    public Tile createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Tile(x, y, true);
            case 0xFFFF0000: //255 0 0
                new Player(x, y);
                return new Tile(x, y, false);
            default:
                return new Tile(x, y, false);
        }
    }

    public Vec2 rayCast(Vec2 start, Vec2 goal) {
        Vec2 diff = goal.subtract(start);
        Vec2 pos = start;
        for (int i = 0; i < 1000; i++) {
            double nextX;
            if (diff.x > 0) {
                nextX = Math.ceil(pos.x / Tile.SIZE) * Tile.SIZE;
                if (nextX == pos.x) {
                    nextX += 1;
                }
            } else {
                nextX = Math.floor(pos.x / Tile.SIZE) * Tile.SIZE;
                if (nextX == pos.x) {
                    nextX -= 1;
                }
            }
            double nextY;
            if (diff.y > 0) {
                nextY = Math.ceil(pos.y / Tile.SIZE) * Tile.SIZE;
                if (nextY == pos.y) {
                    nextY += 1;
                }
            } else {
                nextY = Math.floor(pos.y / Tile.SIZE) * Tile.SIZE;
                if (nextY == pos.y) {
                    nextY -= 1;
                }
            }
            Vec2 time = new Vec2(nextX, nextY).subtract(pos).divide(diff);
            if (time.x > time.y) {
                //y hit
                pos = new Vec2(pos.x + (nextY - pos.y) * diff.x / diff.y, nextY);
            } else {
                //x hit
                pos = new Vec2(nextX, pos.y + (nextX - pos.x) * diff.y / diff.x);
            }
            pos = pos.add(diff.multiply(.00000001));
            Tile t = tileAt(pos);
            if (t != null && t.isWall) {
                return pos.subtract(diff.multiply(.00000001));
            }
            if ((int) pos.x / Tile.SIZE == (int) goal.x / Tile.SIZE && (int) pos.y / Tile.SIZE == (int) goal.y / Tile.SIZE) {
                return goal;
            }
            if (pos.x * diff.x > goal.x * diff.x || pos.y * diff.y > goal.y * diff.y) {
                return goal;
            }
        }
        return goal;
    }

    public Tile tileAt(Vec2 pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x < width * Tile.SIZE && pos.y < height * Tile.SIZE) {
            return tileGrid[(int) pos.x / Tile.SIZE][(int) pos.y / Tile.SIZE];
        }
        return null;
    }

    public boolean wallAt(Vec2 pos) {
        return tileAt(pos) == null || tileAt(pos).isWall;
    }
}
