package grid;

import core.Main;
import creature.Creature;
import graphics.data.Texture;
import graphics.loading.SpriteContainer;
import util.Vec2;

public class Square {

    public static final int SIZE = 64;
    public int x;
    public int y;
    public boolean isWall;
    public Texture tex;
    public Creature creature;

    public Square(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        if (isWall) {
            tex = SpriteContainer.loadSprite("wall");
        } else {
            tex = SpriteContainer.loadSprite("floor");
        }
    }

    public Square(int x, int y, String sprite) {
        this.x = x;
        this.y = y;
        this.isWall = true;
        tex = SpriteContainer.loadSprite(sprite);
    }

    public Square(int x, int y, Texture sprite, boolean wall) {
        this.x = x;
        this.y = y;
        this.isWall = wall;
        tex = sprite;
    }

    public Vec2 center() {
        return new Vec2(x * SIZE + SIZE / 2, y * SIZE + SIZE / 2);
    }

    public int distanceTo(Square other) {
        int dx = Math.abs(x - other.x);
        int dy = Math.abs(y - other.y);
        int diag = Math.min(dx, dy);
        int straight = Math.max(dx, dy) - diag;
        if (diag % 2 == 0) {
            return diag / 2 * 15 + straight * 5;
        } else {
            return diag / 2 * 15 + 5 + straight * 5;
        }
    }

    public Vec2 LL() {
        return new Vec2(x * SIZE, y * SIZE);
    }

    public Vec2 LR() {
        return new Vec2(x * SIZE + SIZE, y * SIZE);
    }

//    public static Vec2 positionAt(int x, int y) {
//        return new Vec2((x + .5) * SIZE, (y + .5) * SIZE);
//    }
//
    public static Square tileAt(Vec2 pos) {
        return Main.gameManager.elc.getEntity(World.class).getComponent(GridComponent.class).tileAt(pos);
    }

    public Vec2 UL() {
        return new Vec2(x * SIZE, y * SIZE + SIZE);
    }

    public Vec2 UR() {
        return new Vec2(x * SIZE + SIZE, y * SIZE + SIZE);
    }
}
