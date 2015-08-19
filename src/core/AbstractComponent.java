package core;

public abstract class AbstractComponent {

    public AbstractComponent() {
//        if (!(this instanceof EntityListComponent)) {
//            Main.gameManager.elc.add(this);
//        }
    }

    protected void destroy() {
        Core.gameManager.elc.remove(this);
    }
}
