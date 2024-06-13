package jp.jaxa.iss.kibo.rpc.defaultapk;

import gov.nasa.arc.astrobee.types.Point;

public enum FieldChickens {

    KOZ11(10.56, -9.475, 4.62),
    KOZ12(11.235, -9.295, 5.295),
    KOZ21(10.475, -8.295, 5.295),
    KOZ22(11.235, -8.295, 4.62),
    KOZ31(10.56, -7.195, 4.62),
    KOZ32(11.235, -7.195, 5.295);

    private final Point point;

    FieldChickens(double x, double y, double z) {
        this.point = new Point(x, y, z);
    }

    public Point get() {
        return this.point;
    }

    public double distanceTo(Point point) {
        double dx = this.point.getX() - point.getX();
        double dy = this.point.getY() - point.getY();
        double dz = this.point.getZ() - point.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
