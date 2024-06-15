package jp.jaxa.iss.kibo.rpc.defaultapk.util;

import gov.nasa.arc.astrobee.types.Point;

public class Cuboid {
    public final Point startPoint;
    public final Point endPoint;

    public Cuboid(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Cuboid(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        this(new Point(startX, startY, startZ), new Point(endX, endY, endZ));
    }

    public Point getMidPoint() {
        return MathHelper.getMidPoint(this.startPoint, this.endPoint);
    }
}
