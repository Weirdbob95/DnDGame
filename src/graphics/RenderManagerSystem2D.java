package graphics;

import core.AbstractSystem;
import core.Keys;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import static util.Util.floatBuffer;

public class RenderManagerSystem2D extends AbstractSystem {

    private RenderManagerComponent2D rmc;

    public RenderManagerSystem2D(RenderManagerComponent2D rmc) {
        this.rmc = rmc;

        try {
            //Display Init
            Camera.setDisplayMode(rmc.viewSize, rmc.startFullscreen);
            Display.setVSyncEnabled(true);
            Display.setResizable(true);
            Display.setTitle("So how are you today?");
            Display.create();
            //OpenGL Init
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            init2D();

        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getLayer() {
        return 0;
    }

    public void init2D() {
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
    }

    public void init3D() {
        //3D
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glAlphaFunc(GL_GREATER, 0.5f);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        //----------- Variables & method calls added for Lighting Test -----------//
        glShadeModel(GL_SMOOTH);
        glLightModel(GL_LIGHT_MODEL_AMBIENT, floatBuffer(.5, .5, .5, 1));		// global ambient light

        glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
        //----------- END: Variables & method calls added for Lighting Test -----------//
    }

    @Override
    public void update() {
        if (Keys.isPressed(Keyboard.KEY_F11)) {
            Camera.setDisplayMode(rmc.viewSize, !Display.isFullscreen());
        }

        Camera.calculateViewport(rmc.viewSize);

        Camera.setProjection2D(rmc.LL(), rmc.UR());

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0, 0, 1);
    }

}
