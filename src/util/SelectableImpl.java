package util;

public class SelectableImpl implements Selectable {

    private final String description;
    private final String name;

    public SelectableImpl(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public SelectableImpl(String[] arr) {
        this(arr[0], arr[1]);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

}
