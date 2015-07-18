package graphics;

import core.AbstractComponent;
import graphics.data.Texture;
import graphics.loading.SpriteContainer;
import java.util.ArrayList;
import util.Color4d;
import util.Vec2;

public class SpriteComponent extends AbstractComponent {

    public ArrayList<Texture> textureArray;
    public String name;
    public double imageIndex;
    public double imageSpeed;
    public boolean visible;
    public Vec2 scale;
    public Color4d color;

    public SpriteComponent() {
        this("default");
    }

    public SpriteComponent(String name) {
        this(name, 1, 1);
    }

    public SpriteComponent(String name, int x, int y) {
        setSprite(name, x, y);
        imageIndex = 0;
        imageSpeed = 0;
        visible = true;
        scale = new Vec2(1, 1);
        color = Color4d.WHITE;
    }

    public Texture getTexture() {
        return textureArray.get((int) imageIndex % textureArray.size());
    }

    public void setSprite(String name) {
        setSprite(name, 1, 1);
    }

    public void setSprite(String name, int x, int y) {
        this.name = name;
        textureArray = SpriteContainer.loadSprite(name, x, y);
    }
}
