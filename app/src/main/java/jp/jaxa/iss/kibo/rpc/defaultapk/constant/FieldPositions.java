package jp.jaxa.iss.kibo.rpc.defaultapk.constant;

import gov.nasa.arc.astrobee.types.Point;

public class FieldPositions {
    private final Point point;

    public FieldPositions(double x, double y, double z) {
        this.point = new Point(x, y, z);
    }

    public Point get() { return this.point; }

    public Point shift(double x, double y, double z) {
        return new Point(this.point.getX() + x, this.point.getY() + y, this.point.getZ() + z);
    }

    public double distanceTo(Point point) {
        double dx = this.point.getX() - point.getX();
        double dy = this.point.getY() - point.getY();
        double dz = this.point.getZ() - point.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
