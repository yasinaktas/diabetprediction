public class Point {
    private final String name;
    private final double x;
    private final double y;
    private final ClassifierType type;

    public Point(String name, double x, double y, ClassifierType type) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ClassifierType getType() {
        return type;
    }
}
